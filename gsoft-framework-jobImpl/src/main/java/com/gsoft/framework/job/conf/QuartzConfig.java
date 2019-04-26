package com.gsoft.framework.job.conf;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.gsoft.framework.core.exception.BusException;
import com.gsoft.framework.core.log.ConfigRegisterLog;
import com.gsoft.framework.job.JobTask;
import com.gsoft.framework.job.factory.AutoJobFactory;
import com.gsoft.framework.util.StringUtils;

/**
 * 
 * @author liupantao
 * 
 */
@Configuration
public class QuartzConfig implements ApplicationContextAware {

	private Log logger = LogFactory.getLog(QuartzConfig.class);

	@Autowired(required = false)
	private DataSource dataSource;

	@Autowired
	private QuartzProperties quartzProperties;

	private ApplicationContext applicationContext;

	/**
	 * 设置属性
	 * 
	 * @return
	 * @throws IOException
	 */
	private Properties quartzProperties() throws IOException {
		Properties prop = new Properties();
		prop.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, quartzProperties.getInstanceName());
		prop.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_ID, quartzProperties.getInstanceId());
		prop.put(StdSchedulerFactory.PROP_SCHED_SKIP_UPDATE_CHECK, "true");

		prop.put(StdSchedulerFactory.PROP_SCHED_JMX_EXPORT, quartzProperties.getJmxExport());

		prop.put(StdSchedulerFactory.PROP_THREAD_POOL_CLASS, quartzProperties.getThreadPoolClass());
		prop.put("org.quartz.threadPool.threadCount", quartzProperties.getThreadPoolThreadCount());
		prop.put("org.quartz.threadPool.threadPriority", quartzProperties.getThreadPoolThreadPriority());
		prop.put("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread",
				quartzProperties.getThreadPoolThreadsInheritContextClassLoaderOfInitializingThread());

		prop.put("org.quartz.jobStore.class", quartzProperties.getJobStoreClass());
		prop.put("org.quartz.jobStore.driverDelegateClass", quartzProperties.getJobStoreDriverDelegateClass());
		prop.put("org.quartz.jobStore.tablePrefix", quartzProperties.getJobStoreTablePrefix());
		prop.put("org.quartz.jobStore.isClustered", quartzProperties.getJobStoreIsClustered());
		prop.put("org.quartz.jobStore.clusterCheckinInterval", quartzProperties.getJobStoreClusterCheckinInterval());
		prop.put("org.quartz.jobStore.misfireThreshold", quartzProperties.getJobStoreMisfireThreshold());

		prop.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS WHERE LOCK_NAME = ? FOR UPDATE");

		if (StringUtils.isNotEmpty(quartzProperties.getJobStoreDataSource())) {
			prop.put("org.quartz.jobStore.dataSource", quartzProperties.getJobStoreDataSource());
			prop.put("org.quartz.dataSource.qzDS.driver", quartzProperties.getQzDSDriver());
			prop.put("org.quartz.dataSource.qzDS.URL", quartzProperties.getQzDSURL());
			prop.put("org.quartz.dataSource.qzDS.user", quartzProperties.getQzDSUser());
			prop.put("org.quartz.dataSource.qzDS.password", quartzProperties.getQzDSPassword());
			prop.put("org.quartz.dataSource.qzDS.maxConnections", quartzProperties.getQzDSMaxConnections());
		}
		return prop;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		// 用于quartz集群,QuartzScheduler
		// 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
		factory.setOverwriteExistingJobs(true);
		// 用于quartz集群,加载quartz数据源
		// factory.setDataSource(dataSource);
		// QuartzScheduler 延时启动，应用启动完10秒后 QuartzScheduler 再启动
		factory.setStartupDelay(10);

		if (StringUtils.isEmpty(quartzProperties.getJobStoreDataSource())) {
			if (StringUtils.isNotEmpty(quartzProperties.getSpringDataSource())) {
				DataSource dataSourceBean = applicationContext.getBean(quartzProperties.getSpringDataSource(), DataSource.class);
				if (dataSourceBean != null) {
					factory.setDataSource(dataSourceBean);
				} else {
					logger.error("获取定时调度指定数据源失败..." + quartzProperties.getSpringDataSource());
				}
			} else {
				if (dataSource != null) {
					factory.setDataSource(dataSource);
				} else {
					logger.warn("获取定时调度默认数据源失败...");
				}
			}
		}
		// 设置beanName解决InstanceName不生效的问题
		factory.setBeanName(quartzProperties.getInstanceName());
		// 用于quartz集群,加载quartz数据源配置
		factory.setQuartzProperties(quartzProperties());
		factory.setAutoStartup(true);

		factory.setApplicationContextSchedulerContextKey("applicationContext");
		// 注册触发器
		factory.setTriggers(getTriggers());
		
		ConfigRegisterLog.registeBean(factory, "定时调度工厂",this);
		
		return factory;
	}

	/**
	 * 获取触发器集合
	 * 
	 * @return
	 */
	private Trigger[] getTriggers() {
		List<Trigger> triggers = new ArrayList<Trigger>();
		List<JobTask> jobTasks = getJobTasks(applicationContext);
		for (JobTask jobTask : jobTasks) {
			triggers.add(buildCronTriggerFactoryBean(jobTask).getObject());
		}
		return triggers.toArray(new Trigger[triggers.size()]);
	}

	/**
	 * 构建触发器
	 * 
	 * @param jobTask
	 * @return
	 */
	private CronTriggerFactoryBean buildCronTriggerFactoryBean(JobTask jobTask) {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setJobDetail(buildJobDetal(jobTask).getObject());
		factoryBean.setDescription(jobTask.getCronDescription());
		factoryBean.setGroup("AutoJobTask");
		factoryBean.setName(jobTask.getName());
		factoryBean.setCronExpression(jobTask.getCronExpression());
		try {
			factoryBean.afterPropertiesSet();
		} catch (ParseException e) {
			throw new BusException(e.getMessage(), e);
		}
		return factoryBean;
	}

	/**
	 * 构建 任务配置
	 * 
	 * @param jobTask
	 * @return
	 */
	private JobDetailFactoryBean buildJobDetal(JobTask jobTask) {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(AutoJobFactory.class);
		factoryBean.setDurability(jobTask.getDurability());
		factoryBean.setRequestsRecovery(jobTask.getRequestsRecovery());
		factoryBean.setGroup("AutoJobTask");
		factoryBean.setName(jobTask.getName());
		factoryBean.setDescription(jobTask.getJobDescription());
		factoryBean.setApplicationContext(applicationContext);
		factoryBean.afterPropertiesSet();
		return factoryBean;
	}

	/**
	 * 获取 JobTask集合
	 * 
	 * @param context
	 */
	private List<JobTask> getJobTasks(ApplicationContext context) {
		List<JobTask> jobTasks = new ArrayList<JobTask>();
		Map<String, JobTask> jobTaskMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, JobTask.class, true, false);
		for (Map.Entry<String, JobTask> entry : jobTaskMap.entrySet()) {
			jobTasks.add(entry.getValue());
		}
		return jobTasks;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}