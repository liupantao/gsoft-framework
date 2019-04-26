package com.gsoft.framework.security.authc;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;

import com.gsoft.framework.core.exception.BusException;
import com.gsoft.framework.core.web.view.Message;
import com.gsoft.framework.security.AccountPrincipal;
import com.gsoft.framework.security.IUser;
import com.gsoft.framework.security.VerificationCodeException;
import com.gsoft.framework.security.util.ResJsonUtils;
import com.gsoft.framework.util.PropertyUtils;

/**
 * 登录认证
 * 
 * @author liupantao
 * 
 */
public class ApiGatewayAuthenticationFilter extends DefaultFormAuthenticationFilter {

	private Log logger = LogFactory.getLog(ApiGatewayAuthenticationFilter.class);

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {

		AccountPrincipal accountPrincipal = com.gsoft.framework.util.SecurityUtils.getAccount();
		if (accountPrincipal instanceof IUser) {
			AppResContext context = new AppResContext();
			context.setToken(WebUtils.toHttp(request).getSession().getId());
			context.setRecord((IUser) accountPrincipal);
			String rongcloudAppkey = null;
			String rongcloudToken = null;

			try {
				Object rongAppKey = PropertyUtils.getPropertyValue(accountPrincipal, "rongcloudAppkey");
				Object rongToken = PropertyUtils.getPropertyValue(accountPrincipal, "rongcloudToken");
				if (rongAppKey != null) {
					rongcloudAppkey = rongAppKey.toString();
				}
				if (rongToken != null) {
					rongcloudToken = rongToken.toString();
				}
			} catch (Exception e) {
			}
			context.setRongcloudAppkey(rongcloudAppkey);
			context.setRongcloudToken(rongcloudToken);

			ResJsonUtils.resJson(request, response, context);
			logger.info(accountPrincipal.getLoginName() + " 登录成功!");
		}
		if (loginLogService != null) {
			loginLogService.onLoginSuccess(token, subject, request, response);
		}
		return false;
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {

		String errorMsg = getFailureMessage(request, e);
		AppResContext context = new AppResContext();
		context.setMessage(new Message("999999", errorMsg));
		ResJsonUtils.resJson(request, response, context);

		if (loginLogService != null) {
			loginLogService.onLoginFailure(token, e, request, response);
		}
		return false;
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

	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		ResJsonUtils.resTokenError(request, response);
	}

}