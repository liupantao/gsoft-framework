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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;

/**
 * TimeWebRequestInterceptor
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
@Component("webRequestInterceptor")
public class TimeWebRequestInterceptor implements WebRequestInterceptor {
	private  static final Log logger = LogFactory.getLog(TimeWebRequestInterceptor.class);
	
	public static final String TIME_ACCESS = "TIME_ACCESS";
	
	/* (non-Javadoc)
	 * @see org.springframework.web.context.request.WebRequestInterceptor#preHandle(org.springframework.web.context.request.WebRequest)
	 */
	@Override
	public void preHandle(WebRequest request) throws Exception {
		long startTime = System.currentTimeMillis();
		//session开启
		request.setAttribute("TIME_ACCESS", new Long(startTime), WebRequest.SCOPE_REQUEST);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.context.request.WebRequestInterceptor#postHandle(org.springframework.web.context.request.WebRequest, org.springframework.ui.ModelMap)
	 */
	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
		//记录日记
		// session 关闭
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.context.request.WebRequestInterceptor#afterCompletion(org.springframework.web.context.request.WebRequest, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(WebRequest request, Exception ex)
			throws Exception {
		long endTime = System.currentTimeMillis();
		//耗时
		long useTime = 0L;
		Object startTime = request.getAttribute(TIME_ACCESS, WebRequest.SCOPE_REQUEST);
		if(startTime!=null){
			useTime = endTime-(Long)startTime;
		}
		if(ex!=null){
			System.out.println("异常信息："+ex.getMessage());
		}
		
		String uri = "";
		if(request instanceof DispatcherServletWebRequest){
			DispatcherServletWebRequest dsw = (DispatcherServletWebRequest)request;
			uri = dsw.getRequest().getRequestURI();
		}
		if(logger.isDebugEnabled()){
			logger.debug("访问【"+uri+"】耗时"+useTime+"毫秒.");
		}
	}

}
