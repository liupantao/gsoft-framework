package com.gsoft.framework.job.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import com.gsoft.framework.core.dataobj.Domain;


/**
 * 
 * @author liupantao
 *
 */
@Entity
@Table(name = "youi_qrtz_cron_triggers")
@IdClass(value = SchedulerJobKey.class)
public class CronTrigger implements Domain{
	/**
	 * 
	 */
	private static final long serialVersionUID = -645796109235676454L;

	@Id
	private String schedName;//
	
	@Id
	private String triggerName;//触发器
	
	@Id
	private String triggerGroup;//触发组
	
	@Column(name = "CRON_EXPRESSION")
	@Length(max=200)
	private String cronExpression;//定时时间表达式
	
	@Column(name = "TIME_ZONE_ID")
	@Length(max=200)
	private String timeZoneId;//时区

	/**
	 * @return the schedName
	 */
	public String getSchedName() {
		return schedName;
	}

	/**
	 * @param schedName the schedName to set
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
	 * @param triggerName the triggerName to set
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
	 * @param triggerGroup the triggerGroup to set
	 */
	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	/**
	 * @return the cronExpression
	 */
	public String getCronExpression() {
		return cronExpression;
	}

	/**
	 * @param cronExpression the cronExpression to set
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	/**
	 * @return the timeZoneId
	 */
	public String getTimeZoneId() {
		return timeZoneId;
	}

	/**
	 * @param timeZoneId the timeZoneId to set
	 */
	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}
	
	
	
}
