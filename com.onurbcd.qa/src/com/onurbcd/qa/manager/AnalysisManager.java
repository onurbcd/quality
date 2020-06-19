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
import org.eclipse.jface.preference.IPreferenceStore;

import com.onurbcd.qa.Activator;
import com.onurbcd.qa.helper.MethodHelper;
import com.onurbcd.qa.helper.PackageHelper;
import com.onurbcd.qa.helper.ProjectHelper;
import com.onurbcd.qa.helper.ReportHelper;
import com.onurbcd.qa.helper.TypeHelper;
import com.onurbcd.qa.helper.UnitHelper;
import com.onurbcd.qa.preferences.PreferenceConstants;
import com.onurbcd.qa.util.DateTimeUtil;
import com.onurbcd.qa.util.FileUtil;
import com.onurbcd.qa.util.QaMethod;

public class AnalysisManager {
	
	// PRIVATE STATIC PROPERTIES
	
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

	private static final String FILE_PATH = "C:\\Users\\bmiguellei\\Desktop\\";
	
	private static final String FILE_NAME = "qa-analysis-";
	
	private static final String FILE_EXTENSION = ".txt";
	
	// PRIVATE PROPERTIES

	private StringBuilder runMessage;

	private StringBuilder fileContent;
	
	private IProject project;

	private IPackageFragment packageFragment;

	private ICompilationUnit compilationUnit;

	private IType type;

	private IMethod method;

	private QaMethod qaMethod;

	private int maxRecursionLevel;

	public AnalysisManager() {
		project = null;
		packageFragment = null;
		compilationUnit = null;
		type = null;
		method = null;
		qaMethod = null;
	}

	public void run() {
		Instant start = Instant.now();
		loadPreferences();
		runMessage = new StringBuilder("");
		fileContent = new StringBuilder("");
		setMessages(true, DateTimeUtil.getNowFormatted());
		handleProject();
		handlePackage();
		handleUnit();
		handleType();
		handleMethod();
		handleCalles();
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).getSeconds();
		setMessages(true, "\n\n", "DURATION IN SECONDS: ", String.valueOf(timeElapsed));
		int numberOfMethods = qaMethod != null ? qaMethod.getNumberOfMethods() : 0;
		setMessages(true, "\n\n", "NUMBER OF METHODS: ", String.valueOf(numberOfMethods));
		setMessages(true, "\n\n", "MAX RECURSION LEVEL: ", String.valueOf(maxRecursionLevel));
		setMethodsNamesInMessage();
		writeToFile();
		ReportHelper.processReport(qaMethod);
	}
	
	public String getMessage() {
		return runMessage != null ? runMessage.toString() : "Call method 'run' before get message.";
	}
	
	private void loadPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		maxRecursionLevel = store.getInt(PreferenceConstants.P_MAX_RECURSION_LEVEL);
	}
	
	private void handleProject() {
		project = ProjectHelper.getProject(PROJECT_NAME);
		
		if (project == null) {
			setMessages(true, "\n\n", "Project '", PROJECT_NAME, WAS_NOT_FOUND);
		}
	}

	private void handlePackage() {
		if (project == null) {
			return;
		}

		packageFragment = PackageHelper.getPackage(project, PACKAGE_NAME);

		if (packageFragment == null) {
			setMessages(true, "\n\n", "Package '", PACKAGE_NAME, WAS_NOT_FOUND);
		}
	}

	private void handleUnit() {
		if (packageFragment == null) {
			return;
		}

		compilationUnit = UnitHelper.getUnit(packageFragment, UNIT_NAME);

		if (compilationUnit == null) {
			setMessages(true, "\n\n", "Compilation unit '", UNIT_NAME, WAS_NOT_FOUND);
		}
	}

	private void handleType() {
		if (compilationUnit == null) {
			return;
		}

		type = TypeHelper.getType(compilationUnit, TYPE_NAME);

		if (type == null) {
			setMessages(true, "\n\n", "Type '", TYPE_NAME, WAS_NOT_FOUND);
		}
	}

	private void handleMethod() {
		if (type == null) {
			return;
		}

		method = MethodHelper.getMethod(type, METHOD_NAME, METHOD_SIGNATURE);

		if (method == null) {
			setMessages(true, "\n\n", "Method '", METHOD_NAME, "' with signature '", METHOD_SIGNATURE, WAS_NOT_FOUND);
		}
	}

	private void handleCalles() {
		if (method == null) {
			return;
		}

		qaMethod = MethodHelper.getCalleesOf(method, null, 1, MAIN_TYPE, NOT_QA_TYPES, maxRecursionLevel);

		if (qaMethod == null || qaMethod.getCallees().isEmpty()) {
			setMessages(true, "\n\n", "Method '", METHOD_NAME, "' does not have callees.");
		}
	}

	private void setMethodsNamesInMessage() {
		if (qaMethod == null) {
			return;
		}

		setMessages(false, "\n", qaMethod.toString());
	}

	private void writeToFile() {
		String fullFileName = FILE_PATH + FILE_NAME + DateTimeUtil.getNowFormatted() + FILE_EXTENSION;
		FileUtil.writeStringToFile(fullFileName, fileContent.toString());
	}

	private void setMessages(boolean addToMessage, String... args) {
		for (String str : args) {
			runMessage.append(addToMessage ? str : "");
			fileContent.append(str);
		}
	}
}
