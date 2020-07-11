package com.onurbcd.qa.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import com.onurbcd.qa.Activator;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */
public class QaPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public QaPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Quality Analysis Preferences");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
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
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
}
