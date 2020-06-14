package com.onurbcd.qa.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.onurbcd.qa.manager.AnalysisManager;

import org.eclipse.jface.dialogs.MessageDialog;

public class AnalysisHandler extends AbstractHandler {

	private AnalysisManager analysisManager = new AnalysisManager();

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		analysisManager.run();
		String message = analysisManager.getMessage();
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
        MessageDialog.openInformation(window.getShell(), "Quality", message);
        return null;
	}
}
