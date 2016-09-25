package com.crt.jobs.api;

import org.quartz.JobExecutionContext;

interface ITask {

	void executeTask(JobExecutionContext context) throws Exception;

	void setParameters(String params);
}