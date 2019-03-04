/**
 * 
 */
package com.gsoft.framework.remote.data;

import java.util.List;

import org.springframework.util.LinkedMultiValueMap;

/**
 * 
 * @author liupantao
 *
 * @param <V>
 */
public class ReqContext<V> extends LinkedMultiValueMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6862991049418613486L;

	private String authorization;

	public ReqContext() {

	}

	/**
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		List<?> values = this.get(key);
		return values == null || values.size() == 0 ? null : values.get(0).toString();
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

}
