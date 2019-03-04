package com.gsoft.framework.remote.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.gsoft.framework.core.log.LogInfo;
import com.gsoft.framework.core.log.TransLogService;
import com.gsoft.framework.remote.data.ReqContext;
import com.gsoft.framework.remote.data.ResContext;
import com.gsoft.framework.remote.service.RemoteLog;
import com.gsoft.framework.util.StringUtils;

/**
 * 日志
 * 
 * @author liupantao
 * 
 */
@Component
public class DefaultRemoteLog implements RemoteLog {

	private ExecutorService executorService = Executors.newFixedThreadPool(20);

	@Autowired(required = false)
	private TransLogService transLogService;// 交易日志

	@Value("${remote.log.logDetails:false}")
	private boolean logDetails;

	@Value("${remote.log.warnTime:60000}")
	private long warnTime;

	@Value("${remote.log.web:false}")
	private boolean logWeb;

	@Value("${remote.log.printall:false}")
	private boolean printAll;

	private static final Log logger = LogFactory.getLog(RemoteLog.class);

	@Override
	public void writeWebLog(String userName, String flowno, String serviceCaption, ReqContext<?> req,
			ResContext<?> res) {
		if (logWeb) {
			executorService.submit(new LogWebCommand(userName, flowno, serviceCaption, req, res));
		}
	}

	@Override
	public void writeLog(LogInfo logInfo, ReqContext<?> req, ResContext<?> res) {
		executorService.submit(new LogCommand(logInfo, req, res));
	}

	private StringBuffer buildCaption(StringBuffer logBuf, String name, String value) {
		return buildCaption(logBuf, name, value, false);
	}

	private StringBuffer buildCaption(StringBuffer logBuf, String name, String value, boolean emptyAppend) {
		if (emptyAppend || StringUtils.isNotEmpty(value)) {
			logBuf.append("[" + name + " : " + value + "]");
		}

		return logBuf;
	}

	private class LogWebCommand extends Thread {
		private String userName;
		private String flowno;
		private String serviceCaption;
		private ReqContext<?> req;
		private ResContext<?> res;

		public LogWebCommand(String userName, String flowno, String serviceCaption, ReqContext<?> req,
				ResContext<?> res) {
			super();
			this.userName = userName;
			this.flowno = flowno;
			this.serviceCaption = serviceCaption;
			this.req = req;
			this.res = res;
		}

		@Override
		public void run() {
			StringBuilder details = new StringBuilder();
			if (logDetails) {
				details.append("请求[" + req.toString() + "]");
				if (res != null) {
					details.append(" 响应报文[").append(JSON.toJSON(res) + "]");
				}
			}
			StringBuffer logBuf = new StringBuffer();
			logBuf.append("WEB请求 : ");
			buildCaption(logBuf, "userName", userName, true);
			buildCaption(logBuf, "Flowno", flowno, true);
			buildCaption(logBuf, "ServiceCaption", serviceCaption, true);
			buildCaption(logBuf, "Details", (details != null ? details.toString() : ""));
			logger.info(logBuf.toString());
		}
	}

	private class LogCommand extends Thread {
		private LogInfo logInfo;
		private ReqContext<?> req;
		private ResContext<?> res;

		public LogCommand(LogInfo logInfo, ReqContext<?> req, ResContext<?> res) {
			super();
			this.logInfo = logInfo;
			this.req = req;
			this.res = res;
		}

		@Override
		public void run() {
			boolean warn = false;
			if (logInfo.getTime() > warnTime) {
				warn = true;
			}
			if (warn || logInfo.isLog() || printAll) {
				StringBuilder details = new StringBuilder();
				if (logDetails) {
					details.append("请求[" + req.toString() + "]");
					if (res != null) {
						details.append(" 响应报文[").append(JSON.toJSON(res) + "]");
					}
				}
				StringBuffer logBuf = new StringBuffer();
				logBuf.append("服务调用 : ");
				logBuf.append(logInfo.toString());
				if (logDetails) {
					buildCaption(logBuf, "Details", (details != null ? details.toString() : ""));
				}

				logger.info(logBuf.toString());
				if (transLogService != null && logInfo.isLog()) {
					transLogService.writeLog(logInfo, req, res);
				}
			}
		}
	}

}
