package com.onurbcd.qa.helper;

import org.eclipse.jdt.core.Flags;

public class FlagsHelper {

	private FlagsHelper() {
	}
	
	public static String getModifier(int flags) {
		if (Flags.isPublic(flags)) {
			return "public";
		} else if (Flags.isPrivate(flags)) {
			return "private";
		} else if (Flags.isProtected(flags)) {
			return "protected";
		}
		
		return "other modifier";
	}
}
