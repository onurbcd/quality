package com.onurbcd.qa.util;

public class ReportMethod {

	private String className;
	
	private String signature;
	
	private int loc;
	
	private int mcc;
	
	private Double coverage;

	public ReportMethod(String className, String signature) {
		super();
		this.className = className;
		this.signature = signature;
	}
	
	public void setMetrics(int loc, int mcc, Double coverage) {
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
	
	public Double getCoverage() {
		return coverage != null ? NumericUtil.round(coverage, 2) : null;
	}

	public void setCoverage(Double coverage) {
		this.coverage = coverage;
	}

	public double getHours(double mccRate, double locRate) {
		double hours = NumericUtil.round((mcc * mccRate) + (loc * locRate), 0); 
		return hours > 0 ? hours : 1;
	}
}
