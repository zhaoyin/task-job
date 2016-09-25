/**
 * 
 */
package com.crt.jobs.core;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.crt.jobs.api.AbstractTask;
import com.crt.jobs.listener.MainListener;
import com.crt.jobs.listener.TaskJobListener;
import com.crt.jobs.models.TaskJob;
import com.crt.jobs.utils.Toolkit;

/**
 * @author UOrder
 *
 */
abstract class AbstractJobService {

	protected TriggerKey getTriggerKey(String taskKey, String groupKey) {
		// 名称：task_1 ..
		// 组 ：group_1 ..
		String name = "task_" + taskKey;
		String group = "group_" + (Toolkit.isEmpty(groupKey) ? taskKey : groupKey);
		TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
		return triggerKey;
	}

	protected JobKey getJobKey(String taskKey, String groupKey) {
		String name = "task_" + taskKey;
		String group = "group_" + (Toolkit.isEmpty(groupKey) ? taskKey : groupKey);
		JobKey jobKey = JobKey.jobKey(name, group);
		return jobKey;
	}

	protected AbstractTask getTask(TaskJob task) throws SchedulerException {
		AbstractTask job;
		try {
			Class<?> clazz=Class.forName(task.getTaskClass());
			if(!AbstractTask.class.isAssignableFrom(clazz)){
				throw new SchedulerException("定时任务之行类必须继承自AbstractTask抽象类!");
			}
			job = (AbstractTask) clazz.newInstance();
		} catch (InstantiationException e) {
			throw new SchedulerException(e);
		} catch (IllegalAccessException e) {
			throw new SchedulerException(e);
		} catch (ClassNotFoundException e) {
			throw new SchedulerException(e);
		}
		return job;
	}

	protected JobListener getJobListener(TaskJob task) throws SchedulerException {
		if (task == null) {
			throw new SchedulerException("无效的job设置！");
		}
		JobListener listener = null;
		if (task != null && task.getSystem()) {
			listener = new MainListener(task);
		} else {
			listener = new TaskJobListener(task);
		}
		return listener;
	}

	/**
	 * 添加定时任务
	 * 
	 * @param id
	 *            名称和组的后缀
	 * @param cron
	 *            执行规则
	 * @param execTask
	 *            添加的执行的任务类
	 * @param jobListener
	 * @throws SchedulerException
	 */
	protected void addJob(String taskKey, String groupKey, String cron, Boolean bCron, AbstractTask execTask,
			String params, JobListener jobListener) throws SchedulerException {
		saveJob(taskKey, groupKey, cron, bCron, execTask, params, jobListener);
	}

	/**
	 * 更新任务
	 * 
	 * @param id
	 *            名称和组的后缀
	 * @param cron
	 *            执行规则
	 * @throws SchedulerException
	 */
	protected void updateJob(String taskKey, String groupKey, String cron, Boolean bCron, String params)
			throws SchedulerException {
		saveJob(taskKey, groupKey, cron, bCron, null, params, null);
	}

	/**
	 * 判断是否存在job
	 * 
	 * @param id
	 * @return
	 * @throws SchedulerException
	 */
	protected boolean isExistJob(String taskKey, String groupKey) throws SchedulerException {
		Scheduler scheduler = TaskScheduler.getTaskScheduler().getScheduler();
		// 可执行的任务列表
		// 任务名称和任务组设置规则：
		// 名称：task_1 ..
		// 组 ：group_1 ..
		// TriggerKey triggerKey = getTriggerKey(taskKey, groupKey);
		return scheduler.checkExists(getJobKey(taskKey, groupKey));
	}

