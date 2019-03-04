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
@Table(name = "youi_qrtz_job_details")
@IdClass(value = JobDetailsKey.class)
public class JobDetails implements Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1400283956491736563L;
	
	@Id
	private String schedName;//调度
	
	@Id
	private String jobName;//任务名称
	
	@Id
	private String jobGroup;//任务组
	
	@Column(name = "DESCRIPTION")
	@Length(max=250)
	private String description;
	
	@Column(name = "JOB_CLASS_NAME")
	@Length(max=250)
	private String jobClassName;

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

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the jobClassName
	 */
	public String getJobClassName() {
		return jobClassName;
	}

	/**
	 * @param jobClassName the jobClassName to set
	 */
	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}

}
