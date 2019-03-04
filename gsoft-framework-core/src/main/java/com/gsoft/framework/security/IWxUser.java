package com.gsoft.framework.security;

public interface IWxUser extends IUser{
	/** 
	 * 获取微信OpenId
	 * @return 
	 */
	public String getOpenId();
}
