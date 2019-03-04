package com.gsoft.framework.security.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gsoft.framework.security.authc.UserHashedCredentialsMatcher;

/**
 * UserSecurityConfig 配置
 * 
 * @author liupantao
 * 
 */
@Configuration
public class SecurityConfig {

	@Value("${security.hashAlgorithmName:MD5}")
	private String hashAlgorithmName;
	
	@Value("${security.hashIterations:2}")
	private int hashIterations;

	@Bean("credentialsMatcher")
	public UserHashedCredentialsMatcher getCredentialsMatcher() {
		UserHashedCredentialsMatcher credentialsMatcher = new UserHashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName(hashAlgorithmName);
		credentialsMatcher.setHashIterations(hashIterations);
		return credentialsMatcher;
	}

}