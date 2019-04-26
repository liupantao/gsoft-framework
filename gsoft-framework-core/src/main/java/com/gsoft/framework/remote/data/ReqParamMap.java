/*
 * Gsoft开发框架
 * Copyright 2015-2020 the original author or authors.
 */

package com.gsoft.framework.remote.data;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * remote请求参数
 * 
 * @author liupantao
 * @date 2018年5月21日
 * 
 */
public class ReqParamMap extends HashMap<String, List<String>> {

	private static final long serialVersionUID = 1L;

	/**
	 * 根据参数名获取参数值
	 * 
	 * @param name
	 * @return
	 */
	public String getParameter(String name) {
		List<String> values = this.get(name);
		if (values == null || values.size() == 0) {
			return null;
		}
		return values.get(0);
	}

	/**
	 * 根据参数名获取参数值集合
	 * 
	 * @param name
	 * @return
	 */
	public List<String> getParameterValues(String name) {
		return this.get(name);
	}

	/**
	 * 获取所有参数名
	 * 
	 * @return
	 */
	public Set<String> getParameterNames() {
		return this.keySet();
	}

}
