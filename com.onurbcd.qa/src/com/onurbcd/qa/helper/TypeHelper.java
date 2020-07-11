package com.onurbcd.qa.helper;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public class TypeHelper {

	private TypeHelper() {
	}

	public static IType getType(ICompilationUnit compilationUnit, String typeName) {
		if (compilationUnit == null || typeName == null || typeName.trim().isEmpty()) {
			return null;
		}

		IType[] types;

		try {
			types = compilationUnit.getTypes();
		} catch (JavaModelException e) {
			return null;
		}

		if (types == null || types.length <= 0) {
			return null;
		}

		return Arrays.stream(types).filter(p -> p.getFullyQualifiedName().equals(typeName)).findFirst().orElse(null);
	}

	public static boolean isQaType(IType type, String mainType, List<String> notQaTypes) {
		if (type == null || type.getFullyQualifiedName() == null || type.getFullyQualifiedName().trim().isEmpty()
				|| mainType == null || mainType.trim().isEmpty()
				|| !type.getFullyQualifiedName().startsWith(mainType)) {
			return false;
		}

		if (notQaTypes != null && !notQaTypes.isEmpty()) {
			for (String notQaType : notQaTypes) {
				if (type.getFullyQualifiedName().startsWith(notQaType)) {
					return false;
				}
			}
		}

		return true;
	}
}
