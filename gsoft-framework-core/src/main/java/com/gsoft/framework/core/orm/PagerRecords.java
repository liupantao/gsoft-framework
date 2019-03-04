/**
 * 
 */
package com.gsoft.framework.core.orm;

import java.util.List;

/**
 * @author Administrator
 * 
 */
public class PagerRecords implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8874497501408426939L;

	/**
	 * @Fields records : 记录集
	 */
	@SuppressWarnings("rawtypes")
	private List records;
	
	/**
	 * @Fields totalCount : 总记录条数
	 */
	private long totalCount;

	/**
	 * @Fields pager : 当前分页信息
	 */
	private Pager pager;
	
	@SuppressWarnings("rawtypes")
	public PagerRecords(List records, long totalCount) {
		this.records = records;
		this.totalCount = totalCount;
	}
	@SuppressWarnings("rawtypes")
	public List getRecords() {
		return records;
	}
	@SuppressWarnings("rawtypes")
	public void setRecords(List records) {
		this.records = records;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * @return the pager
	 */
	public Pager getPager() {
		return pager;
	}
	/**
	 * @param pager the pager to set
	 */
	public void setPager(Pager pager) {
		this.pager = pager;
	}
}
