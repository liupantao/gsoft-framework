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
package com.gsoft.framework.core.web;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * 
 * @author liupantao
 *
 */
public class PageScriptFactory {

	private CacheManager cacheManager;

	private final static String PAGE_SCRIPT_CACHE_KEY = "com.g.f.c.w.PageScriptFactory_cache";
	/**
	 * @param cacheManager
	 *            the cacheManager to set
	 */
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public String getPageScript(String sessionId, String pageId) {
		Cache scriptCache = cacheManager.getCache(PAGE_SCRIPT_CACHE_KEY);

		return scriptCache.get(pageId + sessionId, String.class);
	}

	public void addPageScript(String sessionId, String pageId, String pageScript) {
		Cache scriptCache = cacheManager.getCache(PAGE_SCRIPT_CACHE_KEY);
		scriptCache.put(pageId + sessionId, pageScript);
	}

}
