package com.gsoft.framework.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * TODO (描述该文件做什么)
 * 
 * @author liupantao
 * @date 2017年10月17日
 * 
 */
public class TimerJobUtils {
	private static final Log logger = LogFactory.getLog(TimerJobUtils.class);

	private static class JobResult<T, R> {
		private Future<R> future;
		private T data;
		private long jobStartTime;

		public JobResult(long jobStartTime, T data, Future<R> future) {
			this.jobStartTime = jobStartTime;
			this.future = future;
			this.data = data;
		}

		public Future<R> getFuture() {
			return this.future;
		}

		public long getJobStartTime() {
			return this.jobStartTime;
		}

		public T getData() {
			return this.data;
		}
	}

	private static class JobCheckTimerTask<T, R> extends TimerTask {
		private TimerJobUtils.TimerJobSchedule<T, R> timerJobSchedule;

		public JobCheckTimerTask(TimerJobUtils.TimerJobSchedule<T, R> timerJobSchedule) {
			this.timerJobSchedule = timerJobSchedule;
		}

		@Override
		public void run() {
			JobTimerTask<T, R> jobTimerTask = this.timerJobSchedule.getJobTimerTask();

			if (jobTimerTask != null) {
				if (needRestart(jobTimerTask)) {
					TimerJobUtils.logger.info("重新启动任务.");
					this.timerJobSchedule.restart();
				} else if (jobTimerTask != null) {
					jobTimerTask.consumeResults();
				}
			}
		}

		private boolean needRestart(TimerJobUtils.JobTimerTask<T, R> jobTimerTask) {
			return (jobTimerTask.getLastCompleteTime() > 0L)
					&& (System.currentTimeMillis() - jobTimerTask.getLastCompleteTime() > 600000L);
		}
	}

	private static class TimerJobRunnable<T, R> implements Callable<R> {
		private T data;
		private TimerJobUtils.JobTimerTask<T, R> jobTimerTask;
		private int batchIndex;
		private int index;
		private String jobId;

		public TimerJobRunnable(TimerJobUtils.JobTimerTask<T, R> jobTimerTask, String jobId, int batchIndex, int index,
				T data) {
			this.jobTimerTask = jobTimerTask;
			this.batchIndex = batchIndex;
			this.index = index;
			this.data = data;
			this.jobId = jobId;
		}

		@Override
		public R call() {
			if (this.jobTimerTask != null) {
				return this.jobTimerTask.callJob(this.jobId, this.batchIndex, this.index, this.data);
			}
			return null;
		}
	}

	private static class JobTimerTask<T, R> extends TimerTask {
		private AtomicInteger batchCounter = new AtomicInteger();
		private TimerJobUtils.TimerJob<T, R> timerJob;
		private volatile ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		private volatile long lastCompleteTime;
		private volatile int timeout;
		private List<R> results = Collections.synchronizedList(new ArrayList<R>());

		private Map<String, TimerJobUtils.JobResult<T, R>> jobFutures = Collections
				.synchronizedMap(new HashMap<String, TimerJobUtils.JobResult<T, R>>());

		private JobTimerTask(TimerJobUtils.TimerJob<T, R> timerJob) {
			this.timerJob = timerJob;
			this.timeout = timerJob.getTimeout();
			int queueCapacity = timerJob.getPoolSize();
			this.executor.setMaxPoolSize(queueCapacity);
			this.executor.setQueueCapacity(queueCapacity);
			this.executor.setCorePoolSize(queueCapacity);

			this.executor.setAllowCoreThreadTimeOut(true);
			this.executor.setWaitForTasksToCompleteOnShutdown(true);

			this.executor.initialize();
		}

		@Override
		public void run() {
			int batchIndex;
			int index;
			try {
				long fetchSize = this.executor.getMaxPoolSize()
						- this.executor.getThreadPoolExecutor().getActiveCount();
				batchIndex = this.batchCounter.addAndGet(1);

				if ((this.jobFutures.size() > 0) && (this.timeout > 0)) {
					processTimeoutJobs();
				}

				if (fetchSize > 0L) {
					if (TimerJobUtils.logger.isDebugEnabled()) {
						TimerJobUtils.logger.debug("任务批次:" + batchIndex + ",新添加任务：" + fetchSize + "个.");
					}

					List<T> datas = this.timerJob.getDatas(batchIndex, fetchSize);
					if (datas != null) {
						index = 0;
						for (T data : datas) {
							if (data != null) {
								long jobStartTime = System.currentTimeMillis();
								String jobId = jobStartTime + "_" + batchIndex + "_" + index;
								Future<R> future = this.executor.submit(
										new TimerJobUtils.TimerJobRunnable<T, R>(this, jobId, batchIndex, index, data));

								this.jobFutures.put(jobId,
										new TimerJobUtils.JobResult<T, R>(jobStartTime, data, future));

								++index;
							}
						}

					}
				}
			} catch (Throwable e) {
				TimerJobUtils.logger.error(e.getMessage());
			} finally {
				setLastCompleteTime(System.currentTimeMillis());
			}
		}

