/*
 * Gsoft开发框架
 * Copyright 2015-2020 the original author or authors.
 */

package com.gsoft.framework.core.log;

import java.io.Serializable;

/**
 * 日志
 * 
 * @author liupantao
 * @date 2018年1月23日
 * 
 */
public class LogInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @Fields 流水号
	 */
	private String flowno;

	/**
	 * @Fields 操作用户名
	 */
	private String username;

	/**
	 * @Fields 操作用户ip
	 */
	private String ip;

	/**
	 * @Fields 服务名
	 */
	private String serviceName;

	/**
	 * @Fields 方法名
	 */
	private String methodName;

	/**
	 * @Fields 交易码
	 */
	private String trancode;

	/**
	 * @Fields 交易名
	 */
	private String tranname;

	/**
	 * @Fields 耗时
	 */
	private long time = 0L;

	/**
	 * @Fields 是否记录日志
	 */
	private boolean log;

	/**
	 * 创建一个新的实例 LogInfo.
	 *
	 * @param flowno
	 * @param username
	 * @param tranname
	 * @param rescode
	 * @param resMsg
	 * @param time
	 * @param ip
	 * @param details
	 */
	public LogInfo(String flowno, String username, String ip, String trancode, String tranname) {
		super();
		this.flowno = flowno;
		this.username = username;
		this.ip = ip;
		this.trancode = trancode;
		this.tranname = tranname;
	}

	/**
	 * @return the flowno
	 */
	public String getFlowno() {
		return flowno;
	}

	/**
	 * @param flowno
	 *            the flowno to set
	 */
	public void setFlowno(String flowno) {
		this.flowno = flowno;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName
	 *            the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName
	 *            the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return the trancode
	 */
	public String getTrancode() {
		return trancode;
	}

	/**
	 * @param trancode
	 *            the trancode to set
	 */
	public void setTrancode(String trancode) {
		this.trancode = trancode;
	}

	/**
	 * @return the tranname
	 */
	public String getTranname() {
		return tranname;
	}

	/**
	 * @param tranname
	 *            the tranname to set
	 */
	public void setTranname(String tranname) {
		this.tranname = tranname;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the log
	 */
	public boolean isLog() {
		return log;
	}

	/**
	 * @param log
	 *            the log to set
	 */
	public void setLog(boolean log) {
		this.log = log;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		buildToString(builder, "FlowNo", flowno);
		buildToString(builder, "TranCode", trancode);
		buildToString(builder, "TranName", tranname);
		buildToString(builder, "Service", serviceName + "." + methodName);
		buildToString(builder, "IP", ip);
		buildToString(builder, "Time", String.valueOf(time));
		return builder.toString();
	}

	private void buildToString(StringBuilder builder, String name, String value) {
		builder.append("[");
		builder.append(name);
		builder.append(" : ");
		builder.append(value);
		builder.append("]");
	}

}
