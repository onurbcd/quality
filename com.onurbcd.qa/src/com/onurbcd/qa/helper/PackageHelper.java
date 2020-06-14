package com.onurbcd.qa.helper;

import java.util.Arrays;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

public class PackageHelper {

	private PackageHelper() {
    }

    public static IPackageFragment getPackage(IProject project, String packageName) {
        if (project == null || packageName == null || packageName.trim().isEmpty()) {
            return null;
        }
        
        IJavaProject javaProject = JavaCore.create(project);
        
        if (javaProject == null) {
        	return null;
        }
        
        IPackageFragment[] packageFragments = null;

		try {
			packageFragments = javaProject.getPackageFragments();
		} catch (JavaModelException e) {
			return null;
		}
        
        if (packageFragments == null || packageFragments.length <= 0) {
        	return null;
        }
        
        return Arrays
        		.stream(packageFragments)
        		.filter(p -> p.getElementName().equals(packageName))
        		.findFirst()
        		.orElse(null);
    }
}
