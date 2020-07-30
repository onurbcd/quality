package com.onurbcd.qa.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.onurbcd.qa.util.NumericUtil;
import com.onurbcd.qa.util.RegexUtil;

public class JunitHelper {

	private static final char DOT = '.';
	
	private static final char SLASH = '/';

	private static final String INSTRUCTION = "INSTRUCTION";
	
	private static final String END_TAG = "/>";

	private static final String REGEX_MISSED = "missed=\"";
    
    private static final String REGEX_COVERED = "covered=\"";
    
    private static final String TAG_METHOD_CLOSE = "</method>";

	private JunitHelper() {
	}

	public static Double processCoverageReport(String xml, String className, String method, String signature) {
		if (StringUtils.isBlank(xml) || StringUtils.isBlank(className) || StringUtils.isBlank(method) || StringUtils.isBlank(signature)) {
			return null;
		}

		String findClassName = className.replace(DOT, SLASH);
		String choosenMethod = findMethod(xml, findClassName, method, signature);
		
		if (StringUtils.isBlank(choosenMethod)) {
			return null;
		}
		
		String xmlInstructionCounter = findInstruction(choosenMethod);
		
		if (StringUtils.isBlank(xmlInstructionCounter)) {
			return null;
		}
		
		Double missed = RegexUtil.getNumericValue(REGEX_MISSED + NumericUtil.REGEX_NUMERIC_VALUE, xmlInstructionCounter, REGEX_MISSED);
		Double covered = RegexUtil.getNumericValue(REGEX_COVERED + NumericUtil.REGEX_NUMERIC_VALUE, xmlInstructionCounter, REGEX_COVERED);
		
		if (missed == null || covered == null) {
			return null;
		}
		
		double total = missed + covered;
		return total == 0d ? 0d : (covered / total);
	}
	
	private static String findMethod(String xml, String className, String method, String signature) {
		String classRegex = "<class name=\"" + className + "\".*</class>";
		String classContent = RegexUtil.find(classRegex, xml);
		
		if (StringUtils.isBlank(classContent)) {
			return null;
		}

		List<String> methods = findMultipleMethods(classContent, method);
		
		if (methods.isEmpty()) {
			return null;
		}
		
		if (methods.size() == 1) {
			return methods.get(0);
		}
		
		return chooseMethod(methods, signature);
	}
	
	private static List<String> findMultipleMethods(String classContent, String method) {
		String methodRegex = "<method name=\"" + method + "\"";
		int methodIndex = 0;
		List<String> methods = new ArrayList<>();
		
		do {
			methodIndex = classContent.indexOf(methodRegex, methodIndex);
			
			if (methodIndex != -1) {
				int closeTagIndex = classContent.indexOf(TAG_METHOD_CLOSE, methodIndex);
				
				if (closeTagIndex != -1) {
					String methodContent = classContent.substring(methodIndex, closeTagIndex + TAG_METHOD_CLOSE.length());
					
					if (StringUtils.isNotBlank(methodContent)) {
						methods.add(methodContent);
					}
					
					methodIndex = closeTagIndex + TAG_METHOD_CLOSE.length();
				} else {
					methodIndex = -1;
				}
			}
		} while (methodIndex != -1);
		
		return methods;
	}
	
	private static String chooseMethod(List<String> methods, String signature) {
		List<String> signatureParams = SignatureHelper.getParams(signature, false);
		
		if (signatureParams == null) {
			return null;
		}
		
		for (String method : methods) {
			String descRegex = "desc=\".*\" line";
			String desc = RegexUtil.find(descRegex, method);
			desc = desc.replace("desc=", StringUtils.EMPTY).replace("\"", StringUtils.EMPTY).replace(" line", StringUtils.EMPTY);
			
			if (StringUtils.isBlank(desc)) {
				continue;
			}

			List<String> descParams = SignatureHelper.getParams(desc, true);
			
			if (compareParams(signatureParams, descParams)) {
				return method;
			}
		}

		return null;
	}
	
	private static boolean compareParams(List<String> signatureParams, List<String> descParams) {
		if (descParams == null || signatureParams.size() != descParams.size()) {
			return false;
		}
		
		if (signatureParams.isEmpty() && descParams.isEmpty()) {
			return true;
		}
		
		for (int i = 0; i < signatureParams.size(); i++) {
			if (!signatureParams.get(i).equals(descParams.get(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	private static String findInstruction(String method) {
		int indexOfInstruction =  method.indexOf(INSTRUCTION);
		
		if (indexOfInstruction == -1) {
			return null;
		}
		
		int indexOfCloseCounter = method.indexOf(END_TAG, indexOfInstruction);
		
		if (indexOfCloseCounter == -1) {
			return null;
		}
		
		return method.substring(indexOfInstruction, indexOfCloseCounter);
	}
}
