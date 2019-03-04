/*
 * Gsoft开发框架
 * Copyright 2015-2020 the original author or authors.
 */

package com.gsoft.framework.util;

/**
 * Boolean工具类
 * 
 * @author liupantao
 * @date 2017年10月20日
 * 
 */
public class BooleanUtils {

	/**
	 * 字符串转boolean true,on,yes,1 -> 转换为true
	 * 
	 * @param s
	 * @return
	 */
	public static Boolean valueOf(String s) {
		return (s != null && (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("on") || s.equalsIgnoreCase("yes") || "1".equals(s)));
	}

}
