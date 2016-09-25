package com.crt.jobs.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import com.crt.jobs.api.ITaskService;
import com.crt.jobs.core.ServiceHandler;
import com.crt.jobs.models.TaskJob;
import com.crt.jobs.task.enums.JobStatus;
import com.crt.jobs.utils.Toolkit;

/**
 * 监听job的线程
 * 
 * @version 1.0.0
 */
public final class TaskJobListener implements JobListener {

	private ITaskService taskService;

	// task的id
	private String name;

	public TaskJobListener(TaskJob task) {
		super();
		this.name = task.getKey();
		TaskJobData.addJobName(task);
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
		if (inException != null && !Toolkit.isEmpty(inException.getMessage())) {
			TaskJob task = TaskJobData.getJobNames().get(this.name);
			if (task != null) {
				taskService = ServiceHandler.getInstance().getTaskService();

				taskService.updateTaskStatus(task.getTaskId(), JobStatus.ERROR.getCode());
			}
		}
		// Task的name: inContext.getJobDetail().getKey().getName()
		// Task的group: inContext.getJobDetail().getKey().getGroup()

	}
}