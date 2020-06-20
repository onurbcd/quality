package com.onurbcd.qa.metrics;

public class LOCResult {
	
	private int count;
	
	private String sourceCode;

	public LOCResult(int count, String sourceCode) {
		super();
		this.count = count;
		this.sourceCode = sourceCode;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
}
