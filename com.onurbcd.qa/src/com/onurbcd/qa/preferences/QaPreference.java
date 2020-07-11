package com.onurbcd.qa.preferences;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class QaPreference {

	private String projectName;
	
	private String packageName;
	
	private String unitName;
	
	private String typeName;
	
	private String methodSignature;
	
	private String mainType;

	private List<String> notQaTypes;

	private int maxRecursionLevel;

	public QaPreference(String projectName, String packageName, String unitName, String typeName, String methodSignature, String mainType, String notQaTypes, int maxRecursionLevel) {
		super();
		this.projectName = projectName;
		this.packageName = packageName;
		this.unitName = unitName;
		this.typeName = typeName;
		this.methodSignature = methodSignature;
		this.mainType = mainType;
		this.notQaTypes = StringUtils.isNotBlank(notQaTypes) ? Arrays.asList(StringUtils.splitPreserveAllTokens(notQaTypes, ",")) : Collections.emptyList();
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

	public String getMethodSignature() {
		return methodSignature;
	}

	public void setMethodSignature(String methodSignature) {
		this.methodSignature = methodSignature;
	}

	public String getMainType() {
		return mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}

	public List<String> getNotQaTypes() {
		return notQaTypes;
	}

	public void setNotQaTypes(List<String> notQaTypes) {
		this.notQaTypes = notQaTypes;
	}

	public int getMaxRecursionLevel() {
		return maxRecursionLevel;
	}

	public void setMaxRecursionLevel(int maxRecursionLevel) {
		this.maxRecursionLevel = maxRecursionLevel;
	}
}
