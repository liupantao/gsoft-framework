package com.gsoft.framework.job.factory;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.gsoft.framework.job.JobTask;

/**
 * 代码中定义的定时任务执行工厂
 * @author liupantao
 *
 */
@DisallowConcurrentExecution
public class AutoJobFactory extends QuartzJobBean {

	private Log logger = LogFactory.getLog(AutoJobFactory.class);

	private Map<String, JobTask> jobTaskMap;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			JobKey jobKey = context.getJobDetail().getKey();
			String name = jobKey.getName();
			JobTask jobTask = jobTaskMap.get(name);
			if (jobTask != null) {
				try {
					jobTask.execute();
				} catch (Exception e) {
					logger.error("job任务执行失败..." + e.getMessage(), e);
				}
			} else {
				logger.error("未找到job任务服务:" + name);
			}
		} catch (Exception e) {
			throw new JobExecutionException(e);
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		getJobTasks(applicationContext);
	}

	/**
	 * 获取 JobTask集合
	 * 
	 * @param context
	 */
	private void getJobTasks(ApplicationContext context) {
		if (jobTaskMap == null) {
			jobTaskMap = new HashMap<String, JobTask>();
			Map<String, JobTask> jobTaskAdapterMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, JobTask.class, true, false);
			for (Map.Entry<String, JobTask> entry : jobTaskAdapterMap.entrySet()) {
				jobTaskMap.put(entry.getValue().getName(), entry.getValue());
			}
		}
	}
}