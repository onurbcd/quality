package com.onurbcd.qa.helper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import org.eclipse.jdt.internal.corext.callhierarchy.CallHierarchy;
import org.eclipse.jdt.internal.corext.callhierarchy.MethodWrapper;

/**
 * @see https://www.cct.lsu.edu/~rguidry/ecl31docs/api/org/eclipse/jdt/internal/core/SourceMethod.html#getSignature()
 *      https://www.cct.lsu.edu/~rguidry/ecl31docs/api/org/eclipse/jdt/core/Signature.html
 * @author Bruno Domingues
 */
@SuppressWarnings("restriction")
public class MethodHelper {

	private static final Set<String> NOT_QA_TYPES = new HashSet<>(Arrays.asList(
		"br.com.engdb.geotec.async",
		"br.com.engdb.geotec.config",
		"br.com.engdb.geotec.domain",
		"br.com.engdb.geotec.dto",
		"br.com.engdb.geotec.enums",
		"br.com.engdb.geotec.exception",
		"br.com.engdb.geotec.interfaces",
		"br.com.engdb.geotec.report.dto",
		"br.com.engdb.geotec.ionic.v1.dto",
		"br.com.engdb.geotec.repository",
		"br.com.engdb.geotec.web.exception",
		"br.com.engdb.geotec.web.rest.dto",
		"br.com.engdb.geotec.web.rest.errors",
		"br.com.engdb.geotec.web.rest.grg.dto",
		"br.com.engdb.geotec.web.rest.mapper",
		"br.com.engdb.geotec.web.rest.pims.dto",
		"br.com.engdb.geotec.web.rest.vm",
		"br.com.engdb.geotec.web.soap.grg.dto",
		"br.com.engdb.geotec.web.soap.pims.entity"
	));

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

	public static Set<IMethod> getCalleesOf(IMethod method, int level) {
		Set<IMethod> callees = new HashSet<>();
		callees.add(method);
		
		if (level > 2) {
			return callees;
		}

		CallHierarchy callHierarchy = CallHierarchy.getDefault();
		IMember[] members = {method};
		MethodWrapper[] methodWrappers = callHierarchy.getCalleeRoots(members);
		
		if (methodWrappers == null || methodWrappers.length <= 0) {
			return callees;
		}
		
		for (MethodWrapper methodWrapper : methodWrappers) {
			MethodWrapper[] methodWrappers2 = methodWrapper.getCalls(new NullProgressMonitor());

			if (methodWrappers2 == null || methodWrappers2.length <= 0) {
				continue;
			}

			Set<IMethod> methods = getIMethods(methodWrappers2);
			
			if (!methods.isEmpty()) {
				callees.addAll(methods);
				
				for (IMethod calleeMethod : methods) {
					Set<IMethod> calleeMethods = getCalleesOf(calleeMethod, level + 1);
					
					if (!calleeMethods.isEmpty()) {
						callees.addAll(calleeMethods);
					}
				}
			}
		}

		return callees;
	}

	private static boolean safeEquals(IMethod method, String methodSignature) {
		try {
			return method != null && method.getSignature().equals(methodSignature);
		} catch (JavaModelException e) {
			return false;
		}
	}

	private static Set<IMethod> getIMethods(MethodWrapper[] methodWrappers) {
		Set<IMethod> methods = new HashSet<>();

		for (MethodWrapper methodWrapper : methodWrappers) {
			IMethod method = getIMethodFromMethodWrapper(methodWrapper);

			if (method != null && TypeHelper.isQaType(method.getDeclaringType(), "br.com.engdb.geotec", NOT_QA_TYPES)) {
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
			e.printStackTrace();
		}

		return null;
	}
}
