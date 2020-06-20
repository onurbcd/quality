package com.onurbcd.qa.util;

import java.util.regex.Pattern;

public class StringUtil {

	private StringUtil() {
	}

	public static String fill(int number, String value) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < number; i++) {
			sb.append(value);
		}

		return sb.toString();
	}

	public static int countOccurrences(String strSource, String pattern) {
		if (strSource == null || strSource.trim().isEmpty() || pattern == null || pattern.trim().isEmpty()) {
			return 0;
		}

		return (strSource.split(Pattern.quote(pattern), -1).length) - 1;
	}
}
