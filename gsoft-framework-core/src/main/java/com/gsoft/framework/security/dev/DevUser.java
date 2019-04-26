package com.gsoft.framework.security.dev;

import java.util.ArrayList;
import java.util.List;

import com.gsoft.framework.core.dataobj.Domain;
import com.gsoft.framework.security.IUser;
import com.gsoft.framework.security.PrincipalConfig;

/**
 * 开发用户
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class DevUser implements IUser, Domain {
	private static final long serialVersionUID = 7216960982949006450L;
	private static List<String> devRoleIds = new ArrayList<String>();
	private String loginName;

	@Override
	public List<String> roleIds() {
		return devRoleIds;
	}

	@Override
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Override
	public PrincipalConfig getPrincipalConfig() {
		return new PrincipalConfig();
	}

	@Override
	public String toString() {
		return this.loginName;
	}

	@Override
	public String getPassword() {
		return null;
	}

	static {
		devRoleIds.add("ROLE_MODULE");
	}

	@Override
	public String getUserId() {
		return "demo-dev";
	}
}