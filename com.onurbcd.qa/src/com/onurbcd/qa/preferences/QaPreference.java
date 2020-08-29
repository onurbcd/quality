package com.onurbcd.qa.preferences;

import java.util.List;

import com.onurbcd.qa.util.StringUtil;

public class QaPreference {

	private String projectName;
	
	private String packageName;
	
	private String unitName;
	
	private String typeName;
	
	private List<String> methodSignature;
	
	private String mainType;

	private List<String> notQaTypes;

	private int maxRecursionLevel;
	
	private String reportFilePath;
	
	private String junitCoverageReport;
	
	private double minPercentRate;
	
	private double mccRate;
	
	private double locRate;

	public QaPreference(String projectName, String packageName, String unitName, String typeName, String methodSignature, String mainType, String notQaTypes) {
		super();
		this.projectName = projectName;
		this.packageName = packageName;
		this.unitName = unitName;
		this.typeName = typeName;
		this.methodSignature = StringUtil.stringToList(methodSignature);
		this.mainType = mainType;
		this.notQaTypes = StringUtil.stringToList(notQaTypes);
	}
	
	public QaPreference init(int maxRecursionLevel, String reportFilePath, String junitCoverageReport, int minPercentRate, int mccRate, int locRate) {
		this.maxRecursionLevel = maxRecursionLevel;
		this.reportFilePath = reportFilePath;
		this.junitCoverageReport = junitCoverageReport;
		this.minPercentRate = minPercentRate / 100.0;
		this.mccRate = mccRate / 100.0;
		this.locRate = locRate / 100.0;
		return this;
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

	public List<String> getMethodSignature() {
		return methodSignature;
	}

	public void setMethodSignature(List<String> methodSignature) {
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

	public String getReportFilePath() {
		return reportFilePath;
	}

	public void setReportFilePath(String reportFilePath) {
		this.reportFilePath = reportFilePath;
	}

	public String getJunitCoverageReport() {
		return junitCoverageReport;
	}

	public void setJunitCoverageReport(String junitCoverageReport) {
		this.junitCoverageReport = junitCoverageReport;
	}

	public double getMinPercentRate() {
		return minPercentRate;
	}

	public void setMinPercentRate(double minPercentRate) {
		this.minPercentRate = minPercentRate;
	}

	public double getMccRate() {
		return mccRate;
	}

	public void setMccRate(double mccRate) {
		this.mccRate = mccRate;
	}

	public double getLocRate() {
		return locRate;
	}

	public void setLocRate(double locRate) {
		this.locRate = locRate;
	}
}
