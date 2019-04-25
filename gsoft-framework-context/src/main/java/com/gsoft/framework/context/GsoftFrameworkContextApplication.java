package com.gsoft.framework.context;

import com.gsoft.framework.context.annotation.ModuleScan;
import com.gsoft.framework.context.config.AppConfigFileListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

@SpringBootApplication
@ModuleScan
public class GsoftFrameworkContextApplication {
    private final static Log logger = LogFactory.getLog(GsoftFrameworkContextApplication.class);
    public static void main(String[] args) {
    	logger.info("------》》context---启动");
    	ConfigurableApplicationContext context = SpringApplication.run(GsoftFrameworkContextApplication.class, args);
        context.addApplicationListener(new AppConfigFileListener());
        logger.info("****************項目啟動");
        ConfigurableEnvironment environment = context.getEnvironment();
        String property1 = environment.getProperty("server.ports");
        String property2 = environment.getProperty("localhost.server.ports");
        MutablePropertySources propertySources = environment.getPropertySources();
        //propertySources.get
        PropertySource<?> propertySource = propertySources.get("config-remote.properties");
        /*Object property = propertySource.getProperty("config-remote.properties");
        Object source = propertySource.getSource();*/
        logger.info("****************項目啟動"+propertySource);
   
    }

}
