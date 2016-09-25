package com.crt.jobs.api;

import org.quartz.JobExecutionContext;

/**
 * 抽象任务类
 */
public abstract class AbstractTask implements ITask {

	private String parameters;

	public void setParameters(String params) {
		this.parameters = params;
	}

	public String getParameters() {
		return this.parameters;
	}
	
	public void executeTask(JobExecutionContext context) throws Exception{
		execute(context);
	}
	
	public abstract void execute(JobExecutionContext context) throws Exception;
}
