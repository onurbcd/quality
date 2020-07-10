package com.onurbcd.qa.manager;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

	private static final String PACKAGE_NAME = "br.com.engdb.geotec.quartz.job";

	private static final String UNIT_NAME = "RealizarImportacaoEmLoteJob.java";

	private static final String TYPE_NAME = "br.com.engdb.geotec.quartz.job.RealizarImportacaoEmLoteJob";

	private static final String METHOD_NAME = "executeMonitored";

	private static final String METHOD_SIGNATURE = "(QJobExecutionContext;)V";

	private static final String MAIN_TYPE = "br.com.engdb.geotec";
	
	private static final Set<String> NOT_QA_TYPES = new HashSet<>(Arrays.asList(
			"br.com.engdb.geotec.async",
			"br.com.engdb.geotec.config",
			"br.com.engdb.geotec.domain",
			"br.com.engdb.geotec.dto",
			"br.com.engdb.geotec.enums",
			"br.com.engdb.geotec.exception",
			"br.com.engdb.geotec.interfaces",
			"br.com.engdb.geotec.report.dto",
			"br.com.engdb.geotec.ionic.v1.dto",
			"br.com.engdb.geotec.repository",
			"br.com.engdb.geotec.web.exception",
			"br.com.engdb.geotec.web.rest.dto",
			"br.com.engdb.geotec.web.rest.errors",
			"br.com.engdb.geotec.web.rest.grg.dto",
			"br.com.engdb.geotec.web.rest.mapper",
			"br.com.engdb.geotec.web.rest.pims.dto",
			"br.com.engdb.geotec.web.rest.vm",
			"br.com.engdb.geotec.web.soap.grg.dto",
			"br.com.engdb.geotec.web.soap.pims.entity"
			));

	private static final String WAS_NOT_FOUND = "' was not found.";
	
	// PRIVATE PROPERTIES

	private StringBuilder runMessage;
	
	private IProject project;

	private IPackageFragment packageFragment;

	private ICompilationUnit compilationUnit;

	private IType type;

	private IMethod method;

	private QaMethod qaMethod;

	private QaPreference qaPreference;
	
	private boolean invalid;

	// PUBLIC METHODS

	public void run() {
		Instant start = Instant.now();
		initVariables();
		setMessages(DateTimeUtil.getNowFormatted());
		initPreferences();
		process();
		ReportHelper.processReport(qaMethod);
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
		qaPreference = PreferenceHelper.initPreferences();
		String message = PreferenceHelper.validate(qaPreference);
		
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
		project = ProjectHelper.getProject(qaPreference.getProjectName());
		
		if (project == null) {
			setMessages("Project '", qaPreference.getProjectName(), WAS_NOT_FOUND);
		}
	}

	private void handlePackage() {
		if (project == null) {
			return;
		}

		packageFragment = PackageHelper.getPackage(project, PACKAGE_NAME);

		if (packageFragment == null) {
			setMessages("Package '", PACKAGE_NAME, WAS_NOT_FOUND);
		}
	}

	private void handleUnit() {
		if (packageFragment == null) {
			return;
		}

		compilationUnit = UnitHelper.getUnit(packageFragment, UNIT_NAME);

		if (compilationUnit == null) {
			setMessages("Compilation unit '", UNIT_NAME, WAS_NOT_FOUND);
		}
	}

	private void handleType() {
		if (compilationUnit == null) {
			return;
		}

		type = TypeHelper.getType(compilationUnit, TYPE_NAME);

		if (type == null) {
			setMessages("Type '", TYPE_NAME, WAS_NOT_FOUND);
		}
	}

	private void handleMethod() {
		if (type == null) {
			return;
		}

		method = MethodHelper.getMethod(type, METHOD_NAME, METHOD_SIGNATURE);

		if (method == null) {
			setMessages("Method '", METHOD_NAME, "' with signature '", METHOD_SIGNATURE, WAS_NOT_FOUND);
		}
	}

	private void handleCalles() {
		if (method == null) {
			return;
		}

		qaMethod = MethodHelper.getCalleesOf(method, null, 1, MAIN_TYPE, NOT_QA_TYPES, qaPreference.getMaxRecursionLevel());

		if (qaMethod == null || qaMethod.getCallees().isEmpty()) {
			setMessages("Method '", METHOD_NAME, "' does not have callees.");
		}
	}

	private void setMessages(String... args) {
		runMessage.append("\n");
		
		for (String str : args) {
			runMessage.append(str);
		}
	}
}
