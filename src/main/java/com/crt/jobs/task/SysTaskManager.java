package com.crt.jobs.task;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crt.jobs.api.ITaskService;
import com.crt.jobs.core.JobService;
import com.crt.jobs.core.ServiceHandler;
import com.crt.jobs.models.TaskJob;

/**
 * 定时任务加载工具类
 * 
 * @version V1.0.0
 */
public final class SysTaskManager {

	private static final Logger Logger = LoggerFactory.getLogger(SysTaskManager.class);

	private static ITaskService taskService;

	static{
		if(taskService==null){
			taskService=ServiceHandler.getInstance().getTaskService();
		}
	}
	
	public static void init(JobService jobService) {
		
		try {
			// 添加-集群任务调度线程
			TaskJob mainInfo = taskService.getSysTask("com.crt.jobs.maintask.key");
			jobService.addJob(mainInfo);
		} catch (SchedulerException e) {
			Logger.error("添加系统的定时任务异常: " + e.getMessage(), e);
		}
		try {
			// 添加-Leader的任务
			TaskJob leaderInfo = taskService.getSysTask("com.crt.jobs.leadertask.key");
			jobService.addJob(leaderInfo);
		} catch (SchedulerException e) {
			Logger.error("添加-Leader的任务异常: " + e.getMessage(), e);
		}
		try {
			// 添加-清除过期调度日志线程
			TaskJob cleanInfo = taskService.getSysTask("com.crt.jobs.leadertask.key");
			jobService.addJob(cleanInfo);
		} catch (SchedulerException e) {
			Logger.error("添加清除任务日志的定时任务异常: " + e.getMessage(), e);
		}
	}

}