package com.onurbcd.qa.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class RegexUtil {

	private RegexUtil() {
	}
	
	public static String find(String regex, String input) {
		if (StringUtils.isBlank(regex) || StringUtils.isBlank(input)) {
			return null;
		}
		
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? matcher.group().trim() : null;
    }
	
	public static Double getNumericValue(String regex, String input, String regexReplace) {
		if (StringUtils.isBlank(regex) || StringUtils.isBlank(input) || StringUtils.isBlank(regexReplace)) {
			return null;
		}
		
        String value = find(regex, input);

        if (value == null) {
            return null;
        }

    	value = value.replaceAll(regexReplace, StringUtils.EMPTY);
        return NumericUtil.stringToDouble(value);
    }
}
