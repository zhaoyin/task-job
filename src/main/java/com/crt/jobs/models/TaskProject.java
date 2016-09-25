/**
 * 
 */
package com.crt.jobs.models;

/**
 * @author UOrder
 *
 */
public class TaskProject {
	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String cProjectName;

	public String getProjectName() {
		return this.cProjectName;
	}

	public void setProjectName(String name) {
		this.cProjectName = name;
	}

	private String cProjectKey;

	public String getProjectKey() {
		return this.cProjectKey;
	}

	public void setProjectKey(String projectKey) {
		this.cProjectKey = projectKey;
	}

	private Boolean bSystem;

	public Boolean getSystem() {
		return this.bSystem;
	}

	public void setSystem(Boolean system) {
		this.bSystem = system;
	}
}
