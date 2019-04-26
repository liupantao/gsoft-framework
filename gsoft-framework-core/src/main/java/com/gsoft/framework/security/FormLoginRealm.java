package com.gsoft.framework.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

/**
 * TODO (描述该文件做什么)
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class FormLoginRealm extends AuthorizingRealm implements ApplicationContextAware {
	private UserService userService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		DefaultLoginFormToken formToken = (DefaultLoginFormToken) token;

		if (!formToken.isVerification()) {
			throw new VerificationCodeException();
		}

		if (StringUtils.isEmpty(formToken.getPassword())) {
			throw new CredentialsException("密码不能为空!");
		}

		IRealmUserInfo userInfo = this.userService.getRealmUserInfo(formToken, true);

		if (userInfo == null) {
			throw new UnknownAccountException("用户" + token.getPrincipal() + "不存在!");
		}

		return userInfo;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Object principal = principals.getPrimaryPrincipal();

		if (principal instanceof IUser) {
			return this.userService.getRealmUserInfo((IUser) principal);
		}
		return new SimpleAuthorizationInfo();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		initApplication(applicationContext);
	}

	private void initApplication(ApplicationContext context) {
		setAuthenticationTokenClass(DefaultLoginFormToken.class);
	}
}