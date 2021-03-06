package com.onurbcd.qa.preferences;

/**
 * Constant definitions for plug-in preferences
 */
public class PreferenceConstants {

	private PreferenceConstants() {
	}

	public static final char SEPARATOR = ';';

	public static final String P_PROJECT_NAME = "projectName";

	public static final String P_PACKAGE_NAME = "packageName";

	public static final String P_UNIT_NAME = "unitName";

	public static final String P_TYPE_NAME = "typeName";

	public static final String P_METHOD_SIGNATURE = "methodSignature";

	public static final String P_MAIN_TYPE = "mainType";

	public static final String P_NOT_QA_TYPES = "notQaTypes";

	public static final String P_MAX_RECURSION_LEVEL = "maxRecursionLevel";

	public static final String P_REPORT_FILE_PATH = "reportFilePath";

	public static final String P_JUNIT_COVERAGE_REPORT = "junitCoverageReport";

	public static final String P_MIN_PERCENT_RATE = "minPercentRate";
	
	public static final String P_MCC_RATE = "mccRate";
	
	public static final String P_LOC_RATE = "locRate";
	
	public static final String NOT_QA_TYPES_DIALOG_TITLE = "Not QA Type";
	
	public static final String NOT_QA_TYPES_DIALOG_MESSAGE = "Enter a package name or class type not be be included in the analysis";
	
	public static final String METHOD_SIGNATURE_DIALOG_TITLE = "Method Signature";
	
	public static final String METHOD_SIGNATURE_DIALOG_MESSAGE = "Enter a method signature (all must be from the same class)";
}
