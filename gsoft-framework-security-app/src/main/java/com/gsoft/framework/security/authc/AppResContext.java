package com.gsoft.framework.security.authc;

import com.gsoft.framework.core.dataobj.Domain;
import com.gsoft.framework.remote.data.ResContext;

/**
 * app登录返回
 * 
 * @author liupantao
 *
 */
public class AppResContext extends ResContext<Domain> {

	private static final long serialVersionUID = -4597398471057855080L;

	private String token;

	private String rongcloudAppkey;
	
	private String rongcloudToken;
	
	@Override
	public void setRecord(Domain record) {
		super.setRecord(record);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRongcloudAppkey() {
		return rongcloudAppkey;
	}

	public void setRongcloudAppkey(String rongcloudAppkey) {
		this.rongcloudAppkey = rongcloudAppkey;
	}

	public String getRongcloudToken() {
		return rongcloudToken;
	}

	public void setRongcloudToken(String rongcloudToken) {
		this.rongcloudToken = rongcloudToken;
	}

}
