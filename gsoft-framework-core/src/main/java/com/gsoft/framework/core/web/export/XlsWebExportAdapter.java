package com.gsoft.framework.core.web.export;

import java.io.OutputStream;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 导出excel适配器
 * @author liupantao
 *
 */
@Component
public class XlsWebExportAdapter implements WebExportAdapter<XlsWebExporter>,
		Ordered {
	
	/* (非 Javadoc)  
	 * <p>Title: supports</p>  
	 * <p>Description: </p>  
	 * @param type
	 * @return  
	 * @see com.gsoft.framework.core.web.export.WebExportAdapter#supports(java.lang.String)  
	 */  
	@Override
	public boolean supports(String type) {
		return "xls".equals(type);
	}

	
	/* (非 Javadoc)  
	 * <p>Title: openExporter</p>  
	 * <p>Description: </p>  
	 * @param outputStream
	 * @param type
	 * @return  
	 * @see com.gsoft.framework.core.web.export.WebExportAdapter#openExporter(java.io.OutputStream, java.lang.String)  
	 */  
	@Override
	public XlsWebExporter openExporter(OutputStream outputStream,String type) {
		return new XlsWebExporter(outputStream);
	}

	@Override
	public int getOrder() {
		return 0;
	}
}