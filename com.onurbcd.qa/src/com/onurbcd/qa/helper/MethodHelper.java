package com.onurbcd.qa.helper;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import org.eclipse.jdt.internal.corext.callhierarchy.CallHierarchy;
import org.eclipse.jdt.internal.corext.callhierarchy.MethodWrapper;

import com.onurbcd.qa.util.QaMethod;

/**
 * @see https://www.cct.lsu.edu/~rguidry/ecl31docs/api/org/eclipse/jdt/internal/core/SourceMethod.html#getSignature()
 *      https://www.cct.lsu.edu/~rguidry/ecl31docs/api/org/eclipse/jdt/core/Signature.html
 */
@SuppressWarnings("restriction")
public class MethodHelper {

	private static final int MAX_LEVEL = 3;

	private MethodHelper() {
	}

	public static IMethod getMethod(IType type, String methodName, String methodSignature) {
		if (type == null || methodName == null || methodName.trim().isEmpty() || methodSignature == null || methodSignature.trim().isEmpty()) {
			return null;
		}

		IMethod[] methods;

		try {
			methods = type.getMethods();
		} catch (JavaModelException e) {
			return null;
		}

		if (methods == null || methods.length <= 0) {
			return null;
		}

		return Arrays
				.stream(methods)
				.filter(p -> p.getElementName().equals(methodName) &&
						safeEquals(p, methodSignature))
				.findFirst().orElse(null);
	}

	public static QaMethod getCalleesOf(IMethod method, int level, String mainType, Set<String> notQaTypes) {
		QaMethod qaMethod = new QaMethod(level, method);
		
		if (level > MAX_LEVEL) {
			return qaMethod;
		}

		Set<IMethod> methods = getCalleesMethods(method, mainType, notQaTypes);
		
		if (methods.isEmpty()) {
			return qaMethod;
		}
		
		for (IMethod calleeMethod : methods) {
			QaMethod calleeQaMethod = getCalleesOf(calleeMethod, level + 1, mainType, notQaTypes);
			qaMethod.addtoCallees(calleeQaMethod);
		}

		return qaMethod;
	}

	private static boolean safeEquals(IMethod method, String methodSignature) {
		try {
			return method != null && method.getSignature().equals(methodSignature);
		} catch (JavaModelException e) {
			return false;
		}
	}

	private static Set<IMethod> getCalleesMethods(IMethod method, String mainType, Set<String> notQaTypes) {
		CallHierarchy callHierarchy = CallHierarchy.getDefault();
		IMember[] members = {method};
		MethodWrapper[] methodWrappers = callHierarchy.getCalleeRoots(members);
		
		if (methodWrappers == null || methodWrappers.length <= 0) {
			return new LinkedHashSet<>();
		}

		for (MethodWrapper methodWrapper : methodWrappers) {
			MethodWrapper[] methodWrappers2 = methodWrapper.getCalls(new NullProgressMonitor());

			if (methodWrappers2 == null || methodWrappers2.length <= 0) {
				continue;
			}

			return getIMethods(methodWrappers2, mainType, notQaTypes);
		}

		return new LinkedHashSet<>();
	}

	private static Set<IMethod> getIMethods(MethodWrapper[] methodWrappers, String mainType, Set<String> notQaTypes) {
		Set<IMethod> methods = new LinkedHashSet<>();

		for (MethodWrapper methodWrapper : methodWrappers) {
			IMethod method = getIMethodFromMethodWrapper(methodWrapper);

			if (method != null && TypeHelper.isQaType(method.getDeclaringType(), mainType, notQaTypes)) {
				methods.add(method);
			}
		}

		return methods;
	}

	private static IMethod getIMethodFromMethodWrapper(MethodWrapper methodWrapper) {
		try {
			IMember member = methodWrapper.getMember();

			if (member != null && member.getElementType() == IJavaElement.METHOD) {
				return (IMethod) methodWrapper.getMember();
			}
		} catch (Exception e) {
			return null;
		}

		return null;
	}
}
