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
package com.gsoft.framework.taglib.convert;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ResourceLoader;

import com.gsoft.framework.core.convert.IConvert;
import com.gsoft.framework.core.convert.IConvertProviderFactory;

/**
 * 
 * @author liupantao
 * 
 */
public class ConvertProviderFactory implements IConvertProviderFactory, ApplicationContextAware {

	private List<ConvertProvider> providers;

	private ResourceLoader resourceLoader;

	private CacheManager cacheManager;

	private final static String CONVERT_CACHE_NAME = "com.g.f.t.convert_cache";

	@Autowired(required = false)
	private DBConvertProvider dbConvertProvider;

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	/**
	 * 刷新缓存
	 * 
	 * @param name
	 */
	public void refreshCached(String name) {
		if (this.cacheManager != null) {
			Cache cachedConvert = this.cacheManager.getCache(CONVERT_CACHE_NAME);
			cachedConvert.evict(name);
		}
	}

	@Override
	public IConvert<?> getConvert(String name, Locale locale) {
		IConvert<?> convert = null;
		if (this.cacheManager != null) {
			Cache cachedConvert = this.cacheManager.getCache(CONVERT_CACHE_NAME);
			convert = cachedConvert.get(name, IConvert.class);
			if (convert == null) {
				convert = loadConvert(name, locale);
				if (convert != null) {
					cachedConvert.put(name, convert);
				}
			}
		} else {
			convert = loadConvert(name, locale);
		}
		return convert;
	}

	private IConvert<?> loadConvert(String name, Locale locale) {
		IConvert<?> convert = null;
		for (ConvertProvider provider : this.providers) {
			if (provider instanceof DefaultConvertProvider) {
				((DefaultConvertProvider) provider).setResourceLoader(this.resourceLoader);
			}
			convert = provider.getConvert(name, locale);
			if (convert != null) {
				return convert;
			}
		}
		return convert;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		initRealmProviders(applicationContext);
	}

	/**
	 * 初始化 代码集 提供类
	 * 
	 * @param context
	 */
	private void initRealmProviders(ApplicationContext context) {
		if (this.providers == null) {
			this.providers = new ArrayList<ConvertProvider>();
			Map<String, ConvertProvider> userAdapterMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, ConvertProvider.class, true, false);
			for (Map.Entry<String, ConvertProvider> entry : userAdapterMap.entrySet()) {
				if (entry.getValue() instanceof DBConvertProvider) {
				} else {
					this.providers.add(entry.getValue());
				}
			}
			// DBConvertProvider
			if (dbConvertProvider != null) {
				this.providers.add(dbConvertProvider);
			}
		}
	}
}
