package com.gsoft.framework.job.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Service;

import com.gsoft.framework.core.dataobj.Record;
import com.gsoft.framework.core.exception.BusException;
import com.gsoft.framework.core.orm.Condition;
import com.gsoft.framework.core.orm.Order;
import com.gsoft.framework.core.orm.Pager;
import com.gsoft.framework.core.orm.PagerRecords;
import com.gsoft.framework.job.DBJobTask;
import com.gsoft.framework.job.convert.JobTypeConvert;
import com.gsoft.framework.job.dao.SchedulerJobDao;
import com.gsoft.framework.job.entity.CronTrigger;
import com.gsoft.framework.job.entity.JobDetails;
import com.gsoft.framework.job.entity.SchedulerJob;
import com.gsoft.framework.job.entity.SchedulerJobKey;
import com.gsoft.framework.job.factory.AutoJobFactory;
import com.gsoft.framework.job.factory.DBJobFactory;
import com.gsoft.framework.job.service.SchedulerManager;
import com.gsoft.framework.remote.ResCodeConstants;
import com.gsoft.framework.remote.annotation.ConditionCollection;
import com.gsoft.framework.remote.annotation.OrderCollection;
import com.gsoft.framework.remote.annotation.ServiceMapping;
import com.gsoft.framework.util.ConditionUtils;
import com.gsoft.framework.util.StringUtils;

@Service("schedulerService")
public class SchedulerManagerImpl implements SchedulerManager, ApplicationContextAware {

	@Autowired
	private Scheduler scheduler;
	
	@Value("${org.quartz.scheduler.instanceName}")
	private String instanceName;
	
	@Autowired
	private SchedulerJobDao schedulerJobDao;

	private ApplicationContext applicationContext;
	
	private List<DBJobTask> dbJobTasks = null;

	/**
	 * 分页查询任务列表
	 * 
	 * @param pager
	 * @param conditions
	 * @param orders
	 * @return
	 * @throws BusException
	 */
	@ServiceMapping
	@Override
	public PagerRecords getPagerSchedulerJobs(Pager pager, // 分页条件
			@ConditionCollection(domainClazz = SchedulerJob.class) Collection<Condition> conditions, // 查询条件
			@OrderCollection Collection<Order> orders) throws BusException {
		// 只查询本系统instanceName的调度任务
		//conditions.add(ConditionUtils.getCondition("schedName", Condition.EQUALS, instanceName));
		return schedulerJobDao.findByPager(pager, conditions, orders);
	}
	
	@Override
	@ServiceMapping
	public List<Record> getDBJobTasks(){
		List<Record> records = new ArrayList<>();
		for(DBJobTask dbJobTask:dbJobTasks) {
			Record record = new Record();
			record.put("beanName", dbJobTask.getBeanName());
			record.put("taskName", dbJobTask.getTaskName());
			records.add(record);
		}
		return records;
	}

	@Override
	@ServiceMapping
	public SchedulerJob getSchedulerJob(SchedulerJobKey schedulerJobKey) {
		return schedulerJobDao.findOne(schedulerJobKey);
	}

	@Override
	@ServiceMapping
	public SchedulerJob saveSchedulerJob(SchedulerJob schedulerJob) throws BusException {
		try {
			
			String group = StringUtils.isEmpty(schedulerJob.getGsoftJobType())?"DEFAULT":schedulerJob.getGsoftJobType();
			
			// 设置参数
			CronTrigger sCronTrigger = schedulerJob.getCronTrigger();
			if (sCronTrigger.getTriggerName() == null) {
				sCronTrigger.setTriggerName(UUID.randomUUID().toString());
				sCronTrigger.setTriggerGroup(group);
			}
			// 设置jobDetails
			JobDetails jobDetails = schedulerJob.getJobDetails();
			if(jobDetails==null) {
				jobDetails = new JobDetails();
			}
			if(jobDetails.getJobGroup()==null) {
				jobDetails.setJobGroup(group);
			}
			if(jobDetails.getJobName()==null) {
				jobDetails.setJobName(UUID.randomUUID().toString());
			}

			JobDetail dbJobDetail = scheduler.getJobDetail(
					new JobKey(schedulerJob.getJobDetails().getJobName(), schedulerJob.getJobDetails().getJobGroup()));
			
			if (dbJobDetail != null && AutoJobFactory.class.equals(dbJobDetail.getJobClass())) {
				throw new BusException("000000", "AutoJobFactory内置调度任务不允许修改");
			}

			JobDetail jobDetail = buildJobDetal(schedulerJob).getObject();

			scheduler.addJob(jobDetail, true);

			CronTriggerFactoryBean cronTriggerFactoryBean = buildCronTriggerFactoryBean(schedulerJob);
			cronTriggerFactoryBean.setJobDetail(jobDetail);
			try {
				cronTriggerFactoryBean.afterPropertiesSet();
			} catch (ParseException e) {
				throw new BusException(e.getMessage(), e);
			}
			org.quartz.CronTrigger jobCronTrigger = cronTriggerFactoryBean.getObject();
			Trigger cronTrigger = scheduler.getTrigger(jobCronTrigger.getKey());
			if (cronTrigger == null) {
				scheduler.scheduleJob(jobCronTrigger);
			} else {
				scheduler.rescheduleJob(jobCronTrigger.getKey(), jobCronTrigger);
			}
		} catch (SchedulerException e) {
			throw new BusException(e.getMessage(), e);
		}
		return schedulerJob;
	}

