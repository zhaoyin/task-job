package com.crt.jobs.task;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.slf4j.LoggerFactory;

import com.crt.jobs.api.AbstractTask;
import com.crt.jobs.api.ITaskService;
import com.crt.jobs.core.JobService;
import com.crt.jobs.listener.TaskJobData;
import com.crt.jobs.models.TaskJob;
import com.crt.jobs.task.enums.JobStatus;

/**
 * 集群任务调度线程的定时任务类
 */
class MainTask extends AbstractTask {

	private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(MainTask.class);

	private JobService jobService;

	private ITaskService taskService;

	public void setJobService(JobService service) {
		this.jobService = service;
	}
	
	public void setTaskService(ITaskService service){
		this.taskService=service;
	}

	@Override
	public void execute(JobExecutionContext context) {

		// 获取当前服务正常的任务
		List<TaskJob> servJobs = taskService.getTask(JobStatus.NORMAL.getCode());
		// =========================== 处理正常任务是否在执行 begin
		// =============================处理刚插入到数据库的状态为Normal且尚未加入调度的任务
		List<Long> addJobs = new ArrayList<Long>();
		List<Long> addErrorJobs = new ArrayList<Long>();
		try {
			for (TaskJob taskJob : servJobs) {
				try {
					if (!jobService.isExistJob(taskJob)) {
						// 不存在job，则添加
						jobService.addJob(taskJob);
						if (Logger.isInfoEnabled()) {
							Logger.info("修复任务-数据库中正常，服务没执行的任务 Key【{}】ProjectKey【{}】成功", taskJob.getTaskKey(),
									taskJob.getProjectKey());
						}
						// 修改状态为正常
						addJobs.add(taskJob.getTaskId());
					}
				} catch (SchedulerException e) {
					if (Logger.isErrorEnabled()) {
						Logger.error("MainTask.execute.error", e);
					}
					addErrorJobs.add(taskJob.getTaskId());
				}
			}
			if (!addJobs.isEmpty()) {
				taskService.updateTasksStatus(addJobs, JobStatus.NORMAL.getCode());
			}
			if (!addErrorJobs.isEmpty()) {
				taskService.updateTasksStatus(addErrorJobs, JobStatus.ERROR_ADD.getCode());
			}
		} catch (Exception e) {
			if (Logger.isErrorEnabled()) {
				Logger.error("MainTask.execute", e);
			}
		}
		// =========================== 处理正常任务是否在执行 end
		// =============================

		// =========================== 检测quartz执行的任务 begin ====================
		try {
			// 任务是否还和自己绑定, 不是则删除quartz里面的任务
			List<TaskJob> waitDestroys = new ArrayList<TaskJob>();
			List<Long> destoryJobs = new ArrayList<Long>();
			for (TaskJob taskJob : servJobs) {
				if (!TaskJobData.getJobNames().containsKey(taskJob.getKey())) {
					waitDestroys.add(taskJob);
					destoryJobs.add(taskJob.getTaskId());
				}
			}
			for (TaskJob job : waitDestroys) {
				try {
					// 删除任务
					jobService.deleteJob(job);
					TaskJobData.removeJobName(job);
					if (Logger.isInfoEnabled()) {
						Logger.info("定时清理-删除服务已有任务，数据库中已移除的任务 Key【{}】", job.getTaskKey());
					}
				} catch (SchedulerException e) {
					if (Logger.isErrorEnabled()) {
						Logger.error("删除任务异常: " + e.getMessage(), e);
					}
				}
			}
			if (destoryJobs.isEmpty()) {
				taskService.updateTasksStatus(destoryJobs, JobStatus.STOP.getCode());
			}
		} catch (Exception e) {
			if (Logger.isErrorEnabled()) {
				Logger.error("执行定时清理任务异常", e);
			}
		}
		// =========================== 检测quartz执行的任务 end ====================

		// =========================== 获取待执行的任务 begin ====================
		try {
			// 获取当前服务没有加入的任务
			List<TaskJob> jobs = taskService.getTask(JobStatus.WAIT.getCode());
			List<Long> waitNormalJobs = new ArrayList<Long>();
			List<Long> waitErrorJobs = new ArrayList<Long>();
			for (TaskJob taskJob : jobs) {
				try {
					jobService.saveJob(taskJob);
					if (Logger.isInfoEnabled()) {
						Logger.info("主线程添加任务 TaskKey【{}】ProjectKey【{}】成功", taskJob.getTaskKey(),
								taskJob.getProjectKey());
					}
					// 修改状态为正常
					// taskService.updateTaskStatus(taskJob.getTaskId(),
					// JobStatus.NORMAL.getCode());
				} catch (SchedulerException e) {
					if (Logger.isErrorEnabled()) {
						Logger.error(e.getMessage(), e);
					}
					// taskService.updateTaskStatus(taskJob.getTaskId(),
					// JobStatus.ERROR_ADD.getCode());
				}
				if (!waitNormalJobs.isEmpty()) {
					taskService.updateTasksStatus(waitNormalJobs, JobStatus.NORMAL.getCode());
				}
				if (!waitErrorJobs.isEmpty()) {
					taskService.updateTasksStatus(waitErrorJobs, JobStatus.ERROR_ADD.getCode());
				}
			}
		} catch (Exception e) {
			if (Logger.isErrorEnabled()) {
				Logger.error("执行代执行的任务异常", e);
			}
		}
		// =========================== 获取待执行的任务 end ====================

		// ===========================
		// 系统配置的定时任务规则变更(MainTask,LeaderTask,CleanTask) begin
		// ====================
		// 根据条件判断系统任务是否已经发生变更－－然后更新调度任务中的Job
		// // 添加-集群任务调度线程
		// TaskJob mainTask =
		// taskService.getSysTask("com.crt.jobs.maintask.key");
		// // 添加-Leader的任务
		// TaskJob leaderInfo =
		// taskService.getSysTask("com.crt.jobs.leadertask.key");
		// // 添加-清除过期调度日志线程
		// TaskJob cleanInfo =
		// taskService.getSysTask("com.crt.jobs.leadertask.key");
		// =========================== 系统配置的定时任务规则 end ====================
	}
}