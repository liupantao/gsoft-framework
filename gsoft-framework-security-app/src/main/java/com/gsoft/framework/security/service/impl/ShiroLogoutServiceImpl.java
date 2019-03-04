package com.gsoft.framework.security.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.gsoft.framework.security.service.AbstractShiroLogoutService;

/**
 * 
 * @author liupantao
 *
 */
@Service("shiroLogoutService")
public class ShiroLogoutServiceImpl extends AbstractShiroLogoutService{
	
	@Override
	protected void afterLogout(HttpServletRequest request, HttpServletResponse response) {

	}
	
}
