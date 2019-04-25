package com.gsoft.framework.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import com.gsoft.framework.context.annotation.ModuleScan;
import com.gsoft.framework.context.config.AppConfigFileListener;
import com.gsoft.framework.webcontext.autoconfigure.WebUiConfig;


/**
 * 启动
 * 
 * @author LiuPeng
 *
 */
@SpringBootApplication
@ModuleScan()
public class AppApplication {

	 private final static Log logger = LogFactory.getLog(AppApplication.class);
	    public static void main(String[] args) {

	        ConfigurableApplicationContext context = SpringApplication.run(AppApplication.class, args);
	        context.addApplicationListener(new AppConfigFileListener());
	        logger.info("****************項目啟動");
	        ConfigurableEnvironment environment = context.getEnvironment();
	        String property1 = environment.getProperty("server.ports");
	        String property2 = environment.getProperty("localhost.server.ports");
	        MutablePropertySources propertySources = environment.getPropertySources();
	        //propertySources.get
	        PropertySource<?> propertySource = propertySources.get("config-security.properties");
	        logger.info("****************項目啟動"+propertySource.toString());
	    }

	
	
}