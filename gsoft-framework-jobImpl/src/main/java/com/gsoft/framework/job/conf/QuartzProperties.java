package com.gsoft.framework.job.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Quartz配置
 * @author liupantao
 *
 */
@Component
public class QuartzProperties {

	//默认或是自己改名字都行
	@Value("${org.quartz.scheduler.instanceName}")
	private String instanceName;
	
	//如果使用集群，instanceId必须唯一，设置成AUTO
	@Value("${org.quartz.scheduler.instanceId}")
	private String instanceId;

	@Value("${org.quartz.scheduler.jmx.export}")
	private String jmxExport;
	
	@Value("${org.quartz.scheduler.wrapJobExecutionInUserTransaction}")
	private String wrapJobExecutionInUserTransaction;
	
	@Value("${org.quartz.threadPool.class}")
	private String threadPoolClass;
	
	@Value("${org.quartz.threadPool.threadCount}")
	private String threadPoolThreadCount;
	
	@Value("${org.quartz.threadPool.threadPriority}")
	private String threadPoolThreadPriority;
	
	@Value("${org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread}")
	private String threadPoolThreadsInheritContextClassLoaderOfInitializingThread;
	
	//============================================================================
	//ConfigureJobStore
	//============================================================================
	
	@Value("${org.quartz.jobStore.clusterCheckinInterval}")
	private String jobStoreClusterCheckinInterval;
	
	@Value("${org.quartz.jobStore.misfireThreshold}")
	private String jobStoreMisfireThreshold;

	//存储方式使用JobStoreTX，也就是数据库
	@Value("${org.quartz.jobStore.class}")
	private String jobStoreClass;
	
	@Value("${org.quartz.jobStore.driverDelegateClass}")
	private String jobStoreDriverDelegateClass;
	
	//使用自己的配置文件
	@Value("${org.quartz.jobStore.useProperties}")
	private String JobStoreUseProperties;
	
	//数据库中quartz表的表名前缀
	@Value("${org.quartz.jobStore.tablePrefix}")
	private String jobStoreTablePrefix;

	//是否使用集群（如果项目只部署到一台服务器，就不用了）
	@Value("${org.quartz.jobStore.isClustered}")
	private String jobStoreIsClustered;

	//============================================================================
	//ConfigureDatasources
	//============================================================================
	//配置数据源
	@Value("${org.quartz.jobStore.springDataSource:}")
	private String springDataSource;
	
	@Value("${org.quartz.jobStore.dataSource:}")
	private String jobStoreDataSource;
	
	@Value("${org.quartz.dataSource.qzDS.driver:}")
	private String qzDSDriver;

	@Value("${org.quartz.dataSource.qzDS.URL:}")
	private String qzDSURL;

	@Value("${org.quartz.dataSource.qzDS.user:}")
	private String qzDSUser;

	@Value("${org.quartz.dataSource.qzDS.password:}")
	private String qzDSPassword;
	

	@Value("${org.quartz.dataSource.qzDS.maxConnections:}")
	private String qzDSMaxConnections;

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getJmxExport() {
		return jmxExport;
	}

	public void setJmxExport(String jmxExport) {
		this.jmxExport = jmxExport;
	}

	public String getWrapJobExecutionInUserTransaction() {
		return wrapJobExecutionInUserTransaction;
	}

	public void setWrapJobExecutionInUserTransaction(String wrapJobExecutionInUserTransaction) {
		this.wrapJobExecutionInUserTransaction = wrapJobExecutionInUserTransaction;
	}

	public String getThreadPoolClass() {
		return threadPoolClass;
	}

	public void setThreadPoolClass(String threadPoolClass) {
		this.threadPoolClass = threadPoolClass;
	}

	public String getThreadPoolThreadCount() {
		return threadPoolThreadCount;
	}

	public void setThreadPoolThreadCount(String threadPoolThreadCount) {
		this.threadPoolThreadCount = threadPoolThreadCount;
	}

