package com.onurbcd.qa.util;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jdt.core.IMethod;

public class QaMethod {

	private int level;

	private IMethod method;

	private QaMethod parentMethod;

	private Set<QaMethod> callees;

	public QaMethod(int level, IMethod method, QaMethod parentMethod) {
		super();
		this.level = level;
		this.method = method;
		this.parentMethod = parentMethod;
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

	public QaMethod getParentMethod() {
		return parentMethod;
	}

	public void setParentMethod(QaMethod parentMethod) {
		this.parentMethod = parentMethod;
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

	public boolean isReincarnation(IMethod newMethod) {
		if (parentMethod == null) {
			return false;
		}
		
		if (newMethod.equals(parentMethod.getMethod())) {
			return true;
		}
		
		return parentMethod.isReincarnation(newMethod);
	}
}
