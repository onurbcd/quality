package com.onurbcd.qa.helper;

import java.util.Arrays;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;

public class UnitHelper {

	private UnitHelper() {
	}

	public static ICompilationUnit getUnit(IPackageFragment packageFragment, String unitName) throws JavaModelException {
		if (packageFragment == null || unitName == null || unitName.trim().isEmpty()) {
			return null;
		}
		
		ICompilationUnit[] compilationUnits = packageFragment.getCompilationUnits();
		
		if (compilationUnits == null || compilationUnits.length <= 0) {
			return null;
		}

		return Arrays
				.stream(compilationUnits)
        		.filter(p -> p.getElementName().equals(unitName))
        		.findFirst()
        		.orElse(null);
	}
}
