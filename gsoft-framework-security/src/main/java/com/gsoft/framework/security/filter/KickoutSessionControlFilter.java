/*
 * Gsoft开发框架
 * Copyright 2015-2020 the original author or authors.
 */

package com.gsoft.framework.security.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;

import com.gsoft.framework.security.IUser;

/**
 * 踢人filter
 * 
 * @author LiuPeng
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
		try {
			Map<String, Object> params = new HashMap<String, Object>(8);
			params.put("error", request.getAttribute("error"));
			WebUtils.issueRedirect(request, response, "/login.html", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
