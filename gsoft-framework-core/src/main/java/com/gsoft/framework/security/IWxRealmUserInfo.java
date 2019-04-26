package com.gsoft.framework.security;

import org.apache.shiro.authc.Account;
import org.apache.shiro.authc.MergableAuthenticationInfo;

/**
 * IWxRealmUserInfo
 * @author WangDasong
 * @date 2018年08月24日
 *  
 */
public interface IWxRealmUserInfo extends Account, MergableAuthenticationInfo {
	/** 
	 * 获取用户
	 * @return 
	 */
	public IWxUser getWxUser();
}