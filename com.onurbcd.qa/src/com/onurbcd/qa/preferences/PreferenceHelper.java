package com.onurbcd.qa.preferences;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.preference.IPreferenceStore;

import com.onurbcd.qa.Activator;

public class PreferenceHelper {

	private PreferenceHelper() {
	}
	
	public static QaPreference initPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		return new QaPreference(
				StringUtils.trim(store.getString(PreferenceConstants.P_PROJECT_NAME)),
				StringUtils.trim(store.getString(PreferenceConstants.P_PACKAGE_NAME)),
				StringUtils.trim(store.getString(PreferenceConstants.P_UNIT_NAME)),
				StringUtils.trim(store.getString(PreferenceConstants.P_TYPE_NAME)),
				StringUtils.trim(store.getString(PreferenceConstants.P_METHOD_SIGNATURE)),
				StringUtils.trim(store.getString(PreferenceConstants.P_MAIN_TYPE)),
				StringUtils.trim(store.getString(PreferenceConstants.P_NOT_QA_TYPES)))
				.init(store.getInt(PreferenceConstants.P_MAX_RECURSION_LEVEL),
						StringUtils.trim(store.getString(PreferenceConstants.P_REPORT_FILE_PATH)),
						StringUtils.trim(store.getString(PreferenceConstants.P_JUNIT_COVERAGE_REPORT)));
	}
	
	public static String validate(QaPreference preferences) {
		if (preferences == null) {
			return StringUtils.EMPTY;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(StringUtils.isBlank(preferences.getProjectName()) ? "Project Name is required" : StringUtils.EMPTY);
		sb.append(StringUtils.isBlank(preferences.getPackageName()) ? "Package Name is required" : StringUtils.EMPTY);
		sb.append(StringUtils.isBlank(preferences.getUnitName()) ? "Unit Name is required" : StringUtils.EMPTY);
		sb.append(StringUtils.isBlank(preferences.getTypeName()) ? "Type Name is required" : StringUtils.EMPTY);
		sb.append(StringUtils.isBlank(preferences.getMethodSignature()) ? "Method Signature is required" : StringUtils.EMPTY);
		sb.append(StringUtils.isBlank(preferences.getMainType()) ? "Main Type is required" : StringUtils.EMPTY);
		sb.append(StringUtils.isBlank(preferences.getReportFilePath()) ? "Report File Path is required" : StringUtils.EMPTY);
		sb.append(StringUtils.isBlank(preferences.getJunitCoverageReport()) ? "Junit Coverage Report is required" : StringUtils.EMPTY);
		return sb.toString();
	}
}