	/**
	 * 添加或更新Job
	 * 
	 * @param id
	 * @param cron
	 * @param execTask
	 * @param jobListener
	 * @throws SchedulerException
	 */
	protected void saveJob(String taskKey, String groupKey, String cron, Boolean bCron, AbstractTask execTask,
			String params, JobListener jobListener) throws SchedulerException {
		Scheduler scheduler = TaskScheduler.getTaskScheduler().getScheduler();
		 // First we must get a reference to a scheduler
		// 可执行的任务列表
		// 任务名称和任务组设置规则：
		// 名称：task_1 ..
		// 组 ：group_1 ..
		TriggerKey triggerKey = getTriggerKey(taskKey, groupKey);
		Trigger trigger = null;
		if (!scheduler.checkExists(triggerKey)) {
			// 不存在，创建一个
			JobKey key = getJobKey(taskKey, groupKey);
			JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity(key).build();
			// jobDetail.requestsRecovery();
			jobDetail.getJobDataMap().put("task", execTask);
			jobDetail.getJobDataMap().put("taskKey", key.getName());
			jobDetail.getJobDataMap().put("groupKey", key.getGroup());
			jobDetail.getJobDataMap().put("parameters", params);
			if (jobListener instanceof MainListener) {
				jobDetail.getJobDataMap().put("jobService", this);
				jobDetail.getJobDataMap().put("taskService", ServiceHandler.getInstance().getTaskService());
			}
			if (!bCron) {
				String[] cronList = cron.split("#");// 间隔多少秒执行一次#重复执行次数#当前时间多少秒后开始执行
				int intervalInSeconds = 1;
				SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
				int repeatCounts = -1;
				if (cronList.length > 0) {
					intervalInSeconds = Integer.valueOf(cronList[0]);
					scheduleBuilder.withIntervalInSeconds(intervalInSeconds);
				}
				if (cronList.length > 1) {
					repeatCounts = Integer.valueOf(cronList[1]);
					scheduleBuilder.withRepeatCount(repeatCounts);
				} else {
					scheduleBuilder.repeatForever();
				}
				Date startTime = DateBuilder.newDate().build();
				if (cronList.length > 2) {
					int delaySeconds = Integer.valueOf(cronList[2]);
					startTime = DateBuilder.nextGivenSecondDate(null, delaySeconds);
				}
				trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity(triggerKey).startAt(startTime)
						.withSchedule(scheduleBuilder).build();
			} else {
				// 表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
				// scheduleBuilder.withMisfireHandlingInstructionDoNothing();
				// 按新的表达式构建一个新的trigger
				trigger = (CronTrigger) TriggerBuilder.newTrigger().withIdentity(triggerKey)
						.withSchedule(scheduleBuilder).build();
			}
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.getListenerManager().addJobListener(jobListener);
		} else {
			trigger = scheduler.getTrigger(triggerKey);
			trigger.getJobDataMap().put("parameters", params);
			if (trigger instanceof CronTrigger) {
				// trigger已存在，则更新相应的定时设置
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
				// scheduleBuilder.withMisfireHandlingInstructionDoNothing();
				// 按新的cronExpression表达式重新构建trigger
				trigger = ((CronTrigger) trigger).getTriggerBuilder().withIdentity(triggerKey)
						.withSchedule(scheduleBuilder).build();
			} else {
				String[] cronList = cron.split("#");// 间隔多少秒执行一次#重复执行次数#当前时间多少秒后开始执行
				int intervalInSeconds = 1;
				SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
				int repeatCounts = -1;
				if (cronList.length > 0) {
					intervalInSeconds = Integer.valueOf(cronList[0]);
					scheduleBuilder.withIntervalInSeconds(intervalInSeconds);
				}
				if (cronList.length > 1) {
					repeatCounts = Integer.valueOf(cronList[1]);
					scheduleBuilder.withRepeatCount(repeatCounts);
				} else {
					scheduleBuilder.repeatForever();
				}
				Date startTime = new Date();
				if (cronList.length > 2) {
					int delaySeconds = Integer.valueOf(cronList[2]);
					startTime = DateBuilder.nextGivenSecondDate(null, delaySeconds);
				}
				trigger = ((SimpleTrigger) trigger).getTriggerBuilder().withIdentity(triggerKey).startAt(startTime)
						.withSchedule(scheduleBuilder).build();
			}
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}

	/**
	 * 暂停任务
	 * 
	 * @param id
	 * @throws SchedulerException
	 */
	protected void pauseJob(String taskKey, String groupKey) throws SchedulerException {
		Scheduler scheduler = TaskScheduler.getTaskScheduler().getScheduler();
		JobKey jobKey = getJobKey(taskKey, groupKey);
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 恢复任务
	 * 
	 * @param id
	 * @throws SchedulerException
	 */
	protected void resumeJob(String taskKey, String groupKey) throws SchedulerException {
		Scheduler scheduler = TaskScheduler.getTaskScheduler().getScheduler();
		JobKey jobKey = getJobKey(taskKey, groupKey);
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 删除任务
	 * 
	 * @param id
	 * @throws SchedulerException
	 */
	protected void deleteJob(String taskKey, String groupKey) throws SchedulerException {
		Scheduler scheduler = TaskScheduler.getTaskScheduler().getScheduler();
		JobKey jobKey = getJobKey(taskKey, groupKey);
		scheduler.deleteJob(jobKey);
	}

	/**
	 * 立即运行任务
	 * 
	 * @param id
	 * @throws SchedulerException
	 */
	protected void triggerJob(String taskKey, String groupKey) throws SchedulerException {
		Scheduler scheduler = TaskScheduler.getTaskScheduler().getScheduler();
		JobKey jobKey = getJobKey(taskKey, groupKey);
		scheduler.triggerJob(jobKey);
	}
}
