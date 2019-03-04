package com.gsoft.framework.job.entity;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;

import com.gsoft.framework.core.dataobj.Domain;
import com.gsoft.framework.util.StringUtils;

/**
 * 
 * @author liupantao
 * 
 */
@Entity
@Table(name = "youi_qrtz_triggers")
@IdClass(value = SchedulerJobKey.class)
public class SchedulerJob implements Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9106047587928136468L;

	@Id
	private String schedName;//

	@Id
	private String triggerName;// 触发器

	@Id
	private String triggerGroup;// 触发组

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns(value = { 
			@JoinColumn(name = "SCHED_NAME", insertable = false, updatable = false), 
			@JoinColumn(name = "JOB_NAME", insertable = false, updatable = false), 
			@JoinColumn(name = "JOB_GROUP", insertable = false, updatable = false) })
	private JobDetails jobDetails;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns(value = { 
			@JoinColumn(name = "SCHED_NAME", referencedColumnName = "SCHED_NAME", insertable = false, updatable = false),
			@JoinColumn(name = "TRIGGER_NAME", referencedColumnName = "TRIGGER_NAME", insertable = false, updatable = false),
			@JoinColumn(name = "TRIGGER_GROUP", referencedColumnName = "TRIGGER_GROUP", insertable = false, updatable = false) }, 
			foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
	private CronTrigger cronTrigger;

	@Column(name = "DESCRIPTION")
	@Length(max = 200)
	private String description;// 调度描述

	@Column(name = "trigger_State")
	@Length(max = 200)
	private String triggerState;// 调度状态

	@Column(name = "START_TIME")
	@Length(max = 13)
	private String startTime;// 开始时间

	@Column(name = "END_TIME")
	@Length(max = 13)
	private String endTime;// 结束时间

	//
	@Column(name = "PREV_FIRE_TIME")
	@Length(max = 13)
	private String prevFireTime;// 上次运行时间

	@Column(name = "NEXT_FIRE_TIME")
	@Length(max = 13)
	private String nextFireTime;// 下次运行时间

	@Column(name = "CALENDAR_NAME")
	@Length(max = 200)
	private String calendarName;// 日历名

	// PRIORITY
	@Column(name = "PRIORITY")
	private int priority;// 优先级

	@Column(name = "MISFIRE_INSTR")
	private int misfireInsert;
	
	/**
	 * @Fields 任务类型 [通用调度，自定义调度]
	 */
	@Transient
	private String gsoftJobType;

	/**
	 * @return the schedName
	 */
	public String getSchedName() {
		return schedName;
	}

	/**
	 * @param schedName
	 *            the schedName to set
	 */
	public void setSchedName(String schedName) {
		this.schedName = schedName;
	}

	/**
	 * @return the triggerName
	 */
	public String getTriggerName() {
		return triggerName;
	}

	/**
	 * @param triggerName
	 *            the triggerName to set
	 */
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	/**
	 * @return the triggerGroup
	 */
	public String getTriggerGroup() {
		return triggerGroup;
	}

	/**
	 * @param triggerGroup
	 *            the triggerGroup to set
	 */
	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the triggerState
	 */
	public String getTriggerState() {
		return triggerState;
	}

	/**
	 * @param triggerState
	 *            the triggerState to set
	 */
	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the prevFireTime
	 */
	public String getPrevFireTime() {
		return prevFireTime;
	}

	/**
	 * @param prevFireTime
	 *            the prevFireTime to set
	 */
	public void setPrevFireTime(String prevFireTime) {
		this.prevFireTime = prevFireTime;
	}

	/**
	 * @return the nextFireTime
	 */
	public String getNextFireTime() {
		return nextFireTime;
	}

	/**
	 * @param nextFireTime
	 *            the nextFireTime to set
	 */
	public void setNextFireTime(String nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	/**
	 * @return the calendarName
	 */
	public String getCalendarName() {
		return calendarName;
	}

	/**
	 * @param calendarName
	 *            the calendarName to set
	 */
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return the misfireInsert
	 */
	public int getMisfireInsert() {
		return misfireInsert;
	}

	/**
	 * @param misfireInsert
	 *            the misfireInsert to set
	 */
	public void setMisfireInsert(int misfireInsert) {
		this.misfireInsert = misfireInsert;
	}

	/**
	 * @return the jobDetails
	 */
	public JobDetails getJobDetails() {
		return jobDetails;
	}

	/**
	 * @param jobDetails
	 *            the jobDetails to set
	 */
	public void setJobDetails(JobDetails jobDetails) {
		this.jobDetails = jobDetails;
	}

	/**
	 * @return the cornTrigger
	 */
	public CronTrigger getCronTrigger() {
		return cronTrigger;
	}

	/**
	 * @param cornTrigger
	 *            the cornTrigger to set
	 */
	public void setCronTrigger(CronTrigger cronTrigger) {
		this.cronTrigger = cronTrigger;
	}

	/**
	 * @return the gsoftJobType
	 */
	public String getGsoftJobType() {
		if(StringUtils.isEmpty(gsoftJobType)&&cronTrigger!=null) {
			return cronTrigger.getTriggerGroup();
		}
		return gsoftJobType;
	}

	/**
	 * @param gsoftJobType the gsoftJobType to set
	 */
	public void setGsoftJobType(String gsoftJobType) {
		this.gsoftJobType = gsoftJobType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calendarName == null) ? 0 : calendarName.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + misfireInsert;
		result = prime * result + ((nextFireTime == null) ? 0 : nextFireTime.hashCode());
		result = prime * result + ((prevFireTime == null) ? 0 : prevFireTime.hashCode());
		result = prime * result + priority;
		result = prime * result + ((schedName == null) ? 0 : schedName.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((triggerState == null) ? 0 : triggerState.hashCode());
		result = prime * result + ((triggerGroup == null) ? 0 : triggerGroup.hashCode());
		result = prime * result + ((triggerName == null) ? 0 : triggerName.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SchedulerJob other = (SchedulerJob) obj;
		if (calendarName == null) {
			if (other.calendarName != null)
				return false;
		} else if (!calendarName.equals(other.calendarName))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (misfireInsert != other.misfireInsert)
			return false;
		if (nextFireTime == null) {
			if (other.nextFireTime != null)
				return false;
		} else if (!nextFireTime.equals(other.nextFireTime))
			return false;
		if (prevFireTime == null) {
			if (other.prevFireTime != null)
				return false;
		} else if (!prevFireTime.equals(other.prevFireTime))
			return false;
		if (priority != other.priority)
			return false;
		if (schedName == null) {
			if (other.schedName != null)
				return false;
		} else if (!schedName.equals(other.schedName))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (triggerState == null) {
			if (other.triggerState != null)
				return false;
		} else if (!triggerState.equals(other.triggerState))
			return false;
		if (triggerGroup == null) {
			if (other.triggerGroup != null)
				return false;
		} else if (!triggerGroup.equals(other.triggerGroup))
			return false;
		if (triggerName == null) {
			if (other.triggerName != null)
				return false;
		} else if (!triggerName.equals(other.triggerName))
			return false;
		return true;
	}

}
