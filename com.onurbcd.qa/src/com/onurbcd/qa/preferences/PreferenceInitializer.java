package com.onurbcd.qa.preferences;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.onurbcd.qa.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
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
	}
}
