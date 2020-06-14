package com.onurbcd.qa.util;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jdt.core.IMethod;

public class QaMethod {

	private int level;

	private IMethod method;

	private Set<QaMethod> callees;

	public QaMethod(int level, IMethod method) {
		super();
		this.level = level;
		this.method = method;
		callees = new LinkedHashSet<>();
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public IMethod getMethod() {
		return method;
	}

	public void setMethod(IMethod method) {
		this.method = method;
	}

	public Set<QaMethod> getCallees() {
		return callees;
	}

	public void setCallees(Set<QaMethod> callees) {
		this.callees = callees;
	}

	public void addtoCallees(QaMethod qaMethod) {
		callees.add(qaMethod);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder
		.append("\n")
		.append(StringUtil.fill(level * 4, " "))
		.append(level)
		.append(" - ")
		.append(method.getElementName());
		
		for (QaMethod qaMethod : callees) {
			builder.append(qaMethod.toString());
		}
		
		return builder.toString();
	}
}
