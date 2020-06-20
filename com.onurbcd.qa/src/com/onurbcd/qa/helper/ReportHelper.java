package com.onurbcd.qa.helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;

import com.onurbcd.qa.metrics.LOCCalculator;
import com.onurbcd.qa.metrics.LOCResult;
import com.onurbcd.qa.metrics.MCCCalculator;
import com.onurbcd.qa.util.DateTimeUtil;
import com.onurbcd.qa.util.FileUtil;
import com.onurbcd.qa.util.QaMethod;
import com.onurbcd.qa.util.ReportMethod;

public class ReportHelper {
	
	private static final String REPORT_FILE_PATH = "C:\\Users\\bmiguellei\\Desktop\\";
	
	private static final String REPORT_FILE_NAME = "qa-analysis-report-";
	
	private static final String REPORT_FILE_EXTENSION = ".txt";

	private ReportHelper() {
	}
	
	public static void processReport(QaMethod qaMethod) {
		if (qaMethod == null) {
			return;
		}
		
		Map<String, List<ReportMethod>> reports = new LinkedHashMap<>();
		processQaMethod(reports, qaMethod);
		generateReport(reports);
	}
	
	private static void processQaMethod(Map<String, List<ReportMethod>> reports, QaMethod qaMethod) {
		addReportMethod(reports, qaMethod);
		
		if (qaMethod.getCallees() == null || qaMethod.getCallees().isEmpty()) {
			return;
		}
		
		for (QaMethod calleeQaMethod : qaMethod.getCallees()) {
			processQaMethod(reports, calleeQaMethod);
		}
	}
	
	private static void addReportMethod(Map<String, List<ReportMethod>> reports, QaMethod qaMethod) {
		String className = qaMethod.getMethod().getDeclaringType().getFullyQualifiedName();
		String signature = MethodHelper.getMethodFullName(qaMethod.getMethod());
		String source = getSourceSafe(qaMethod.getMethod());
		ReportMethod reportMethod = new ReportMethod(className, signature);
		
		if (reports.containsKey(className)) {
			if (reports.get(className).stream().noneMatch(rm -> rm.getSignature().equals(signature))) {
				LOCResult locResult = LOCCalculator.calculate(source);
				int mcc = MCCCalculator.calculate(source, locResult.getSourceCode());
				reportMethod.setMetrics(locResult.getCount(), mcc);
				reports.get(className).add(reportMethod);
			}
		} else {
			LOCResult locResult = LOCCalculator.calculate(source);
			int mcc = MCCCalculator.calculate(source, locResult.getSourceCode());
			reportMethod.setMetrics(locResult.getCount(), mcc);
			List<ReportMethod> methods = new ArrayList<>();
			methods.add(reportMethod);
			reports.put(className, methods);
		}
	}
	
	private static String getSourceSafe(IMethod method) {
		try {
			return method.getSource();
		} catch (JavaModelException e) {
			return "";
		}
	}
	
	private static void generateReport(Map<String, List<ReportMethod>> reports) {
		StringBuilder reportContent = new StringBuilder();
		int numberOfMethods = 0;
		
		for (Map.Entry<String, List<ReportMethod>> entry : reports.entrySet()) {
	        reportContent.append(entry.getKey()).append("\n");
	        
	        for (ReportMethod reportMethod : entry.getValue()) {
	        	reportContent.append("    ").append(reportMethod.getSignature()).append("\n")
	        	.append("        LOC: ").append(reportMethod.getLoc()).append("\n")
	        	.append("        MCC: ").append(reportMethod.getMcc()).append("\n");
	        	numberOfMethods++;
			}
	        
	        reportContent.append("\n");
	    }
		
		if (reportContent.length() <= 0) {
			return;
		}
		
		String header = "NUMBER OF METHODS: " + numberOfMethods + "\n\n";
		String fullFileName = REPORT_FILE_PATH + REPORT_FILE_NAME + DateTimeUtil.getNowFormatted() + REPORT_FILE_EXTENSION;
		FileUtil.writeStringToFile(fullFileName, header + reportContent.toString());
	}
}
