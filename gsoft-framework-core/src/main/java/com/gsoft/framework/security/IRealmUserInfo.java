package com.gsoft.framework.security;

import org.apache.shiro.authc.Account;
import org.apache.shiro.authc.MergableAuthenticationInfo;
import org.apache.shiro.authc.SaltedAuthenticationInfo;

/**
 * IRealmUserInfo
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public interface IRealmUserInfo extends Account, MergableAuthenticationInfo, SaltedAuthenticationInfo {
	/** 
	 * 获取用户
	 * @return 
	 */
	public IUser getUser();
}