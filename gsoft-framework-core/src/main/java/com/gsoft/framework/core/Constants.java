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
package com.gsoft.framework.core;

import com.gsoft.framework.core.context.Config;
import com.gsoft.framework.util.StringUtils;

/**
 * 常量
 * 
 * @author liupantao
 * @date 2017年10月16日
 * 
 */
public class Constants {

	private final static String DEFAULT_WEB_ROOT_KEY = "youi.root";

	public static final String APP_PATH = null;

	/**
	 * @Fields WEB_ROOT : web根路径 
	 */
	public static String WEB_ROOT = null;

	public static String SESSION_DECORATOR = "SESSION_DECORATOR";

	/**
	 * @Fields SESSION_THEME : 样式
	 */
	public static String SESSION_THEME = "SESSION_THEME";

	/**
	 * @Fields PARAMETER_PAGE_ID : 页面ID参数名称
	 */
	public static String PARAMETER_PAGE_ID = "_pageId";

	// 异常代码
	/**
	 * @Fields SUCCESS_CODE : 访问成功
	 */
	public static final String SUCCESS_CODE = "000000";

	/**
	 * @Fields ACCESS_DENIED_CODE : 拒绝访问 
	 */
	public static final String ACCESS_DENIED_CODE = "111111";

	/**
	 * @Fields ERROR_DOMAIN_VALIDATOR : domain对象校验不通过
	 */
	public static final String ERROR_DOMAIN_VALIDATOR = "111112";

	/**
	 * @Fields ERROR_DEFAULT_CODE : 默认错误代码 
	 */
	public static final String ERROR_DEFAULT_CODE = "999999";

	/************************* begin 配置项名称 *************************/
	/**
	 * @Fields PROP_CONVERTSERVICE_BEAN : 转换服务配置服务名称（spring）
	 */
	public static final String PROP_CONVERTSERVICE_BEAN = "convertService.bean";
	/**
	 * @Fields PROP_TRANSLOG_BEAN : 日记服务名称（spring）
	 */
	public static final String PROP_TRANSLOG_BEAN = "transLog.bean";


	/**
	 * @Fields PROP_TRACE_ERROR : 输出异常轨迹 
	 */
	public static final String PROP_TRACE_ERROR = "trace.log";
	/**
	 * @Fields PROP_LOGIN_RANDCODE : 校验码启用配置
	 */
	public static final String PROP_LOGIN_RANDCODE = "login.randCode";

	/************************* end 配置项名称 *************************/

	/******************************* URL后缀 *******************************/
	public static final String PAGE_URL_POSTFIX = "html";
	public static final String DATA_URL_POSTFIX = "json";

	public static final String PROPERTY_VALUE_NULL = "PROPERTY_VALUE_NULL";

	/******************************* 流程相关 *******************************/
	/**
	 * @Fields WORKFLOW_STATUS_START : 流程启动状态
	 */
	public static final String WORKFLOW_STATUS_START = "start";

	/**
	 * 得到当前环境的根目录 首先从环境变量中查找 未找到则使用静态变量WEB_ROOT
	 * 
	 * @return
	 */
	public static String getWebRoot() {
		if (WEB_ROOT != null) {
			return WEB_ROOT;
		}
		// 从环境变量中查找
		String path = System.getProperty(getWebRootKey());
		WEB_ROOT = path;
		return WEB_ROOT;
	}

	/**
	 * 设置根路径
	 * 
	 * @param webRoot
	 */
	public static void setWebRoot(String webRoot) {
		WEB_ROOT = webRoot;
	}

	private static String getWebRootKey() {
		String webRootkey = Config.getInstance().getProperty("webroot.key");
		return StringUtils.isEmpty(webRootkey) ? DEFAULT_WEB_ROOT_KEY : webRootkey;
	}
}
