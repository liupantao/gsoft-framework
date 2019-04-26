package com.gsoft.framework.core.web.view;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 交易日志抽象类
 * @author liupantao
 * @date 2017年10月16日
 *  
 */
public class AbstractTransLogger {

	private Log logger = LogFactory.getLog(AbstractTransLogger.class);

	protected void writeLog(String tranId, String tranCaption, String details, long useTime, String userIP) {
		StringBuffer logBuf = new StringBuffer();
		buildCaption(logBuf, "交易码", tranId);
		buildCaption(logBuf, "交易名称", tranCaption);
		buildCaption(logBuf, "访问耗时", useTime == 0 ? "" : String.valueOf(useTime));
		buildCaption(logBuf, "访问IP", userIP);
		buildCaption(logBuf, "详细信息", (details != null ? details.toString() : ""));
		logger.info(logBuf.toString());
	}

	private StringBuffer buildCaption(StringBuffer logBuf, String name, String value) {
		return logBuf.append("[" + name + " : " + value + "]");
	}
}
