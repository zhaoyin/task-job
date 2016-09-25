package com.crt.jobs.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import com.crt.jobs.models.TaskJob;

/**
 * 监听主线程
 * 
 * @version 1.0.0
 */
public final class MainListener implements JobListener {

	// task的id
	private String name;

	public MainListener(TaskJob task) {
		super();
		this.name = task.getKey();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void jobToBeExecuted(JobExecutionContext inContext) {
		// 执行任务前执行
	}

	public void jobExecutionVetoed(JobExecutionContext inContext) {

	}

	public void jobWasExecuted(JobExecutionContext inContext, JobExecutionException inException) {
		// 任务执行完后，执行

		// Task的name: inContext.getJobDetail().getKey().getName()
		// Task的group: inContext.getJobDetail().getKey().getGroup()

	}
}