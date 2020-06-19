package com.onurbcd.qa.util;

public class ReportMethod {

	private String className;
	
	private String signature;
	
	private int loc;

	public ReportMethod(String className, String signature, int loc) {
		super();
		this.className = className;
		this.signature = signature;
		this.loc = loc;
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

	public int getLoc() {
		return loc;
	}

	public void setLoc(int loc) {
		this.loc = loc;
	}
}
