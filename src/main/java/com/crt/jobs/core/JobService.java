package com.crt.jobs.core;

import org.quartz.JobListener;
import org.quartz.SchedulerException;

import com.crt.jobs.api.AbstractTask;
import com.crt.jobs.models.TaskJob;

/**
 * 定时任务的工具类
 * 
 * @version 1.0.0
 */
public final class JobService extends AbstractJobService {

	public void addJob(TaskJob task) throws SchedulerException {
		AbstractTask job = getTask(task);
		if (task != null && job != null) {
			JobListener listener = getJobListener(task);
			addJob(task.getTaskKey(), task.getProjectKey(), task.getCron(), task.getIsCron(), job, task.getParameters(),
					listener);
		}
	}

	/**
	 * 添加或更新Job
	 */
	public void saveJob(TaskJob task) throws SchedulerException {
		AbstractTask job = getTask(task);
		if (task != null && job != null) {
			JobListener listener = getJobListener(task);
			saveJob(task.getTaskKey(), task.getProjectKey(), task.getCron(), task.getIsCron(), job,
					task.getParameters(), listener);
		}
	}

	public void updateJob(TaskJob task) throws SchedulerException {
		updateJob(task.getTaskKey(), task.getProjectKey(), task.getCron(), task.getIsCron(), task.getParameters());
	}

	public boolean isExistJob(TaskJob task) throws SchedulerException {
		return isExistJob(task.getTaskKey(), task.getProjectKey());
	}

	/**
	 * 暂停任务
	 * 
	 * @param id
	 * @throws SchedulerException
	 */
	public void pauseJob(TaskJob task) throws SchedulerException {
		pauseJob(task.getTaskKey(), task.getProjectKey());
	}

	/**
	 * 恢复任务
	 * 
	 * @param id
	 * @throws SchedulerException
	 */
	public void resumeJob(TaskJob task) throws SchedulerException {
		resumeJob(task.getTaskKey(), task.getProjectKey());
	}

	/**
	 * 删除任务
	 * 
	 * @param id
	 * @throws SchedulerException
	 */
	public void deleteJob(TaskJob task) throws SchedulerException {
		deleteJob(task.getTaskKey(), task.getProjectKey());
	}

	/**
	 * 立即运行任务
	 * 
	 * @param id
	 * @throws SchedulerException
	 */
	public void triggerJob(TaskJob task) throws SchedulerException {
		triggerJob(task.getTaskKey(), task.getProjectKey());
	}
}