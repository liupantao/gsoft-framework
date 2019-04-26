/*
 * Gsoft开发框架
 * Copyright 2015-2020 the original author or authors.
 */

package com.gsoft.framework.security.service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;

/**
 * 登录日志接口
 * 
 * @author liupeng
 * @date 2018年4月28日
 * 
 */
public interface LoginLogService {

	/**
	 * 登录成功
	 * 
	 * @param token
	 * @param subject
	 * @param request
	 * @param response
	 */
	public void onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response);

	/** 
	 * 登录失败
	 * @param token
	 * @param e
	 * @param request
	 * @param response 
	 */
	public void onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response);
	
	/** 
	 * 退出
	 * @param request
	 * @param response 
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response);
}
