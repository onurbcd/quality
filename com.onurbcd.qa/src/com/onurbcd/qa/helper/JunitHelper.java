package com.onurbcd.qa.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class JunitHelper {
	
	private static final String DIGITS = "(\\p{Digit}+)";
    private static final String HEXDIGITS = "(\\p{XDigit}+)";
    // an exponent is 'e' or 'E' followed by an optionally
    // signed decimal integer.
    private static final String EXP = "[eE][+-]?" + DIGITS;
    private static final String REGEX_NUMERIC_VALUE = ("[\\x00-\\x20]*" + // Optional leading "whitespace"
            "[+-]?(" + // Optional sign character
            "NaN|" + // "NaN" string
            "Infinity|" + // "Infinity" string
            // A decimal floating-point string representing a finite positive
            // number without a leading sign has at most five basic pieces:
            // Digits . Digits ExponentPart FloatTypeSuffix
            //
            // Since this method allows integer-only strings as input
            // in addition to strings of floating-point literals, the
            // two sub-patterns below are simplifications of the grammar
            // productions from section 3.10.2 of
            // The Java™ Language Specification.
            // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
            "(((" + DIGITS + "(\\.)?(" + DIGITS + "?)(" + EXP + ")?)|" +
            // . Digits ExponentPart_opt FloatTypeSuffix_opt
            "(\\.(" + DIGITS + ")(" + EXP + ")?)|" +
            // Hexadecimal strings
            "((" +
            // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
            "(0[xX]" + HEXDIGITS + "(\\.)?)|" +
            // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
            "(0[xX]" + HEXDIGITS + "?(\\.)" + HEXDIGITS + ")" + ")[pP][+-]?" + DIGITS + "))" + "[fFdD]?))"
            + "[\\x00-\\x20]*");// Optional trailing "whitespace"
    
    private static final String REGEX_MISSED = "missed=\"";
    
    private static final String REGEX_COVERED = "covered=\"";

	private JunitHelper() {
	}

	public static double processCoverageReport(String xml, String className, String method) {
		if (StringUtils.isBlank(xml) || StringUtils.isBlank(className) || StringUtils.isBlank(method)) {
			return 0d;
		}

		String findClassName = className.replace('.', '/');
		int indexOfClass = xml.indexOf(findClassName);

		if (indexOfClass == -1) {
			return 0d;
		}

		int indexOfMethod = xml.indexOf(method, indexOfClass);

		if (indexOfMethod == -1) {
			return 0d;
		}
		
		int indexOfInstruction = xml.indexOf("INSTRUCTION", indexOfMethod);
		
		if (indexOfInstruction == -1) {
			return 0d;
		}
		
		int indexOfCloseCounter = xml.indexOf("/>", indexOfInstruction);
		
		if (indexOfCloseCounter == -1) {
			return 0d;
		}
		
		String xmlInstructionCounter = xml.substring(indexOfInstruction, indexOfCloseCounter);
		
		if (StringUtils.isBlank(xmlInstructionCounter)) {
			return 0d;
		}
		
		double missed = getNumericValue(REGEX_MISSED + REGEX_NUMERIC_VALUE, xmlInstructionCounter, REGEX_MISSED);
		double covered = getNumericValue(REGEX_COVERED + REGEX_NUMERIC_VALUE, xmlInstructionCounter, REGEX_COVERED);
		double total = missed + covered;
		return total == 0d ? 0d : (covered / total);
	}
	
	private static double getNumericValue(String regex, String input, String regexReplace) {
        String value = find(regex, input);

        if (value == null) {
            return 0d;
        }

    	value = value.replaceAll(regexReplace, StringUtils.EMPTY);
    	Double convertedValue = stringValueToDouble(value); 
        return convertedValue != null ? convertedValue : 0d;
    }
	
	private static String find(String regex, CharSequence input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? matcher.group().trim() : null;
    }
	
	private static Double stringValueToDouble(String strValue) {
        Double value = null;

        try {
        	value = new Double(strValue);
        } catch (NumberFormatException | NullPointerException ex) {
            return null;
        }

        return value;
    }
}
