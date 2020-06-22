package com.onurbcd.qa.util;

public class ReportMethod {

	private String className;
	
	private String signature;
	
	private int loc;
	
	private int mcc;
	
	private double coverage;

	public ReportMethod(String className, String signature) {
		super();
		this.className = className;
		this.signature = signature;
	}
	
	public void setMetrics(int loc, int mcc, double coverage) {
		this.loc = loc;
		this.mcc = mcc;
		this.coverage = coverage;
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

	public int getMcc() {
		return mcc;
	}

	public void setMcc(int mcc) {
		this.mcc = mcc;
	}
	
	public double getCoverage() {
		return coverage;
	}

	public void setCoverage(double coverage) {
		this.coverage = coverage;
	}

	public double getHours() {
		// MCC * 0.5 + LOC * 0.25
		return (mcc * 0.4) + (loc * 0.15);
	}
}
