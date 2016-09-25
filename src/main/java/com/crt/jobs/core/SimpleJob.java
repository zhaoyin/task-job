package com.crt.jobs.core;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import com.crt.jobs.api.AbstractTask;

/**
 * 定时任务Job
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public final class SimpleJob implements Job {
	
	public SimpleJob(){
		//do nothing
	}

	private AbstractTask task;

	public void setTask(AbstractTask task) {
		this.task = task;
	}

	public AbstractTask getTask() {
		return task;
	}

	private String parameters;

	public void setParameters(String params) {
		this.parameters = params;
	}

	public String getParameters() {
		return this.parameters;
	}

	/*
	 * 2016年8月23日 下午5:12:23
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if (this.task != null) {
			task.setParameters(this.parameters);
			try {
				task.executeTask(context);
			} catch (Exception e) {
				throw new JobExecutionException(e);
			}
		}
	}
}
