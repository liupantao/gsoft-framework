package com.gsoft.framework.job;

/**
 * 
 * @author liupantao
 * 
 */
public interface DBJobTask {

	/** 
	 * 执行任务 
	 * @param jobName 
	 */
	public void execute(String jobName);
	
	/** 
	 * 获取任务在spring上线文中注册的beanName
	 * @return 
	 */
	public String getBeanName();
	
	/** 
	 * 获取任务名称
	 * @return 
	 */
	public String getTaskName();
}
