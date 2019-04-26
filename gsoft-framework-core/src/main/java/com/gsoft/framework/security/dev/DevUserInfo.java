package com.gsoft.framework.security.dev;

import com.gsoft.framework.security.AbstractFormUserInfo;
import com.gsoft.framework.security.IRealmUserInfo;

/**
 * DevUserInfo
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class DevUserInfo extends AbstractFormUserInfo implements IRealmUserInfo {
	private static final long serialVersionUID = -1868802600203187837L;

	public DevUserInfo(DevUser user, String password) {
		super(user, password);
	}
}