	/**
	 * 暂停
	 * 
	 * @param schedulerJob
	 */
	@ServiceMapping
	public void pause(SchedulerJobKey schedulerJobKey) {
		this.pauseTrigger(schedulerJobKey.getTriggerName(), schedulerJobKey.getTriggerGroup());
	}

	/**
	 * 恢复
	 * 
	 * @param schedulerJob
	 */
	@ServiceMapping
	public void resume(SchedulerJobKey schedulerJobKey) {
		this.resumeTrigger(schedulerJobKey.getTriggerName(), schedulerJobKey.getTriggerGroup());
	}

	/**
	 * 删除
	 * 
	 * @param schedulerJob
	 */
	@ServiceMapping
	public void removeSchedulerJob(SchedulerJobKey schedulerJobKey) {
		SchedulerJob schedulerJob = getSchedulerJob(schedulerJobKey);
		try {
			JobDetail dbJobDetail = scheduler.getJobDetail(
					new JobKey(schedulerJob.getJobDetails().getJobName(), schedulerJob.getJobDetails().getJobGroup()));
			if (dbJobDetail != null && AutoJobFactory.class.equals(dbJobDetail.getJobClass())) {
				throw new BusException("000000", "AutoJobFactory内置调度任务不允许修改");
			}
		} catch (BusException e) {
			throw e;
		} catch (Exception e) {
			throw new BusException("获取任务信息失败..." + e.getMessage(), e);
		}
		this.removeTrigger(schedulerJob);
	}

	private void pauseTrigger(String triggerName, String group) {
		try {
			// 停止触发器
			scheduler.pauseTrigger(new TriggerKey(triggerName, group));
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	private void resumeTrigger(String triggerName, String group) {
		try {
			scheduler.resumeTrigger(new TriggerKey(triggerName, group));// 重启触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean removeTrigger(SchedulerJob schedulerJob) {
		try {
			TriggerKey triggerKey = new TriggerKey(schedulerJob.getTriggerGroup(), schedulerJob.getTriggerName());
			scheduler.pauseTrigger(triggerKey);// 停止触发器
			scheduler.unscheduleJob(triggerKey);// 移除触发器

			JobKey jobKey = new JobKey(schedulerJob.getJobDetails().getJobName(),
					schedulerJob.getJobDetails().getJobGroup());
			return scheduler.deleteJob(jobKey);// 移除任务
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<SchedulerJob> getSchedulerJobsByProperty(String property, String value) throws BusException {
		return schedulerJobDao.findList(property, value);
	}

	/**
	 * 构建触发器
	 * 
	 * @param jobTask
	 * @return
	 */
	private CronTriggerFactoryBean buildCronTriggerFactoryBean(SchedulerJob schedulerJob) {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setDescription(schedulerJob.getDescription());
		factoryBean.setGroup(schedulerJob.getCronTrigger().getTriggerGroup());
		factoryBean.setName(schedulerJob.getCronTrigger().getTriggerName());
		factoryBean.setCronExpression(schedulerJob.getCronTrigger().getCronExpression());
		return factoryBean;
	}

	/**
	 * 构建 任务配置
	 * 
	 * @param jobTask
	 * @return
	 */
	private JobDetailFactoryBean buildJobDetal(SchedulerJob schedulerJob) {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		if(JobTypeConvert.DBJOB.equals(schedulerJob.getGsoftJobType())) {
			factoryBean.setJobClass(DBJobFactory.class);
		}else {
			try {
				Class<?> jobClass = Class.forName(schedulerJob.getJobDetails().getJobClassName());
				factoryBean.setJobClass(jobClass);
			} catch (Exception e) {
				throw new BusException(ResCodeConstants.INFO_CODE, "任务类["+schedulerJob.getJobDetails().getJobClassName()+"]未找到！");
			}
		}
		factoryBean.setDurability(true);
		factoryBean.setRequestsRecovery(true);
		factoryBean.setGroup(schedulerJob.getJobDetails().getJobGroup());
		factoryBean.setName(schedulerJob.getJobDetails().getJobName());
		factoryBean.setDescription(schedulerJob.getJobDetails().getDescription());
		factoryBean.setApplicationContext(applicationContext);
		factoryBean.afterPropertiesSet();
		return factoryBean;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		initDbJobs(applicationContext);
	}
	
	/**
	 * 初始化dbJobTask列表
	 * 
	 * @param applicationContext
	 */
	private void initDbJobs(ApplicationContext applicationContext) {
		if (dbJobTasks == null) {
			dbJobTasks = new ArrayList<>(8);
			Map<String, DBJobTask> beanMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext,
					DBJobTask.class, true, false);
			for (Map.Entry<String, DBJobTask> entry : beanMap.entrySet()) {
				dbJobTasks.add(entry.getValue());
			}
		}
	}
}
