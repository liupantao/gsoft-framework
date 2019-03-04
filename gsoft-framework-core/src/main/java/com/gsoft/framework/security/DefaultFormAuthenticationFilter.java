package com.gsoft.framework.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.gsoft.framework.core.Constants;
import com.gsoft.framework.core.exception.BusException;

/**
 * 登录认证
 * 
 * @author liupantao
 * 
 */
public class DefaultFormAuthenticationFilter extends FormAuthenticationFilter {
	public static final String DEFAULT_DYNAMICCODE_PARAM = "dynamicCode";
	public static final String DEFAULT_VERIFICATIONCODE_PARAM = "verificationCode";
	public static final String DEFAULT_LOGINTYPE_PARAM = "loginType";
	private boolean dynamicCheck;
	private boolean vcodeCheck;
	private String authParam;
	private int sessionTimeOut = 1800;

	public DefaultFormAuthenticationFilter() {
		this.dynamicCheck = false;

		this.vcodeCheck = false;

		this.authParam = "smsCodeAuth";
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		DefaultLoginFormToken token = new DefaultLoginFormToken(getUsername(request), getPassword(request),
				isRememberMe(request), getHost(request));

		token.setDynamicCode(getDynamicCode(request));

		token.setDynamicCheck(this.dynamicCheck);
		token.setAuthParam(getAuthParam(request));
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		token.setContextPath(httpRequest.getContextPath());

		if (this.vcodeCheck) {
			String vCode = getVerificationCode(request);
			Object sessionRandCode = httpRequest.getSession().getAttribute("KAPTCHA_SESSION_KEY");

			if ((sessionRandCode == null) || (!sessionRandCode.equals(vCode))) {
				token.setVerification(false);
			}
		}
		return token;
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {

		String isJson = request.getParameter(Constants.DATA_URL_POSTFIX);

		if (Boolean.valueOf(isJson).booleanValue() == true) {
			Map<String, Object> params = new HashMap<String, Object>(8);
			params.put("principal", token.getPrincipal());
			params.put("username", getUsername(request));
			WebUtils.issueRedirect(request, response, "/common/loginSuccess.json", params);
			return true;
		}
		// 设置session超时时间
		SecurityUtils.getSubject().getSession().setTimeout(1000 * sessionTimeOut);

		return super.onLoginSuccess(token, subject, request, response);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {

		String isJson = request.getParameter(Constants.DATA_URL_POSTFIX);

		if (Boolean.valueOf(isJson).booleanValue() == true) {
			setFailureAttribute(request, e);
			Map<String, Object> params = new HashMap<String, Object>(8);
			params.put("error", request.getAttribute("error"));
			params.put("username", getUsername(request));
			try {
				WebUtils.issueRedirect(request, response, "/common/loginFailed.json", params);
				return true;
			} catch (IOException ioe) {
			}
		}
		return super.onLoginFailure(token, e, request, response);
	}

	private String getVerificationCode(ServletRequest request) {
		return WebUtils.getCleanParam(request, DEFAULT_VERIFICATIONCODE_PARAM);
	}

	private String getDynamicCode(ServletRequest request) {
		return WebUtils.getCleanParam(request, DEFAULT_DYNAMICCODE_PARAM);
	}

	private String getAuthParam(ServletRequest request) {
		return WebUtils.getCleanParam(request, getAuthParam());
	}

	@Override
	protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
		super.setFailureAttribute(request, ae);
		request.setAttribute("error", getFailureMessage(request, ae));
	}

	private String getFailureMessage(ServletRequest request, AuthenticationException ae) {
		String username = getUsername(request);
		String message;
		if (ae instanceof IncorrectCredentialsException) {
			message = "密码不正确！";
		} else if (ae instanceof VerificationCodeException) {
			message = "验证码错误！";
		} else if (ae instanceof CredentialsException) {
			message = ae.getMessage();
		} else if (ae instanceof UnknownAccountException) {
			message = "用户[" + username + "]不存在！";
		} else if (ae instanceof AuthenticationException) {
			Throwable busException = ae.getCause();
			if (busException != null && busException instanceof BusException) {
				message = ae.getCause().getMessage();
			} else {
				message = "用户[" + username + "]不可登录！";
			}
		} else {
			message = "登陆失败:" + ae.getMessage();
		}
		return message;
	}

	public void setDynamicCheck(boolean dynamicCheck) {
		this.dynamicCheck = dynamicCheck;
	}

	public void setVcodeCheck(boolean vcodeCheck) {
		this.vcodeCheck = vcodeCheck;
	}

	public String getAuthParam() {
		return this.authParam;
	}

	public void setAuthParam(String authParam) {
		this.authParam = authParam;
	}

	public void setSessionTimeOut(int sessionTimeOut) {
		this.sessionTimeOut = sessionTimeOut;
	}

	public boolean isDynamicCheck() {
		return dynamicCheck;
	}

	public boolean isVcodeCheck() {
		return vcodeCheck;
	}

	public int getSessionTimeOut() {
		return sessionTimeOut;
	}

	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		super.redirectToLogin(request, response);
	}
}