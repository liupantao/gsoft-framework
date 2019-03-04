package com.gsoft.framework.security.authc;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Hash;

/**
 * 密码匹配服务
 * 
 * @author liupantao
 *
 */
public class UserHashedCredentialsMatcher extends HashedCredentialsMatcher {

	/**
	 * 密码加密
	 * @param credentials
	 * @return
	 */
	public Hash hashPassword(Object password) {
		return this.hashProvidedCredentials(password, null, getHashIterations());
	}
}
