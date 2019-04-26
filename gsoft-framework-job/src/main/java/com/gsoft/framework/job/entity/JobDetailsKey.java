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
public class JobDetailsKey implements Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4290465625087361218L;
	
	@Id 
	@Column(name = "SCHED_NAME")
	@Length(max=120)
	private String schedName;
	
	@Id
	@Column(name = "JOB_NAME")
	@Length(max=200)
	private String jobName;
	
	@Id
	@Column(name = "JOB_GROUP")
	@Length(max=200)
	private String jobGroup;

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
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @return the jobGroup
	 */
	public String getJobGroup() {
		return jobGroup;
	}

	/**
	 * @param jobGroup the jobGroup to set
	 */
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((jobGroup == null) ? 0 : jobGroup.hashCode());
		result = prime * result + ((jobName == null) ? 0 : jobName.hashCode());
		result = prime * result
				+ ((schedName == null) ? 0 : schedName.hashCode());
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
		JobDetailsKey other = (JobDetailsKey) obj;
		if (jobGroup == null) {
			if (other.jobGroup != null)
				return false;
		} else if (!jobGroup.equals(other.jobGroup))
			return false;
		if (jobName == null) {
			if (other.jobName != null)
				return false;
		} else if (!jobName.equals(other.jobName))
			return false;
		if (schedName == null) {
			if (other.schedName != null)
				return false;
		} else if (!schedName.equals(other.schedName))
			return false;
		return true;
	}
	
	

}
