package com.crt.jobs.models;

public class Task {

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long iProjectId;

	public Long getProjectId() {
		return this.iProjectId;
	}

	public void setProjectId(Long projectId) {
		this.iProjectId = projectId;
	}

	private String cTaskKey;

	public String getTaskKey() {
		return this.cTaskKey;
	}

	public void setTaskKey(String taskKey) {
		this.cTaskKey = taskKey;
	}

	private String cTaskName;

	public String getTaskName() {
		return this.cTaskName;
	}

	public void setShowName(String showName) {
		this.cTaskName = showName;
	}

	private String cComment;

	public String getComment() {
		return this.cComment;
	}

	public void setComment(String comment) {
		this.cComment = comment;
	}

	private String cTaskClass;

	public String getTaskClass() {
		return this.cTaskClass;
	}

	public void setTaskClass(String taskClass) {
		this.cTaskClass = taskClass;
	}

	private String cType;

	public String getType() {
		return this.cType;
	}

	public void setType(String type) {
		this.cType = type;
	}

	private Integer iOrder;

	public Integer getOrder() {
		return this.iOrder;
	}

	public void setOrder(Integer order) {
		this.iOrder = order;
	}

	private Boolean bHidden;

	public Boolean getHidden() {
		return this.bHidden;
	}

	public void setHidden(Boolean hidden) {
		this.bHidden = hidden;
	}

	public String toString() {
		return "任务Key【cTaskKey】:" + this.cTaskKey + "；任务名称【cShowName】:" + this.cTaskName + "；所属项目【iProjectId】:"
				+ this.iProjectId + "；描述【cComment】:" + this.cComment + "；" + "；任务实现类【cTaskClass】:" + this.cTaskClass
				+ "；" + "是否在界面隐藏【bHidden】:" + this.bHidden + "任务类型【cType】:" + this.cType + "；顺序号【iOrder】:" + this.iOrder
				+ "；";
	}
}
