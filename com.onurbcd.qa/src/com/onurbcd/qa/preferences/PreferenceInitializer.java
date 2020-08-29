package com.onurbcd.qa.preferences;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.onurbcd.qa.Activator;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_PROJECT_NAME, StringUtils.EMPTY);
		store.setDefault(PreferenceConstants.P_PACKAGE_NAME, StringUtils.EMPTY);
		store.setDefault(PreferenceConstants.P_UNIT_NAME, StringUtils.EMPTY);
		store.setDefault(PreferenceConstants.P_TYPE_NAME, StringUtils.EMPTY);
		store.setDefault(PreferenceConstants.P_METHOD_SIGNATURE, StringUtils.EMPTY);
		store.setDefault(PreferenceConstants.P_MAIN_TYPE, StringUtils.EMPTY);
		store.setDefault(PreferenceConstants.P_NOT_QA_TYPES, StringUtils.EMPTY);
		store.setDefault(PreferenceConstants.P_MAX_RECURSION_LEVEL, 1);
		store.setDefault(PreferenceConstants.P_REPORT_FILE_PATH, StringUtils.EMPTY);
		store.setDefault(PreferenceConstants.P_JUNIT_COVERAGE_REPORT, StringUtils.EMPTY);
		store.setDefault(PreferenceConstants.P_MIN_PERCENT_RATE, 1);
		store.setDefault(PreferenceConstants.P_MCC_RATE, 1);
		store.setDefault(PreferenceConstants.P_LOC_RATE, 1);
	}
}
