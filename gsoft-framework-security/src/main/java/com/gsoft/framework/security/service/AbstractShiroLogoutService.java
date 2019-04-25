package com.gsoft.framework.security.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsoft.framework.security.LogoutService;

/**
 * 
 * @author LiuPeng
 *
 */
public abstract class AbstractShiroLogoutService implements LogoutService{

	@Autowired(required=false)
	private LoginLogService loginLogService;
	
	@Override
	public final void logout(HttpServletRequest request, HttpServletResponse response) {
		if(loginLogService!=null){
			loginLogService.logout(request, response);
		}
		org.apache.shiro.SecurityUtils.getSubject().logout();
		afterLogout(request, response);
	}
	
	/** 
	 * 退出完成处理 
	 * @param request
	 * @param response 
	 */
	protected void afterLogout(HttpServletRequest request, HttpServletResponse response){
		try {
			WebUtils.issueRedirect(request, response, "/login.html");
		} catch (IOException e) {
		}
	}

}
