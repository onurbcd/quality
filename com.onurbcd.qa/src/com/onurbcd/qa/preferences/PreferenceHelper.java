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
				StringUtils.trim(store.getString(PreferenceConstants.P_METHOD_NAME)),
				store.getInt(PreferenceConstants.P_MAX_RECURSION_LEVEL)
		);
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
		sb.append(StringUtils.isBlank(preferences.getMethodName()) ? "Method Name is required" : StringUtils.EMPTY);
		return sb.toString();
	}
}
