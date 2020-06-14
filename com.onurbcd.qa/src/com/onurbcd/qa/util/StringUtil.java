package com.onurbcd.qa.util;

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
}
