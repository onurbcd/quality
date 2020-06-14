package com.onurbcd.qa.manager;

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
		// TODO Retirar os try catch dos handlers e colocar nos m�todos est�ticos que eles chamam
		// TODO Adicionar um contador para verificar quanto tempo o run demora a executar
		sb = new StringBuilder("");
		handleProject();
		handlePackage();
		handleUnit();
		handleType();
		handleMethod();
		handleCalles();
		setMethodsNamesInMessage();
		sb.append("\n").append("So far, so good!");
	}
	
	public String getMessage() {
		return sb != null ? sb.toString() : "Call method 'run' before get message.";
	}
	
	private void handleProject() {
		project = ProjectHelper.getProject(PROJECT_NAME);
		
		if (project == null) {
			sb.append("\n").append("Project '").append(PROJECT_NAME).append("' was not found.");
		}
	}

	private void handlePackage() {
		if (project == null) {
			return;
		}

		packageFragment = PackageHelper.getPackage(project, PACKAGE_NAME);

		if (packageFragment == null) {
			sb.append("\n").append("Package '").append(PACKAGE_NAME).append("' was not found.");
		}
	}

	private void handleUnit() {
		if (packageFragment == null) {
			return;
		}

		compilationUnit = UnitHelper.getUnit(packageFragment, UNIT_NAME);

		if (compilationUnit == null) {
			sb.append("\n").append("Compilation unit '").append(UNIT_NAME).append("' was not found.");
		}
	}

	private void handleType() {
		if (compilationUnit == null) {
			return;
		}

		type = TypeHelper.getType(compilationUnit, TYPE_NAME);

		if (type == null) {
			sb.append("\n").append("Type '").append(TYPE_NAME).append("' was not found.");
		}
	}

	private void handleMethod() {
		if (type == null) {
			return;
		}

		method = MethodHelper.getMethod(type, METHOD_NAME, METHOD_SIGNATURE);

		if (method == null) {
			sb.append("\n").append("Method '").append(METHOD_NAME).append("' with signature '").append(METHOD_SIGNATURE).append("' was not found.");
		}
	}

	private void handleCalles() {
		if (method == null) {
			return;
		}

		callees = MethodHelper.getCalleesOf(method, 1);

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
