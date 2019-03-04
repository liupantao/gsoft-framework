package com.gsoft.framework.job.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.gsoft.framework.job.DBJobTask;

/**
 * 数据库自定义定时任务执行工厂
 * @author liupantao
 *
 */
@DisallowConcurrentExecution
public class DBJobFactory extends QuartzJobBean {

	private Log logger = LogFactory.getLog(DBJobFactory.class);

	private ApplicationContext ctx;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			JobKey jobKey = context.getJobDetail().getKey();
			String group = jobKey.getGroup();
			String name = jobKey.getName();
			DBJobTask dbJobTask = ctx.getBean(group, DBJobTask.class);
			if (dbJobTask != null) {
				try {
					dbJobTask.execute(name);
				} catch (Exception e) {
					logger.error("DBjob任务执行失败..." + e.getMessage(), e);
				}
			} else {
				logger.error("未找到DBjob任务服务:" + group);
			}
		} catch (Exception e) {
			throw new JobExecutionException(e);
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.ctx = applicationContext;
	}

}