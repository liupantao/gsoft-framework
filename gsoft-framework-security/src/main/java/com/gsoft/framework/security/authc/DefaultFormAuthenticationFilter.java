package com.gsoft.framework.security.authc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.gsoft.framework.core.exception.BusException;
import com.gsoft.framework.security.AbstractFormUserInfo;
import com.gsoft.framework.security.DefaultLoginFormToken;
import com.gsoft.framework.security.IUser;
import com.gsoft.framework.security.UserService;
import com.gsoft.framework.security.VerificationCodeException;
import com.gsoft.framework.security.service.LoginLogService;

/**
 * 登录认证
 * 
 * @author LiuPeng
 * 
 */
public class DefaultFormAuthenticationFilter extends FormAuthenticationFilter {
	public static final String DEFAULT_DYNAMICCODE_PARAM = "dynamicCode";
	public static final String DEFAULT_VERIFICATIONCODE_PARAM = "verificationCode";
	public static final String DEFAULT_LOGINTYPE_PARAM = "loginType";
	public static final String DEFAULT_OSTYPE_PARAM = "osType";
	public static final String LOGINTYPE_TOKEN = "token";
	private boolean dynamicCheck;
	private boolean vcodeCheck;
	private String authParam;
	private int sessionTimeOut = 1800;
	protected LoginLogService loginLogService;
	private UserService userService;

	public DefaultFormAuthenticationFilter() {
		this.dynamicCheck = false;

		this.vcodeCheck = false;

		this.authParam = "smsCodeAuth";
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		DefaultLoginFormToken token = new DefaultLoginFormToken(getUsername(request), getPassword(request),
				isRememberMe(request), getHost(request));

		// 登录类型
		token.setLoginType(WebUtils.getCleanParam(request, DEFAULT_LOGINTYPE_PARAM));
		token.setOsType(WebUtils.getCleanParam(request, DEFAULT_OSTYPE_PARAM));
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
		if (loginLogService != null) {
			loginLogService.onLoginSuccess(token, subject, request, response);
		}
		//处理token登录
		DefaultLoginFormToken loginToken = (DefaultLoginFormToken)token;
		String loginType = loginToken.getLoginType();
		Object principal = subject.getPrincipal();
		if (principal != null && principal instanceof IUser) {
			IUser user = (IUser) principal;
			AbstractFormUserInfo userInfo = (AbstractFormUserInfo)userService.getRealmUserInfo(user);
			if(LOGINTYPE_TOKEN.equals(loginType)) {
				String tokenCode = userInfo.getTokenCode();
				responseLoginResultByJson(response, true, tokenCode);
				return false;
			}
		}
		return super.onLoginSuccess(token, subject, request, response);
	}
	private void responseLoginResultByJson(ServletResponse response, boolean isSuccess, String token) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; chartset=utf-8");
		PrintWriter writer = response.getWriter();
		Map<String, String> map = new HashMap<String, String>();
		if(isSuccess) {
			map.put("result", "OK");
		}else {
			map.put("result", "NG");
		}
		map.put("token", token);
		writer.write(JSON.toJSONString(map));
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		if (loginLogService != null) {
			loginLogService.onLoginFailure(token, e, request, response);
		}
		//处理token登录
		DefaultLoginFormToken loginToken = (DefaultLoginFormToken)token;
		String loginType = loginToken.getLoginType();
		if(LOGINTYPE_TOKEN.equals(loginType)) {
			try {
				responseLoginResultByJson(response, false, "login fail!");
				return false;
			} catch (IOException e1) {
				e1.printStackTrace();
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

	public LoginLogService getLoginLogService() {
		return loginLogService;
	}

	public void setLoginLogService(LoginLogService loginLogService) {
		this.loginLogService = loginLogService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		if (((HttpServletRequest) request).getRequestURI().endsWith("json")) {
			WebUtils.issueRedirect(request, response, "/common/accessDenied.json");
		} else {
			super.redirectToLogin(request, response);
		}
	}
}