package com.onurbcd.qa.manager;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;

import com.onurbcd.qa.helper.MethodHelper;
import com.onurbcd.qa.helper.PackageHelper;
import com.onurbcd.qa.helper.ProjectHelper;
import com.onurbcd.qa.helper.ReportHelper;
import com.onurbcd.qa.helper.TypeHelper;
import com.onurbcd.qa.helper.UnitHelper;
import com.onurbcd.qa.preferences.PreferenceHelper;
import com.onurbcd.qa.preferences.QaPreference;
import com.onurbcd.qa.util.DateTimeUtil;
import com.onurbcd.qa.util.QaMethod;

public class AnalysisManager {
	
	// PRIVATE STATIC PROPERTIES

	private static final String WAS_NOT_FOUND = "' was not found.";
	
	// PRIVATE PROPERTIES

	private StringBuilder runMessage;
	
	private IProject project;

	private IPackageFragment packageFragment;

	private ICompilationUnit compilationUnit;

	private IType type;

	private IMethod method;

	private QaMethod qaMethod;

	private QaPreference prefs;
	
	private boolean invalid;

	// PUBLIC METHODS

	public void run() {
		Instant start = Instant.now();
		initVariables();
		setMessages(DateTimeUtil.getNowFormatted());
		initPreferences();
		process();
		ReportHelper.processReport(qaMethod, prefs);
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).getSeconds();
		setMessages("DURATION IN SECONDS: ", String.valueOf(timeElapsed));
	}
	
	public String getMessage() {
		return runMessage != null ? runMessage.toString() : "Call method 'run' before get message.";
	}
	
	// PRIVATE METHODS
	
	private void initVariables() {
		runMessage = new StringBuilder("");
		project = null;
		packageFragment = null;
		compilationUnit = null;
		type = null;
		method = null;
		qaMethod = null;
		invalid = false;
	}
	
	private void initPreferences() {
		prefs = PreferenceHelper.initPreferences();
		String message = PreferenceHelper.validate(prefs);
		
		if (StringUtils.isNotBlank(message)) {
			invalid = true;
			setMessages(message);
		}
	}
	
	private void process() {
		if (invalid) {
			return;
		}
		
		handleProject();
		handlePackage();
		handleUnit();
		handleType();
		handleMethod();
		handleCalles();
	}
	
	private void handleProject() {
		project = ProjectHelper.getProject(prefs.getProjectName());
		
		if (project == null) {
			setMessages("Project '", prefs.getProjectName(), WAS_NOT_FOUND);
		}
	}

	private void handlePackage() {
		if (project == null) {
			return;
		}

		packageFragment = PackageHelper.getPackage(project, prefs.getPackageName());

		if (packageFragment == null) {
			setMessages("Package '", prefs.getPackageName(), WAS_NOT_FOUND);
		}
	}

	private void handleUnit() {
		if (packageFragment == null) {
			return;
		}

		compilationUnit = UnitHelper.getUnit(packageFragment, prefs.getUnitName());

		if (compilationUnit == null) {
			setMessages("Compilation unit '", prefs.getUnitName(), WAS_NOT_FOUND);
		}
	}

	private void handleType() {
		if (compilationUnit == null) {
			return;
		}

		type = TypeHelper.getType(compilationUnit, prefs.getTypeName());

		if (type == null) {
			setMessages("Type '", prefs.getTypeName(), WAS_NOT_FOUND);
		}
	}

	private void handleMethod() {
		if (type == null) {
			return;
		}

		method = MethodHelper.getMethod(type, prefs.getMethodSignature());

		if (method == null) {
			setMessages("Method '", prefs.getMethodSignature(), WAS_NOT_FOUND);
		}
	}

	private void handleCalles() {
		if (method == null) {
			return;
		}

		qaMethod = MethodHelper.getCalleesOf(method, null, 1, prefs.getMainType(), prefs.getNotQaTypes(), prefs.getMaxRecursionLevel());

		if (qaMethod == null || qaMethod.getCallees().isEmpty()) {
			setMessages("Method '", prefs.getMethodSignature(), "' does not have callees.");
		}
	}

	private void setMessages(String... args) {
		runMessage.append("\n");
		
		for (String str : args) {
			runMessage.append(str);
		}
	}
}