	public String getThreadPoolThreadPriority() {
		return threadPoolThreadPriority;
	}

	public void setThreadPoolThreadPriority(String threadPoolThreadPriority) {
		this.threadPoolThreadPriority = threadPoolThreadPriority;
	}

	public String getThreadPoolThreadsInheritContextClassLoaderOfInitializingThread() {
		return threadPoolThreadsInheritContextClassLoaderOfInitializingThread;
	}

	public void setThreadPoolThreadsInheritContextClassLoaderOfInitializingThread(String threadPoolThreadsInheritContextClassLoaderOfInitializingThread) {
		this.threadPoolThreadsInheritContextClassLoaderOfInitializingThread = threadPoolThreadsInheritContextClassLoaderOfInitializingThread;
	}

	public String getJobStoreClusterCheckinInterval() {
		return jobStoreClusterCheckinInterval;
	}

	public void setJobStoreClusterCheckinInterval(String jobStoreClusterCheckinInterval) {
		this.jobStoreClusterCheckinInterval = jobStoreClusterCheckinInterval;
	}

	public String getJobStoreMisfireThreshold() {
		return jobStoreMisfireThreshold;
	}

	public void setJobStoreMisfireThreshold(String jobStoreMisfireThreshold) {
		this.jobStoreMisfireThreshold = jobStoreMisfireThreshold;
	}

	public String getJobStoreClass() {
		return jobStoreClass;
	}

	public void setJobStoreClass(String jobStoreClass) {
		this.jobStoreClass = jobStoreClass;
	}

	public String getJobStoreDriverDelegateClass() {
		return jobStoreDriverDelegateClass;
	}

	public void setJobStoreDriverDelegateClass(String jobStoreDriverDelegateClass) {
		this.jobStoreDriverDelegateClass = jobStoreDriverDelegateClass;
	}

	public String getJobStoreUseProperties() {
		return JobStoreUseProperties;
	}

	public void setJobStoreUseProperties(String jobStoreUseProperties) {
		JobStoreUseProperties = jobStoreUseProperties;
	}

	public String getJobStoreTablePrefix() {
		return jobStoreTablePrefix;
	}

	public void setJobStoreTablePrefix(String jobStoreTablePrefix) {
		this.jobStoreTablePrefix = jobStoreTablePrefix;
	}

	public String getJobStoreIsClustered() {
		return jobStoreIsClustered;
	}

	public void setJobStoreIsClustered(String jobStoreIsClustered) {
		this.jobStoreIsClustered = jobStoreIsClustered;
	}

	public String getSpringDataSource() {
		return springDataSource;
	}

	public void setSpringDataSource(String springDataSource) {
		this.springDataSource = springDataSource;
	}

	public String getJobStoreDataSource() {
		return jobStoreDataSource;
	}

	public void setJobStoreDataSource(String jobStoreDataSource) {
		this.jobStoreDataSource = jobStoreDataSource;
	}

	public String getQzDSDriver() {
		return qzDSDriver;
	}

	public void setQzDSDriver(String qzDSDriver) {
		this.qzDSDriver = qzDSDriver;
	}

	public String getQzDSURL() {
		return qzDSURL;
	}

	public void setQzDSURL(String qzDSURL) {
		this.qzDSURL = qzDSURL;
	}

	public String getQzDSUser() {
		return qzDSUser;
	}

	public void setQzDSUser(String qzDSUser) {
		this.qzDSUser = qzDSUser;
	}

	public String getQzDSPassword() {
		return qzDSPassword;
	}

	public void setQzDSPassword(String qzDSPassword) {
		this.qzDSPassword = qzDSPassword;
	}

	public String getQzDSMaxConnections() {
		return qzDSMaxConnections;
	}

	public void setQzDSMaxConnections(String qzDSMaxConnections) {
		this.qzDSMaxConnections = qzDSMaxConnections;
	}

}
