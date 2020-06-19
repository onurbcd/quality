package com.onurbcd.qa.util;

public class ReportMethod {

	private String className;
	
	private String signature;

	public ReportMethod(String className, String signature) {
		super();
		this.className = className;
		this.signature = signature;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
}
