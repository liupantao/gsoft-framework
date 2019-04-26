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

import javax.servlet.jsp.tagext.Tag;

import com.gsoft.framework.core.convert.IConvert;

/**
 * 数据字典 容器标签 接口
 * @author liupantao
 * @date 2017年10月17日
 *  
 */
public interface IConvertContainer extends Tag{

	/** 
	 * 页面添加convert
	 * @param convert 
	 */
	public void addPageConvert(String convert);
	
	/** 
	 * 获取convert 
	 * @param convert
	 * @return 
	 */
	public IConvert<?> getConvert(String convert);
	
}
