/**
 * 
 */
package com.crt.jobs.core;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author UOrder
 *
 */
class TaskScheduler {
	private static final Logger Logger = LoggerFactory.getLogger(TaskScheduler.class);
	protected final Object lock = new Object();
	private TaskScheduler() {

	}

	private static class TaskSchedulerHolder {
		public static TaskScheduler scheduler = new TaskScheduler();
	}

	public static TaskScheduler getTaskScheduler() {
		return TaskSchedulerHolder.scheduler;
	}

	private Scheduler scheduler;

	public Scheduler getScheduler() {
		synchronized (lock) {
			try {
				if (scheduler == null) {
					SchedulerFactory sf = new StdSchedulerFactory();
					scheduler = sf.getScheduler();
					if (!scheduler.isStarted()) {
						scheduler.start();
					}
				}
			} catch (SchedulerException e) {
				Logger.error("TaskScheduler.getScheduler", e);
			}

			return scheduler;
		}
		
	}

	public void stopScheduler() {
		if (scheduler != null) {
			try {
				if (!scheduler.isShutdown()) {
					scheduler.shutdown(true);
				}
			} catch (SchedulerException e) {
				try {
					scheduler.shutdown();
				} catch (SchedulerException e1) {
					Logger.error("TaskScheduler.stopScheduler", e1);
				}
			}
		}
		scheduler = null;
	}
}
