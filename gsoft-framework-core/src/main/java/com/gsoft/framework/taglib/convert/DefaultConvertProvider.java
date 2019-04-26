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

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.io.ResourceLoader;

import com.gsoft.framework.core.convert.IConvert;
import com.gsoft.framework.util.StringUtils;

/**
 * 
 * @author liupantao
 *
 */
public class DefaultConvertProvider extends ApplicationObjectSupport implements ConvertProvider {

	private String location;
	private ResourceLoader resourceLoader;
	private ConvertResourceBundleMessageSource resourceBundle;
	private Map<String, IConvert<?>> converts;

	public DefaultConvertProvider() {
		this.location = "WEB-INF/configs/converts/common";

		this.resourceBundle = null;

		this.converts = Collections.synchronizedMap(new HashMap<String, IConvert<?>>());
	}

	/**
	 * @param resourceLoader
	 *            the resourceLoader to set
	 */
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	/**
	 * @param resource
	 *            the resource to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public IConvert<?> getConvert(String name, Locale locale) {
		if (this.converts != null) {
			IConvert<?> convert = this.converts.get(name);
			if (convert != null) {
				return convert;
			}
		}
		if (this.resourceBundle == null) {
			loadConvert(name, locale);
		}
		return this.resourceBundle.getConvert(name);
	}

	/**
	 * 
	 */
	public void loadConvert(String name, Locale locale) {
		if (resourceBundle == null) {
			resourceBundle = new ConvertResourceBundleMessageSource();
			resourceBundle.setResourceLoader(resourceLoader);
		}
		resourceBundle.loadConverts(location, locale);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void initApplicationContext(ApplicationContext context) throws BeansException {
		super.initApplicationContext(context);
		Map<String, IConvert> converts = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, IConvert.class, true, false);
		for (Map.Entry<String, IConvert> entry : converts.entrySet()) {
			String name = StringUtils.findNotEmpty(new String[] { ((IConvert) entry.getValue()).getName(),
					(String) entry.getKey() });
			this.converts.put(name, entry.getValue());
		}
	}
}
