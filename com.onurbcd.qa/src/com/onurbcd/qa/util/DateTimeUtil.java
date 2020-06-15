package com.onurbcd.qa.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeUtil {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter
			.ofPattern("yyyy-MM-dd-HH-mm-ss")
            .withLocale(Locale.getDefault())
            .withZone(ZoneId.systemDefault());

	private DateTimeUtil() {
	}

	public static String getNowFormatted() {
		return FORMATTER.format(Instant.now()); 
	}
}
