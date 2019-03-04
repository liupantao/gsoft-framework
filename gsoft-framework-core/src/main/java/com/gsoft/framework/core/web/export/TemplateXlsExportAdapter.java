package com.gsoft.framework.core.web.export;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.gsoft.framework.util.StringUtils;

/**
 * 根据模板导出excel适配器
 * @author liupantao
 *
 */
@Component
public class TemplateXlsExportAdapter implements WebExportAdapter<TemplateXlsWebExporter>,ApplicationContextAware,Ordered {
	
	
	/**
	 * @Fields XLS_PREFIX : 导出excel前缀，可以自定义模本
	 */
	private final static String XLS_PREFIX = "xls,";
	
	private ApplicationContext applicationContext;
	
	@Override
	public boolean supports(String type) {
		String templateName = getTemplateName(type);
		if(StringUtils.isNotEmpty(templateName)){
			Resource resource = this.applicationContext.getResource(getExportTemplatePath(templateName));
			return (resource != null) && (resource.exists());
		}
		return false;
	}
	
	private String getTemplateName(String type){
		if(type.startsWith(XLS_PREFIX)){
			String[] params = type.split(",");
			return params[1];
		}
		return null;
	}
	
	private String getExportTemplatePath(String templateName) {
		return "classpath:exports/" + templateName + ".xls";
	}
	
	public InputStream loadExportTemplate(String templateName) {
		InputStream input = null;
		Resource resource = this.applicationContext.getResource(getExportTemplatePath(templateName));
		try {
			input = resource.getInputStream();
		} catch (IOException e) {
		}
		return input;
	}

	@Override
	public TemplateXlsWebExporter openExporter(OutputStream outputStream,String type) {
		return new TemplateXlsWebExporter(outputStream,loadExportTemplate(getTemplateName(type)));
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}