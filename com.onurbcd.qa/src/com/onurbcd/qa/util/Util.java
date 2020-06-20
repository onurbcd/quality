package com.onurbcd.qa.util;

public class Util {

	private Util() {
	}
	
	public static <T> T safeCast(Object o, Class<T> clazz) {
        return clazz != null && clazz.isInstance(o) ? clazz.cast(o) : null;
    }
}
