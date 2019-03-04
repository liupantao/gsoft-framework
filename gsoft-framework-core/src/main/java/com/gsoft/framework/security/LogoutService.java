package com.gsoft.framework.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author liupantao
 *
 */
/**
 * 登出服务
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public interface LogoutService {
	/** 
	 * 登出
	 * @param request
	 * @param response 
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response);
}
