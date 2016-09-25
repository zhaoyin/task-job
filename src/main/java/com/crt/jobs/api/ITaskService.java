/**
 * 
 */
package com.crt.jobs.api;

import java.util.List;

import com.crt.jobs.models.TaskJob;

/**
 * @author UOrder
 *
 */
public interface ITaskService {
	/**
	 * 根据任务key获取系统任务
	 * 
	 * @param taskKey
	 * @return
	 */
	public TaskJob getSysTask(String taskKey);

	/**
	 * 根据状态获取非系统任务列表
	 * 
	 * @param status
	 * @return
	 */
	public List<TaskJob> getTask(Integer status);

	/**
	 * 批量更新非系统任务列表
	 * 
	 * @param taskIds
	 * @param status
	 */
	public void updateTasksStatus(List<Long> taskIds, Integer status);

	/**
	 * 更新非系统任务列表
	 * 
	 * @param taskId
	 * @param status
	 */
	public void updateTaskStatus(Long taskId, Integer status);

}
