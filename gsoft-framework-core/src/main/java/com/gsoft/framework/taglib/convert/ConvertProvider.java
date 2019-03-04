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

import java.util.Locale;

import com.gsoft.framework.core.convert.IConvert;

/**
 * 字典转换提供类
 * @author liupantao
 * @date 2017年10月17日
 *  
 */
public interface ConvertProvider {
	
	/** 
	 * 根据代码集名称及国际化参数获取代码集
	 * @param name
	 * @param locale
	 * @return 
	 */
	public IConvert<?> getConvert(String name,Locale locale);
	
}
