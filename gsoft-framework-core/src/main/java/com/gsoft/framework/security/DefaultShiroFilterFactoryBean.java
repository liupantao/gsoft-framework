/*
 * YOUI框架
 * Copyright 2012 the original author or authors.
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
package com.gsoft.framework.security;


import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;

import com.gsoft.framework.util.CollectionUtils;

/**
 * DefaultShiroFilterFactoryBean
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class DefaultShiroFilterFactoryBean extends ShiroFilterFactoryBean {

	/**
	 * @Fields urlResourceManager : URL资源管理类
	 */
	private UrlRecourceManager urlResourceManager;
	
	
	/**
	 * @param urlResourceManager the urlResourceManager to set
	 */
	public void setUrlResourceManager(UrlRecourceManager urlResourceManager) {
		this.urlResourceManager = urlResourceManager;
	}


	/* (non-Javadoc)
	 * @see org.apache.shiro.spring.web.ShiroFilterFactoryBean#createFilterChainManager()
	 */
	@Override
	protected FilterChainManager createFilterChainManager() {
		FilterChainManager manager = super.createFilterChainManager();
		if(urlResourceManager==null){
			urlResourceManager = new DefaultUrlRecourceManager();
		}
		 //build up the chains:
        Map<String, String> chains = urlResourceManager.getFilterChainDefinitionMap();
        if (!CollectionUtils.isEmpty(chains)) {
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue();
                manager.createChain(url, chainDefinition);
            }
        }
		return manager;
	}

}
