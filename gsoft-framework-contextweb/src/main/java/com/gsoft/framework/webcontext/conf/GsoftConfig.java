package com.gsoft.framework.webcontext.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gsoft.framework.core.log.ConfigRegisterLog;
import com.gsoft.framework.security.UserService;
import com.gsoft.framework.security.UserServiceImpl;
import com.gsoft.framework.taglib.convert.ConvertProviderFactory;
import com.gsoft.framework.taglib.convert.DefaultConvertProvider;

/**
 * 平台 配置
 * 
 * @author liupantao
 * 
 */
@Configuration
public class GsoftConfig {

	@Autowired
	private CacheManager cacheManager;
	
	@Bean(name = "userService")
	public UserService getUserService() {
		UserServiceImpl userService = new UserServiceImpl();
		userService.setCacheManager(cacheManager);
		return userService;
	}

	@Bean
	public ConvertProviderFactory getConvertProviderFactory() {
		ConvertProviderFactory convertProviderFactory = new ConvertProviderFactory();
		convertProviderFactory.setCacheManager(cacheManager);
		ConfigRegisterLog.registeBean(convertProviderFactory, "代码集工厂",this);
		return convertProviderFactory;
	}

	@Bean
	public DefaultConvertProvider getDefaultConvertProvider() {
		DefaultConvertProvider defaultConvertProvider = new DefaultConvertProvider();
		ConfigRegisterLog.registeBean(defaultConvertProvider, "默认代码集提供服务",this);
		return defaultConvertProvider;
	}

}