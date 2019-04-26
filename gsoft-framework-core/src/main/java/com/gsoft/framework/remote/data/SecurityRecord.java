package com.gsoft.framework.remote.data;

import com.gsoft.framework.core.dataobj.Record;

/**
 * SecurityRecord
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class SecurityRecord extends Record {
	private static final long serialVersionUID = 2271343783534676742L;

	public String getLoginName() {
		return (get("loginName") != null) ? get("loginName").toString() : "";
	}

	public void setLoginName(String loginName) {
		put("loginName", loginName);
	}

	public String getSyscode() {
		return (get("syscode") != null) ? get("syscode").toString() : "";
	}

	public void setSyscode(String syscode) {
		put("syscode", syscode);
	}
}