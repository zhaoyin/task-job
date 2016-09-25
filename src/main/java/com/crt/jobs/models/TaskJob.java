/**
 * 
 */
package com.crt.jobs.models;

/**
 * @author UOrder
 *
 */
public class TaskJob {

	public TaskJob() {

	}

	public TaskJob(String projectKey, String taskKey, String cron, Boolean bCron, String taskClass, String params) {
		this.cProjectKey = projectKey;
		this.bSystem = false;
		this.cTaskKey = taskKey;
		this.cron = cron;
		this.bCron = bCron;
		this.cTaskClass = taskClass;
		this.cParameters = params;
	}

	private Long iTaskId;

	public Long getTaskId() {
		return this.iTaskId;
	}

	private String cProjectKey;

	public String getProjectKey() {
		return this.cProjectKey;
	}

	private Boolean bSystem;

	public Boolean getSystem() {
		return this.bSystem;
	}

	private String cTaskKey;

	public String getTaskKey() {
		return this.cTaskKey;
	}

	private String cTaskClass;

	public String getTaskClass() {
		return this.cTaskClass;
	}

	private String cron;

	public String getCron() {
		return this.cron;
	}

	private Boolean bCron;

	public Boolean getIsCron() {
		return this.bCron;
	}

	/**
	 * 状态【0正常、50停止、100待添加、150添加异常、500异常终止】
	 */
	private Integer status;

	public Integer getStatus() {
		return this.status;
	}

	private String cParameters;

	public String getParameters() {
		return this.cParameters;
	}

	private String cKey;

	public String getKey() {
		if (this.bSystem == null) {
			this.cKey = "";
		}
		if (this.bSystem) {
			this.cKey = this.cTaskKey;
		} else {
			this.cKey = this.cTaskKey + "_" + this.cProjectKey;
		}
		return this.cKey;
	}
}
