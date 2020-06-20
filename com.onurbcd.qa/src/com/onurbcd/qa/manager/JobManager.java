package com.onurbcd.qa.manager;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.onurbcd.qa.helper.JunitHelper;

public class JobManager {

	private JobManager() {
	}

	private static final AnalysisManager ANALYSIS_MANAGER = new AnalysisManager();

	public static void scheduleJob(ExecutionEvent event) {
		Job job = Job.create("Analyse method", monitor -> {
			JunitHelper.processCoverageReport();
			run();
			syncWithUi(event);
		});

		job.schedule();
	}

	private static void run() {
		ANALYSIS_MANAGER.run();
	}

	private static void syncWithUi(ExecutionEvent event) {
		Display.getDefault().asyncExec(() -> {
			try {
				IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
				String message = ANALYSIS_MANAGER.getMessage();
				MessageDialog.openInformation(window.getShell(), "Quality", message);
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		});
	}
}
