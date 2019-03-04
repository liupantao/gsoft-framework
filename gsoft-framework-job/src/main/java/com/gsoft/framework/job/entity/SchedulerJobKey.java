package com.gsoft.framework.job.entity;

import javax.persistence.Column;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;

import com.gsoft.framework.core.dataobj.Domain;

/**
 * 
 * @author liupantao
 *
 */
public class SchedulerJobKey implements Domain{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3062507591231607641L;

	@Id
	@Column(name = "SCHED_NAME")
	@Length(max=120)
	private String schedName;//
	
	@Id
	@Column(name = "TRIGGER_NAME")
	@Length(max=200)
	private String triggerName;//触发器
	
	@Id
	@Column(name = "TRIGGER_GROUP")
	@Length(max=200)
	private String triggerGroup;//触发组
	
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((schedName == null) ? 0 : schedName.hashCode());
		result = prime * result
				+ ((triggerGroup == null) ? 0 : triggerGroup.hashCode());
		result = prime * result
				+ ((triggerName == null) ? 0 : triggerName.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		SchedulerJobKey other = (SchedulerJobKey) obj;
		if (schedName == null) {
			if (other.schedName != null)
				return false;
		} else if (!schedName.equals(other.schedName))
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
