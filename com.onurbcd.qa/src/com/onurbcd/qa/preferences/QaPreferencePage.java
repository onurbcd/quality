package com.onurbcd.qa.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import com.onurbcd.qa.Activator;

public class QaPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public QaPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Quality Analysis Preferences");
	}

	public void createFieldEditors() {
		addField(new StringFieldEditor(PreferenceConstants.P_PROJECT_NAME, "Project Name:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.P_PACKAGE_NAME, "Package Name:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.P_UNIT_NAME, "Unit Name:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.P_TYPE_NAME, "Type Name:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.P_METHOD_SIGNATURE, "Method Signature:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.P_MAIN_TYPE, "Main Type:", getFieldEditorParent()));
		addField(new QaListEditor(PreferenceConstants.P_NOT_QA_TYPES, "Not QA Types:", getFieldEditorParent()));
		IntegerFieldEditor maxRecursionLevel = new IntegerFieldEditor(PreferenceConstants.P_MAX_RECURSION_LEVEL, "Max Recursion Level", getFieldEditorParent());
		maxRecursionLevel.setValidRange(0, 99);
		addField(maxRecursionLevel);
		addField(new DirectoryFieldEditor(PreferenceConstants.P_REPORT_FILE_PATH, "Report File Path:", getFieldEditorParent()));
		addField(new FileFieldEditor(PreferenceConstants.P_JUNIT_COVERAGE_REPORT, "Junit Coverage Report:", getFieldEditorParent()));
		IntegerFieldEditor junitCoverageReport = new IntegerFieldEditor(PreferenceConstants.P_MIN_PERCENT_RATE, "Minimum Percentage Rate:", getFieldEditorParent());
		junitCoverageReport.setValidRange(1, 100);
		addField(junitCoverageReport);
	}

	public void init(IWorkbench workbench) {
	}
}
