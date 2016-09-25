package com.crt.jobs.listener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.crt.jobs.models.TaskJob;

/**
 * 任务的数据信息
 * 
 * @version V1.0
 */
public class TaskJobData {

	/** 任务监听的名称集合 */
	private static Map<String, TaskJob> jobNames = new ConcurrentHashMap<String, TaskJob>();

	/**
	 * 添加job的名称
	 * 
	 * @param jobName
	 */
	public static void addJobName(TaskJob jobName) {
		if (!jobNames.containsKey(jobName.getKey())) {
			jobNames.put(jobName.getKey(), jobName);
		}
	}

	/**
	 * 获取所有job的任务名称集合
	 * 
	 * @return
	 */
	public static Map<String, TaskJob> getJobNames() {
		return jobNames;
	}

	/**
	 * 移除job的名称
	 * 
	 * @param jobName
	 */
	public static void removeJobName(TaskJob jobName) {
		if (jobNames.containsKey(jobName.getKey())) {
			jobNames.remove(jobName.getKey());
		}
	}
}