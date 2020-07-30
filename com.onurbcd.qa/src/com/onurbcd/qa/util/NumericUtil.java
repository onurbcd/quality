package com.onurbcd.qa.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumericUtil {
	
	public static final String DIGITS = "(\\p{Digit}+)";
	
	public static final String HEXDIGITS = "(\\p{XDigit}+)";

    // an exponent is 'e' or 'E' followed by an optionally
    // signed decimal integer.
	public static final String EXP = "[eE][+-]?" + DIGITS;

	public static final String REGEX_NUMERIC_VALUE = ("[\\x00-\\x20]*" + // Optional leading "whitespace"
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
            // The Java� Language Specification.
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

	private NumericUtil() {
	}
	
	public static double round(double value, int places) {
	    if (places < 0) {
	    	throw new IllegalArgumentException();
	    }

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public static Double stringToDouble(String strValue) {
        Double value = null;

        try {
        	value = new Double(strValue);
        } catch (NumberFormatException | NullPointerException ex) {
            return null;
        }

        return value;
    }
}
