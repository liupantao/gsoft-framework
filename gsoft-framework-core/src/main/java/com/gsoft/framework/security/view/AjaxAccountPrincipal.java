package com.gsoft.framework.security.view;

import java.io.Serializable;
import java.util.List;

import com.gsoft.framework.security.AccountPrincipal;
import com.gsoft.framework.security.PrincipalConfig;

/**
 * 
 * @author liupantao
 *
 */
public class AjaxAccountPrincipal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7791617876647443716L;
	
	private boolean success;
	private String info;
	
	private String authorization;
	private String loginName;
	private List<String> roles;
	private PrincipalConfig principalConfig;

	
	public AjaxAccountPrincipal(boolean success, String info, String authorization) {
		super();
		this.success = success;
		this.info = info;
		this.authorization = authorization;
	}
	
	public void setAccountPrincipal(AccountPrincipal accountPrincipal) {
		this.loginName = accountPrincipal.getLoginName();
		this.roles = accountPrincipal.roleIds();
		this.principalConfig = accountPrincipal.getPrincipalConfig();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public PrincipalConfig getPrincipalConfig() {
		return principalConfig;
	}

	public void setPrincipalConfig(PrincipalConfig principalConfig) {
		this.principalConfig = principalConfig;
	}

}
