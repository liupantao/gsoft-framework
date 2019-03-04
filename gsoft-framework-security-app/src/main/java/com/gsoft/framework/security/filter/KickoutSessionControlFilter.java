/*
 * Gsoft开发框架
 * Copyright 2015-2020 the original author or authors.
 */

package com.gsoft.framework.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;

import com.gsoft.framework.core.web.view.Message;
import com.gsoft.framework.security.AppResConstants;
import com.gsoft.framework.security.IUser;
import com.gsoft.framework.security.authc.AppResContext;
import com.gsoft.framework.security.util.ResJsonUtils;

/**
 * 踢人filter
 * 
 * @author liupantao
 * @date 2018年7月23日
 * 
 */
public class KickoutSessionControlFilter extends AbstractKickoutSessionControlFilter {

	@Override
	protected String getKickoutUsername(Subject subject) {
		Object principal = subject.getPrincipal();
		if (principal != null && principal instanceof IUser) {
			IUser user = (IUser) principal;
			return user.getUserId();
		}
		return subject.getPrincipal().toString();
	}

	@Override
	protected void afterKickout(ServletRequest request, ServletResponse response) {
		Object errorMsg = request.getAttribute("error");
		AppResContext resContext = new AppResContext();
		resContext.setMessage(
				new Message(AppResConstants.ERROR_TOKEN, errorMsg == null ? "token认证失败." : errorMsg.toString()));
		ResJsonUtils.resJson(request, response, resContext);
	}

}
