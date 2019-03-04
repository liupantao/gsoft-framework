package com.gsoft.framework.security.session;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

/**
 * app session
 * 
 * @author liupantao
 *
 */
public class AppWebSessionManager extends DefaultWebSessionManager {

	public final static String AUTH_TOKEN = "token";

	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		Serializable id = getSessionIdAuthTokenValue(request, response);
		if (id == null) {
			id = super.getSessionId(request, response);
		}
		return id;
	}

	/**
	 * 从http头 获取token
	 * @param request
	 * @param response
	 * @return
	 */
	private String getSessionIdAuthTokenValue(ServletRequest request, ServletResponse response) {
		if (!(request instanceof HttpServletRequest)) {
			return null;
		}
		return WebUtils.toHttp(request).getHeader(AUTH_TOKEN);
	}

}
