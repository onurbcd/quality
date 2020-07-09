package com.onurbcd.qa.preferences;

public class QaPreference {

	private String projectName;
	
	private int maxRecursionLevel;

	public QaPreference(String projectName, int maxRecursionLevel) {
		super();
		this.projectName = projectName;
		this.maxRecursionLevel = maxRecursionLevel;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getMaxRecursionLevel() {
		return maxRecursionLevel;
	}

	public void setMaxRecursionLevel(int maxRecursionLevel) {
		this.maxRecursionLevel = maxRecursionLevel;
	}
}
