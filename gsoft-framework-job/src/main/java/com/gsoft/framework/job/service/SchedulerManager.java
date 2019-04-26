package com.gsoft.framework.job.service;

import java.util.Collection;
import java.util.List;

import com.gsoft.framework.core.dataobj.Record;
import com.gsoft.framework.core.exception.BusException;
import com.gsoft.framework.core.orm.Condition;
import com.gsoft.framework.core.orm.Order;
import com.gsoft.framework.core.orm.Pager;
import com.gsoft.framework.core.orm.PagerRecords;
import com.gsoft.framework.job.entity.SchedulerJob;
import com.gsoft.framework.job.entity.SchedulerJobKey;

public interface SchedulerManager {

	public PagerRecords getPagerSchedulerJobs(Pager pager,// 分页条件
			Collection<Condition> conditions,// 查询条件
			Collection<Order> orders) throws BusException;
	
	/** 
	 * 获取DBJobTasks
	 * @return 
	 */
	public List<Record> getDBJobTasks();
	
	/**
	 * 暂停
	 * 
	 * @param schedulerJob
	 */
	public void pause(SchedulerJobKey schedulerJobKey);

	/**
	 * 恢复
	 * 
	 * @param schedulerJob
	 */
	public void resume(SchedulerJobKey schedulerJobKey);

	/**
	 * 删除
	 * 
	 * @param schedulerJob
	 */
	public void removeSchedulerJob(SchedulerJobKey schedulerJobKey);

	/**
	 * @param schedulerJobKey
	 * @return
	 * @throws BusException
	 */
	public SchedulerJob getSchedulerJob(SchedulerJobKey schedulerJobKey) throws BusException;

	/**
	 * @param webSchedulerJob
	 * @return
	 */
	public SchedulerJob saveSchedulerJob(SchedulerJob webSchedulerJob) throws BusException;

	/**
	 * @param property
	 * @param value
	 * @return
	 * @throws BusException
	 */
	public List<SchedulerJob> getSchedulerJobsByProperty(String property, String value) throws BusException;


}
