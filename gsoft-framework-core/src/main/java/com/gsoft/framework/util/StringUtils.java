/*

 * @(#)StringUtils.java  1.0.0 下午04:11:47

 * Copyright 2013 gicom, Inc. All rights reserved.

 */
package com.gsoft.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringUtils
 * 
 * @author liupantao
 * @date 2017年10月17日
 * 
 */
public class StringUtils extends org.springframework.util.StringUtils {

	private final static int FLAG_BEG = 1;
	private final static int FLAG_END = 9;

	/** 
	 * join
	 * @param o
	 * @param flag
	 * @return 
	 */
	public static String join(String[] o, String flag) {
		StringBuffer str_buff = new StringBuffer();
		for (int i = 0, len = o.length; i < len; i++) {
			str_buff.append(o[i]);
			if (i < len - 1)
				str_buff.append(flag);
		}
		return str_buff.toString();
	}

	/**
	 * null或空字符的串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.equals("");
	}

	/**
	 * 非null和空字符的串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String upperCaseFirstLetter(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	/**
	 * 首字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String lowerCaseFirstLetter(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	/**
	 * @param strs
	 * @return
	 */
	public static String findNotEmpty(String... strs) {
		for (String str : strs) {
			if (isNotEmpty(str)) {
				return str;
			}
		}
		return null;
	}

	/**
	 * &#...;格式的10进制串转换成中文
	 * 
	 * @param str
	 * @return
	 */
	public static String decodeXmlText(String str) {
		if (isEmpty(str)) {
			return null2Empty(str);
		}

		StringBuffer results = new StringBuffer();
		int mflag = -1;
		int begPos = 0, endPos = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (i > 0 && c == '#' && str.substring(i - 1, i + 1).equals("&#")) {
				// 开始计算
				mflag = FLAG_BEG;
				begPos = i;
			} else if (c == ';' && mflag == FLAG_BEG) {
				mflag = FLAG_END;
				endPos = i;
			}
			if (mflag == FLAG_BEG) {
				// 跳过
			} else if (mflag == FLAG_END && endPos > begPos) {
				results.deleteCharAt(results.length() - 1);
				results.append(
						String.valueOf((char) Integer.valueOf(str.substring(begPos + 1, endPos), 10).intValue()));
				mflag = -1;
			} else {
				results.append(c);
			}
		}
		return results.toString();
	}

	/**
	 * null转空
	 * 
	 * @param str
	 * @return
	 */
	public static String null2Empty(String str) {
		return str == null ? "" : str;
	}

	public static String prefixUpperAnd2Lower(String str, String prefix) {
		if (str == null) {
			return null;
		}

		String word = str + prefix;

		String patternRegex = "[A-Z]";
		Pattern pattern = Pattern.compile(patternRegex);
		Matcher matcher = pattern.matcher(word);
		String[] results = pattern.split(word);

		if ((results.length == 2) && (str.length() == 1)) {
			return str.toLowerCase();
		}

		int index = 1;
		while ((matcher.find()) && (index < results.length)) {
			results[index] = (prefix + matcher.group().toLowerCase() + results[index]);
			++index;
		}
		String result = arrayToDelimitedString(results, "");

		return result.substring(0, result.length() - 1);
	}
}
