package com.gsoft.framework.webcontext.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gsoft.framework.core.log.ConfigRegisterLog;
import com.gsoft.framework.core.web.PageScriptFactory;
import com.gsoft.framework.taglib.layout.LayoutProvider;
import com.gsoft.framework.taglib.resource.PageResourceBundleMessageSource;

/**
 * 前端UI自动配置
 * @author liupantao
 * @date 2017年10月17日
 *  
 */
@Configuration
public class WebUiConfig {

	@Autowired(required=false)
	private CacheManager cacheManager;
	
	@Bean(name = "pageScriptFactory")
	public PageScriptFactory getPageScriptFactory() {
		PageScriptFactory pageScriptFactory = new PageScriptFactory();
		pageScriptFactory.setCacheManager(cacheManager);
		ConfigRegisterLog.registeBean(pageScriptFactory, "页面script方法工厂", this);
		return pageScriptFactory;
	}
	
	@Bean
	public PageResourceBundleMessageSource getPageResourceBundleMessageSource() {
		PageResourceBundleMessageSource pageResourceBundleMessageSource = new PageResourceBundleMessageSource();
		ConfigRegisterLog.registeBean(pageResourceBundleMessageSource, "页面ResourceBundleMessageSource", this);
		return pageResourceBundleMessageSource;
	}

	@Bean
	public LayoutProvider getLayoutProvider() {
		LayoutProvider layoutProvider = new LayoutProvider();
		ConfigRegisterLog.registeBean(layoutProvider, "页面装饰器Provider", this);
		return layoutProvider;
	}
	
	
}
