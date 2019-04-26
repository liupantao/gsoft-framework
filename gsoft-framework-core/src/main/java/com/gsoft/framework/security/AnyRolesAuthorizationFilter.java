package com.gsoft.framework.security;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/**
 * 安全控制角色过滤
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class AnyRolesAuthorizationFilter extends AuthorizationFilter {
	
	@Override
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
		Subject subject = getSubject(request, response);
		 String[] rolesArray = (String[]) mappedValue;
		if ((rolesArray == null) || (rolesArray.length == 0)) {
			return true;
		}

		for (int i = 0; i < rolesArray.length; ++i) {
			if (subject.hasRole(rolesArray[i])) {
				return true;
			}
		}

		return false;
	}
}