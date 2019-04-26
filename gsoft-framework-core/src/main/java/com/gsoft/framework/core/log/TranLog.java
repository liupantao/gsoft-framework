package com.gsoft.framework.core.log;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志ThreadLocal
 * 
 * @author liupantao
 *
 */
public class TranLog {

	public static String TRAN_LOG_NAME = "tranName";
	public static String TRAN_LOG_DETAILS = "details";

	private static ThreadLocal<Map<String, String>> localLogMap = new ThreadLocal<Map<String, String>>();

	/** 
	 * 设置交易名称
	 * @param tranName 
	 */
	public static void setTranName(String tranName) {
		Map<String, String> logMap = getLogMap();
		logMap.put(TRAN_LOG_NAME, tranName);
	}

	/** 
	 * 设置交易日志详情,将会覆盖
	 * @param details 
	 */
	public static void setTranDetails(String details) {
		Map<String, String> logMap = getLogMap();
		logMap.put(TRAN_LOG_DETAILS, details);
	}

	/** 
	 * 添加交易日志详情
	 * @param details 
	 */
	public static void addTranDetails(String details) {
		Map<String, String> logMap = getLogMap();
		String oldDetails = logMap.get(TRAN_LOG_DETAILS);
		logMap.put(TRAN_LOG_DETAILS, (oldDetails == null ? "" : oldDetails + ",") + details);
	}

	private static Map<String, String> getLogMap() {
		Map<String, String> logMap = localLogMap.get();
		if (logMap == null) {
			logMap = new HashMap<String, String>(2);
			localLogMap.set(logMap);
		}
		return logMap;
	}

}
