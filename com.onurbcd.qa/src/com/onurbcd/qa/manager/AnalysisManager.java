package com.onurbcd.qa.manager;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;

import com.onurbcd.qa.helper.MethodHelper;
import com.onurbcd.qa.helper.PackageHelper;
import com.onurbcd.qa.helper.ProjectHelper;
import com.onurbcd.qa.helper.TypeHelper;
import com.onurbcd.qa.helper.UnitHelper;

public class AnalysisManager {
	
	private static final String PROJECT_NAME = "geotec";

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
			"br.com.engdb.geotec.web.soap.pims.entity"));

	private static final String WAS_NOT_FOUND = "' was not found.";
	
	private StringBuilder sb;
	
	private IProject project;

	private IPackageFragment packageFragment;

	private ICompilationUnit compilationUnit;

	private IType type;

	private IMethod method;

	private Set<IMethod> callees;

	public AnalysisManager() {
		project = null;
		packageFragment = null;
		compilationUnit = null;
		type = null;
		method = null;
		callees = null;
	}

	public void run() {
		Instant start = Instant.now();
		sb = new StringBuilder("");
		handleProject();
		handlePackage();
		handleUnit();
		handleType();
		handleMethod();
		handleCalles();
		setMethodsNamesInMessage();
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).getSeconds();
		sb.append("\n\n").append("DURATION IN SECONDS: ").append(timeElapsed);
	}
	
	public String getMessage() {
		return sb != null ? sb.toString() : "Call method 'run' before get message.";
	}
	
	private void handleProject() {
		project = ProjectHelper.getProject(PROJECT_NAME);
		
		if (project == null) {
			sb.append("\n").append("Project '").append(PROJECT_NAME).append(WAS_NOT_FOUND);
		}
	}

	private void handlePackage() {
		if (project == null) {
			return;
		}

		packageFragment = PackageHelper.getPackage(project, PACKAGE_NAME);

		if (packageFragment == null) {
			sb.append("\n").append("Package '").append(PACKAGE_NAME).append(WAS_NOT_FOUND);
		}
	}

	private void handleUnit() {
		if (packageFragment == null) {
			return;
		}

		compilationUnit = UnitHelper.getUnit(packageFragment, UNIT_NAME);

		if (compilationUnit == null) {
			sb.append("\n").append("Compilation unit '").append(UNIT_NAME).append(WAS_NOT_FOUND);
		}
	}

	private void handleType() {
		if (compilationUnit == null) {
			return;
		}

		type = TypeHelper.getType(compilationUnit, TYPE_NAME);

		if (type == null) {
			sb.append("\n").append("Type '").append(TYPE_NAME).append(WAS_NOT_FOUND);
		}
	}

	private void handleMethod() {
		if (type == null) {
			return;
		}

		method = MethodHelper.getMethod(type, METHOD_NAME, METHOD_SIGNATURE);

		if (method == null) {
			sb.append("\n").append("Method '").append(METHOD_NAME).append("' with signature '").append(METHOD_SIGNATURE).append(WAS_NOT_FOUND);
		}
	}

	private void handleCalles() {
		if (method == null) {
			return;
		}

		callees = MethodHelper.getCalleesOf(method, 1, MAIN_TYPE, NOT_QA_TYPES);

		if (callees == null || callees.isEmpty()) {
			sb.append("\n").append("Method '").append(METHOD_NAME).append("' does not have callees.");
		}
	}

	private void setMethodsNamesInMessage() {
		if (callees == null || callees.isEmpty()) {
			return;
		}

		for (IMethod calleMethod : callees) {
			sb.append("\n").append(calleMethod.getElementName());
		}
	}
}
