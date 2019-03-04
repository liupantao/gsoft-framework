/**
 * 
 */
package com.gsoft.framework.remote.data;

import java.util.HashMap;
import java.util.Map;

import com.gsoft.framework.util.StringUtils;

/**
 * 
 * @author liupantao
 * 
 */
public class PubContext implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8278975025549567107L;

	private String username;

	private Map<String, Object> params = new HashMap<String, Object>();

	public PubContext() {
	}

	public void addParam(String paramName, Object paramValue) {
		if (StringUtils.isNotEmpty(paramName) && paramValue != null) {
			params.put(paramName, paramValue);
		}
	}

	public void addParams(Map<String, Object> params) {
		if (params != null) {
			this.params.putAll(params);
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
