package com.onurbcd.qa.helper;

import static org.eclipse.core.runtime.Path.SEPARATOR;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;

import com.onurbcd.qa.metrics.LOCCalculator;
import com.onurbcd.qa.metrics.LOCResult;
import com.onurbcd.qa.metrics.MCCCalculator;
import com.onurbcd.qa.preferences.QaPreference;
import com.onurbcd.qa.util.DateTimeUtil;
import com.onurbcd.qa.util.FileUtil;
import com.onurbcd.qa.util.NumericUtil;
import com.onurbcd.qa.util.QaMethod;
import com.onurbcd.qa.util.ReportMethod;

public class ReportHelper {
	
	private static final String REPORT_FILE_NAME = "qa-analysis-report-";
	
	private static final String REPORT_FILE_EXTENSION = ".txt";

	private ReportHelper() {
	}
	
	public static void processReport(QaMethod qaMethod, QaPreference prefs) {
		if (qaMethod == null || prefs == null || StringUtils.isBlank(prefs.getReportFilePath())) {
			return;
		}

		Map<String, List<ReportMethod>> reports = new LinkedHashMap<>();
		String xml = FileUtil.getJunitCoverageReport(prefs.getJunitCoverageReport());
		processQaMethod(reports, qaMethod, xml, prefs.getMinPercentRate());
		generateReport(reports, prefs);
	}

	private static void processQaMethod(Map<String, List<ReportMethod>> reports, QaMethod qaMethod, String xml, double minPercentRate) {
		addReportMethod(reports, qaMethod, xml, minPercentRate);
		
		if (qaMethod.getCallees() == null || qaMethod.getCallees().isEmpty()) {
			return;
		}
		
		for (QaMethod calleeQaMethod : qaMethod.getCallees()) {
			processQaMethod(reports, calleeQaMethod, xml, minPercentRate);
		}
	}
	
	private static void addReportMethod(Map<String, List<ReportMethod>> reports, QaMethod qaMethod, String xml, double minPercentRate) {
		String className = qaMethod.getMethod().getDeclaringType().getFullyQualifiedName();
		String signature = MethodHelper.getMethodFullName(qaMethod.getMethod());
		
		if (reports.containsKey(className)) {
			if (reports.get(className).stream().noneMatch(rm -> rm.getSignature().equals(signature))) {
				addToReport(xml, className, qaMethod, minPercentRate, signature, reports, true);
			}
		} else {
			addToReport(xml, className, qaMethod, minPercentRate, signature, reports, false);
		}
	}
	
	private static void addToReport(String xml, String className, QaMethod qaMethod, double minPercentRate, String signature, Map<String, List<ReportMethod>> reports, boolean exists) {
		double coverage = JunitHelper.processCoverageReport(xml, className, qaMethod.getMethod().getElementName());
		String source = getSourceSafe(qaMethod.getMethod());
		ReportMethod reportMethod = new ReportMethod(className, signature);
		
		if (coverage < minPercentRate) {
			LOCResult locResult = LOCCalculator.calculate(source);
			int mcc = MCCCalculator.calculate(source, locResult.getSourceCode());
			reportMethod.setMetrics(locResult.getCount(), mcc, coverage);
			
			if (exists) {
				reports.get(className).add(reportMethod);
			} else {
				List<ReportMethod> methods = new ArrayList<>();
				methods.add(reportMethod);
				reports.put(className, methods);
			}
		}
	}
	
	private static String getSourceSafe(IMethod method) {
		try {
			return method.getSource();
		} catch (JavaModelException e) {
			return StringUtils.EMPTY;
		}
	}
	
	private static void generateReport(Map<String, List<ReportMethod>> reports, QaPreference prefs) {
		StringBuilder reportContent = new StringBuilder();
		int numberOfMethods = 0;
		double totalHours = 0;
		
		for (Map.Entry<String, List<ReportMethod>> entry : reports.entrySet()) {
	        reportContent.append(entry.getKey()).append("\n");
	        
	        for (ReportMethod reportMethod : entry.getValue()) {
	        	double hours = reportMethod.getHours(prefs.getMccRate(), prefs.getLocRate());
	        	totalHours += hours;
	        	reportContent.append("    ").append(reportMethod.getSignature()).append("\n")
	        	.append("        LOC: ").append(reportMethod.getLoc()).append("\n")
	        	.append("        MCC: ").append(reportMethod.getMcc()).append("\n")
	        	.append("        COVERAGE: ").append(reportMethod.getCoverage()).append("\n")
	        	.append("        HORAS: ").append(hours).append("\n");
	        	numberOfMethods++;
			}
	        
	        reportContent.append("\n");
	    }
		
		if (reportContent.length() <= 0) {
			return;
		}
		
		String header = "TOTAL HOURS: " + NumericUtil.round(totalHours, 2) +  "\nNUMBER OF METHODS: " + numberOfMethods + "\n\n";
		String fullFileName = prefs.getReportFilePath() + SEPARATOR + REPORT_FILE_NAME + DateTimeUtil.getNowFormatted() + REPORT_FILE_EXTENSION;
		FileUtil.writeStringToFile(fullFileName, header + reportContent.toString());
	}
}
