package com.onurbcd.qa.helper;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.internal.corext.callhierarchy.CallHierarchy;
import org.eclipse.jdt.internal.corext.callhierarchy.MethodWrapper;

import com.onurbcd.qa.util.QaMethod;

/**
 * @see https://www.cct.lsu.edu/~rguidry/ecl31docs/api/org/eclipse/jdt/internal/core/SourceMethod.html#getSignature()
 *      https://www.cct.lsu.edu/~rguidry/ecl31docs/api/org/eclipse/jdt/core/Signature.html
 */
@SuppressWarnings("restriction")
public class MethodHelper {

	private MethodHelper() {
	}

	public static IMethod getMethod(IType type, String methodSignature) {
		if (type == null || StringUtils.isBlank(methodSignature)) {
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
				.filter(p -> methodSignature.equals(getMethodFullName(p)))
				.findFirst().orElse(null);
	}

	public static QaMethod getCalleesOf(IMethod method, QaMethod parentMethod, int level, String mainType, List<String> notQaTypes, int maxRecursionLevel) {
		QaMethod qaMethod = new QaMethod(level, method, parentMethod);
		
		if (level > maxRecursionLevel) {
			return qaMethod;
		}

		Set<IMethod> methods = getCalleesMethods(method, mainType, notQaTypes);
		
		if (methods.isEmpty()) {
			return qaMethod;
		}
		
		for (IMethod calleeMethod : methods) {
			if (qaMethod.isReincarnation(calleeMethod)) {
				continue;
			}

			QaMethod calleeQaMethod = getCalleesOf(calleeMethod, qaMethod, level + 1, mainType, notQaTypes, maxRecursionLevel);
			qaMethod.addtoCallees(calleeQaMethod);
		}

		return qaMethod;
	}
	
	public static String getMethodFullName(IMethod iMethod) {
        StringBuilder name = new StringBuilder();

        try {
        	String signature = Signature.toString(iMethod.getSignature());
        	int firstIndexOfEmpty = signature.indexOf(' ');
        	String returnType = signature.substring(0, firstIndexOfEmpty);
        	String params = signature.substring(firstIndexOfEmpty + 1, signature.length());
        	name.append(FlagsHelper.getModifier(iMethod.getFlags()))
            .append(" ")
            .append(returnType)
            .append(" ")
            .append(iMethod.getElementName())
            .append(params);
        } catch (JavaModelException e) {
        	return "error getMethodFullName";
        }

        return name.toString();
	}

	private static Set<IMethod> getCalleesMethods(IMethod method, String mainType, List<String> notQaTypes) {
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

			return getIMethods(methodWrappers2, method, mainType, notQaTypes);
		}

		return new LinkedHashSet<>();
	}

	private static Set<IMethod> getIMethods(MethodWrapper[] methodWrappers, IMethod parentMethod, String mainType, List<String> notQaTypes) {
		Set<IMethod> methods = new LinkedHashSet<>();

		for (MethodWrapper methodWrapper : methodWrappers) {
			IMethod method = getIMethodFromMethodWrapper(methodWrapper);

			if (method != null &&
					!parentMethod.equals(method) &&
					!methods.contains(method) &&
					TypeHelper.isQaType(method.getDeclaringType(), mainType, notQaTypes)) {

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
	
	public static boolean isConstructor(IMethod method) {
		try {
			return method.isConstructor();
		} catch (JavaModelException e) {
			return false;
		}
	}
}
