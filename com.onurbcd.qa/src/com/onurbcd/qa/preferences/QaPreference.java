package com.onurbcd.qa.preferences;

public class QaPreference {

	private String projectName;
	
	private String packageName;
	
	private int maxRecursionLevel;

	public QaPreference(String projectName, String packageName, int maxRecursionLevel) {
		super();
		this.projectName = projectName;
		this.packageName = packageName;
		this.maxRecursionLevel = maxRecursionLevel;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public int getMaxRecursionLevel() {
		return maxRecursionLevel;
	}

	public void setMaxRecursionLevel(int maxRecursionLevel) {
		this.maxRecursionLevel = maxRecursionLevel;
	}
}
