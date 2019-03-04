/**
 * 
 */
package com.gsoft.framework.remote.data;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.gsoft.framework.core.dataobj.Domain;
import com.gsoft.framework.core.log.LogInfo;
import com.gsoft.framework.core.orm.PagerRecords;
import com.gsoft.framework.core.web.view.Message;

/**
 * 返回
 * 
 * @author liupantao
 *
 * @param <T>
 */
public class ResContext<T extends Domain> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1607997323023348578L;

	private static final String LINE_SPLIT = System.getProperty("line.separator");

	/**
	 * @Fields record : 单记录
	 */
	private T record;

	private String code = "0";

	private String msg;

	/**
	 * @Fields totalCount : 总记录数
	 */
	private long count;

	/**
	 * @Fields 页码
	 */
	private int page;

	/**
	 * @Fields 每页条数
	 */
	private int limit;

	/**
	 * @Fields records : 多记录
	 */
	private List<T> data;

	/**
	 * @Fields instanceId : 流程实例ID
	 */
	private String instanceId;

	private String html;

	/**
	 * @Fields 日志信息
	 */
	@JSONField(serialize = false)
	private LogInfo logInfo;

	/**
	 * @Fields 业务日志信息
	 */
	@JSONField(serialize = false)
	private StringBuilder log = new StringBuilder();

	public ResContext() {
		super();
	}

	public ResContext(Message message) {
		super();
		this.code = message.getCode();
		this.msg = message.getInfo();
	}

	public ResContext(T record) {
		super();
		this.record = record;
	}

	public ResContext(int count, List<T> data) {
		super();
		this.count = count;
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	public void setPagerRecords(PagerRecords pagerRecords) {
		this.data = pagerRecords.getRecords();
		this.count = pagerRecords.getTotalCount();
		if (pagerRecords.getPager() != null) {
			this.page = pagerRecords.getPager().getPageNumber() + 1;
			this.limit = pagerRecords.getPager().getPageSize();
		}
	}

	/**
	 * @param message
	 */
	public void setMessage(Message message) {
		this.code = message.getCode();
		this.msg = message.getInfo();
	}

	public void log(String log) {
		this.log.append(log);
		this.log.append(LINE_SPLIT);
	}

	public T getRecord() {
		return record;
	}

	public void setRecord(T record) {
		this.record = record;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @return the data
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	/**
	 * @return the logInfo
	 */
	public LogInfo getLogInfo() {
		return logInfo;
	}

	/**
	 * @param logInfo
	 *            the logInfo to set
	 */
	public void setLogInfo(LogInfo logInfo) {
		this.logInfo = logInfo;
	}

	/**
	 * @return the log
	 */
	public StringBuilder getLog() {
		return log;
	}

}