		private synchronized void processTimeoutJobs() {
			long currentTime = System.currentTimeMillis();

			for (Map.Entry<String, TimerJobUtils.JobResult<T, R>> entry : this.jobFutures.entrySet()) {
				long runningTime = currentTime - (entry.getValue()).getJobStartTime();

				long deltTime = runningTime - this.timeout * 1000;
				if (deltTime > 0L) {
					try {
						R result = (entry.getValue()).getFuture().get(10L, TimeUnit.MILLISECONDS);
						if (result != null) {
							addResult(entry.getKey(), result);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					} catch (TimeoutException e) {
						TimerJobUtils.logger.warn("停止执行超时的任务:" + (String) entry.getKey() + ",已经超时" + deltTime + "毫秒.");

						this.timerJob.processTimeoutJob((String) entry.getKey(), (entry.getValue()).getData());
					}
				}
			}
		}

		public synchronized long getLastCompleteTime() {
			return this.lastCompleteTime;
		}

		private synchronized void setLastCompleteTime(long lastCompleteTime) {
			this.lastCompleteTime = lastCompleteTime;
		}

		public synchronized void addResult(String jobId, R result) {
			if (result != null) {
				this.results.add(result);
				if (!this.jobFutures.containsKey(jobId)) {
					return;
				}
				this.jobFutures.remove(jobId);
			}
		}

		protected R callJob(String jobId, int batchIndex, int index, T data) {
			R result = null;
			try {
				result = this.timerJob.call(data);
				addResult(jobId, result);
				if (TimerJobUtils.logger.isDebugEnabled()) {
					TimerJobUtils.logger.info(jobId + " end at:" + System.currentTimeMillis());
				}
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
			}
			return result;
		}

		public void consumeResults() {
			List<R> rets = new ArrayList<R>();
			if (this.results != null) {
				rets.addAll(this.results);
				this.results.clear();

				this.timerJob.processResults(rets);
			}
		}
	}

	public static class TimerJobSchedule<T, R> {
		private volatile TimerJobUtils.JobTimerTask<T, R> jobTimerTask;
		private volatile TimerJobUtils.JobCheckTimerTask<T, R> jobCheckTimerTask;
		private volatile Timer timer = new Timer();

		private volatile Timer checkTimer = new Timer();
		private volatile TimerJobUtils.TimerJob<T, R> timerJob;

		public TimerJobSchedule(TimerJobUtils.TimerJob<T, R> timerJob) {
			this.timerJob = timerJob;
			this.jobTimerTask = createJobTimerTask();
			this.jobCheckTimerTask = new TimerJobUtils.JobCheckTimerTask<T, R>(this);
		}

		public synchronized void start() {
			if (this.timerJob != null) {
				int period = this.timerJob.getPeriod();
				this.timer.schedule(this.jobTimerTask, 0L, period);

				this.checkTimer.schedule(this.jobCheckTimerTask, period / 3, period / 2);
			}
		}

		public synchronized void restart() {
			if (this.timer != null) {
				this.timer.cancel();
			}
			this.jobTimerTask = createJobTimerTask();
			this.timer = new Timer();
			this.timer.schedule(this.jobTimerTask, 0L, this.timerJob.getPeriod());
		}

		public synchronized void stop() {
			this.timer.cancel();
			this.jobTimerTask.consumeResults();
			this.checkTimer.cancel();
		}

		private TimerJobUtils.JobTimerTask<T, R> createJobTimerTask() {
			return new TimerJobUtils.JobTimerTask<T, R>(this.timerJob);
		}

		public synchronized TimerJobUtils.JobTimerTask<T, R> getJobTimerTask() {
			return this.jobTimerTask;
		}
	}

	public static abstract interface TimerJob<T, R> {
		public abstract R call(T paramT);

		public abstract List<T> getDatas(int paramInt, long paramLong);

		public abstract void processResults(List<R> paramList);

		public abstract int getPeriod();

		public abstract int getPoolSize();

		public abstract int getTimeout();

		public abstract void processTimeoutJob(String paramString, T paramT);
	}
}