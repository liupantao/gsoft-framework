package com.gsoft.framework.security;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * IRealmUserToken
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public interface IRealmUserToken extends AuthenticationToken {
	/** 
	 * 获取用户名
	 * @return 
	 */
	public String getUsername();
}