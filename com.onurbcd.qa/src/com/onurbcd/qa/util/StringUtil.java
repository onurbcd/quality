package com.onurbcd.qa.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.onurbcd.qa.preferences.PreferenceConstants;

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
	
	public static String inputStreamToString(InputStream inputStream) throws IOException {
		if (inputStream == null) {
			return null;
		}
		
	    StringBuilder sb = new StringBuilder();
	    String line;
	    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

	    while ((line = bufferedReader.readLine()) != null) {
	        sb.append(line);
	    }

	    bufferedReader.close();
	    return sb.toString();
	}
	
	public static List<String> stringToList(String str) {
		return StringUtils.isNotBlank(str) ? Arrays.asList(StringUtils.splitPreserveAllTokens(str, PreferenceConstants.SEPARATOR)) : Collections.emptyList();
	}
}
