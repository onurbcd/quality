package com.onurbcd.qa.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.onurbcd.qa.manager.JobManager;

public class AnalysisHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		JobManager.scheduleJob(event);
        return null;
	}
}
