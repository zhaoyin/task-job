package com.crt.jobs.task;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crt.jobs.api.AbstractTask;
import com.crt.jobs.api.ITaskService;
import com.crt.jobs.core.JobService;

/**
 * 清空任务日志的定时任务类
 */
final class CleanTask extends AbstractTask {

	private static final Logger Logger = LoggerFactory.getLogger(CleanTask.class);

	private JobService jobService;

	private ITaskService taskService;

	public void setJobService(JobService service) {
		this.jobService = service;
	}

	public void setTaskService(ITaskService service) {
		this.taskService = service;
	}

	public void execute(JobExecutionContext context) throws Exception {
		// 清空小于指定日期的日志
		if (Logger.isInfoEnabled()) {
			Logger.info("清空小于指定日期日志的定时任务");
		}
		// //删除过期7天的任务
		// String logValue = sysConfigService.getValue(Config.JOBLOG_SAVE_DAY,
		// "7");
		// Date logDate = DateTimeUtil.addDays(DateTimeUtil.getTime(), -
		// Integer.valueOf(logValue));
		// taskJobLogService.deleteLtDate(logDate);
		//
		// String siValue = sysConfigService.getValue(Config.SERV_SAVE_DAY,
		// "7");
		// Date siDate = DateTimeUtil.addDays(DateTimeUtil.getTime(), -
		// Integer.valueOf(siValue));
		// //清空小于指定日期的已停止的服务
		// servInfoService.deleteDestroyLtDate(siDate);
		//
		// //修改已销毁的服务为非Leader
		// servInfoService.destroyLeader();
		//
		// //清空小于指定日期的负载的服务
		// servEqService.deleteDestroyLtDate(siDate);
	}
}