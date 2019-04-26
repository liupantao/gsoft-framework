/**
 * 
 */
package com.gsoft.framework.security;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 登录表单服务
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class DefaultLoginFormToken extends UsernamePasswordToken implements IRealmUserToken{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8866503207510464030L;

	/**
	 * @Fields verification : 校验码校验结果
	 */
	private boolean verification = true;

	/**
	 * @Fields dynamicCode : 动态码
	 */
	private String dynamicCode;

	/**
	 * @Fields isDynamicCheck : 是否开启动态码校验
	 */
	private boolean isDynamicCheck;

	/**
	 * @Fields contextPath : 上下文路径
	 */
	private String contextPath;

	/**
	 * @Fields authParam : 其他登录参数
	 */
	private String authParam;

	private String loginType;
	private String osType;
	private String redirect;

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public DefaultLoginFormToken(String username, String password, boolean rememberMe, String host) {
		super(username, password, rememberMe, host);
	}

	/**
	 * @return the verificationFlag
	 */
	public boolean isVerification() {
		return verification;
	}

	/**
	 * @param verificationFlag
	 *            the verificationFlag to set
	 */
	public void setVerification(boolean verification) {
		this.verification = verification;
	}

	public String getDynamicCode() {
		return dynamicCode;
	}

	public void setDynamicCode(String dynamicCode) {
		this.dynamicCode = dynamicCode;
	}

	public boolean isDynamicCheck() {
		return isDynamicCheck;
	}

	public void setDynamicCheck(boolean isDynamicCheck) {
		this.isDynamicCheck = isDynamicCheck;
	}

	/**
	 * @return the authParam
	 */
	public String getAuthParam() {
		return authParam;
	}

	/**
	 * @param authParam
	 *            the authParam to set
	 */
	public void setAuthParam(String authParam) {
		this.authParam = authParam;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

}
