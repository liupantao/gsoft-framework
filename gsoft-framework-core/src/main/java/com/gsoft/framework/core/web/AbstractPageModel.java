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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * AbstractPageModel
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public abstract class AbstractPageModel implements IPageModel{
	
	protected final Log logger = LogFactory.getLog(getClass());

	protected Map<String,?> addModelObject(Map<String, Object> modelMap,String key,Object value){
		if(modelMap==null){
			modelMap = new HashMap<>(16);
		}
		
		modelMap.put(key, value);
		return modelMap;
	}
}
