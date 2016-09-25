package com.crt.jobs.models;

import java.util.Date;

public class TaskConfig {

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long iTaskId;

	public Long getTaskId() {
		return this.iTaskId;
	}

	public void setTaskId(Long taskId) {
		this.iTaskId = taskId;
	}

	private String cron;

	public String getCron() {
		return this.cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	/**
	 * 状态【0正常、50停止、100待添加、150添加异常、500异常终止】
	 */
	private Integer status;

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	private String cStatusName;

	public String getStatusName() {
		return this.cStatusName;
	}

	public void setStatusName(String statusName) {
		this.cStatusName = statusName;
	}

	private Integer iInterrupted;

	public Integer getInterrupted() {
		return this.iInterrupted;
	}

	public void setInterrupted(int interrupted) {
		this.iInterrupted = interrupted;
	}

	private Date dNextTime;

	public Date getNextTime() {
		return this.dNextTime;
	}

	public void setNextTime(Date nextTime) {
		this.dNextTime = nextTime;
	}

	private Date dPreTime;

	public Date getPreTime() {
		return this.dPreTime;
	}

	public void setPreTime(Date preTime) {
		this.dPreTime = preTime;
	}

	public String toString() {
		// +
		return "配置标识【id】:" + this.id + "；任务标识【iTaskId】:" + this.iTaskId + "；执行计划【cron】:" + this.cron
				+ "；执行状态【cStatusName】:" + this.cStatusName + "；执行状态【status】:" + this.status + "；是否补漏执行预警【iInterrupted】:"
				+ this.iInterrupted + "；下次执行时间【dNextTime】:" + dNextTime + "；" + "上次执行时间【dPreTime】:" + dPreTime + "；";
	}
}
