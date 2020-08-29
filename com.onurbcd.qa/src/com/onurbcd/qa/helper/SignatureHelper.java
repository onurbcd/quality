package com.onurbcd.qa.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.core.Signature;

public class SignatureHelper {
	
	private static final String LIST = "List";
	
	private static final String SET = "Set";
	
	private static final String COLLECTION = "Collection";
	
	private static final String MAP = "Map";

	private SignatureHelper() {
	}
	
	public static List<String> getParams(String signature, boolean isDesc) {
		if (StringUtils.isBlank(signature)) {
			return null;
		}

		String[] paramTypes = Signature.getParameterTypes(signature);
		
		if (paramTypes == null || paramTypes.length <= 0) {
			return Collections.emptyList();
		}
		
		List<String> params = new ArrayList<>();

		for (String s : paramTypes) {
		    String paramName = Signature.getSignatureSimpleName(s);
		    
		    if (StringUtils.isBlank(paramName)) {
		    	continue;
		    }
		    
		    if (isDesc) {
		    	paramName = getDescParam(paramName);
		    }
		    
		    if (StringUtils.isNotBlank(paramName)) {
		    	if (paramName.startsWith(LIST)) {
		    		params.add(LIST);
		    	} else if (paramName.startsWith(SET)) {
		    		params.add(SET);
		    	} else if (paramName.startsWith(COLLECTION)) {
		    		params.add(COLLECTION);
		    	} else if (paramName.startsWith(MAP)) {
		    		params.add(MAP);
		    	} else {
		    		params.add(paramName);
		    	}
		    }
		}
		
 		return params;
	}
	
	private static String getDescParam(String paramName) {
		int lastIndex = paramName.lastIndexOf('/');
		
		if (lastIndex == -1 || (lastIndex + 1) == paramName.length()) {
			return paramName;
		}
		
		return paramName.substring(lastIndex + 1);
	}
}
