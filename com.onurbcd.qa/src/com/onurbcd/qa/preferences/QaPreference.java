package com.onurbcd.qa.preferences;

public class QaPreference {

	private String projectName;
	
	private String packageName;
	
	private String unitName;
	
	private String typeName;
	
	private String methodName;



	private int maxRecursionLevel;

	public QaPreference(String projectName, String packageName, String unitName, String typeName, String methodName, int maxRecursionLevel) {
		super();
		this.projectName = projectName;
		this.packageName = packageName;
		this.unitName = unitName;
		this.typeName = typeName;
		this.methodName = methodName;
		
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}



	public int getMaxRecursionLevel() {
		return maxRecursionLevel;
	}

	public void setMaxRecursionLevel(int maxRecursionLevel) {
		this.maxRecursionLevel = maxRecursionLevel;
	}
}
