/*
 * YOUI框架
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gsoft.framework.core.context;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;


/**
 * 平台配置文件工厂
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class ConfigFactoryBean implements 
		FactoryBean<Config>,InitializingBean{
	private final static Log logger = LogFactory.getLog(ConfigFactoryBean.class);
	
	/**
	 * @Fields properties : 属性
	 */
	private Properties properties;
	
	/**
	 * @Fields decorators : 布局
	 */
	private Properties decorators;
	
	private Config config;
	
	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * @return the decorators
	 */
	public Properties getDecorators() {
		return decorators;
	}

	/**
	 * @param decorators the decorators to set
	 */
	public void setDecorators(Properties decorators) {
		this.decorators = decorators;
	}

	@Override
	public Config getObject() throws Exception {
		return config;
	}
	
	@Override
	public Class<Config> getObjectType() {
		return Config.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//初始化配置文件
		logger.debug("---------------初始化平台配置文件------------");
		config = Config.init();
		config.setDecorators(decorators);
		config.setProperties(properties);
		String defaultLayout = properties.getProperty(Config.CONIFG_LAYOUT_DEFAULT);
		if(defaultLayout!=null){
			config.setDefaultLayout(defaultLayout);
		}
	}
}
