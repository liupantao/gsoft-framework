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
package com.gsoft.framework.core.web.controller;

import com.gsoft.framework.core.Constants;

/**
 * BaseDataController
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class BaseDataController extends BaseController{
	
	/**
	 * @Fields SUCCESS_CODE : 访问成功
	 */
	protected static final String SUCCESS_CODE=Constants.SUCCESS_CODE;
	
	/**
	 * @Fields ACCESS_DENIED_CODE : 拒绝访问
	 */
	protected static final String ACCESS_DENIED_CODE=Constants.ACCESS_DENIED_CODE;
	
	@Override
	public String getUrlType() {
		return BaseController.URL_TYPE_DATA;
	}
}
