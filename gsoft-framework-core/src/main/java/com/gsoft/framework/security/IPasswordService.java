package com.gsoft.framework.security;

/**
 * 
 * @author liupantao
 *
 */
public interface IPasswordService {

	/** 
	 * 修改密码
	 * @param username
	 * @param password
	 * @param confirmPassword
	 * @param oldPassword 
	 */
	public void modifyPassword(String username, String password, String confirmPassword, String oldPassword);

}