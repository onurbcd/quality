package com.onurbcd.qa.preferences;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class QaListEditor extends ListEditor {
	
	private static final String DIALOG_TITLE = "Not QA Type";
	
	private static final String DIALOG_MESSAGE = "Enter a package name or class type not be be included in the analysis";
	
	protected QaListEditor() {
	}

	public QaListEditor(String name, String labelText, Composite parent) {
        init(name, labelText);
        createControl(parent);
    }

	@Override
	protected String createList(String[] items) {
		return Joiner.on(PreferenceConstants.SEPARATOR).join(items);
	}

	@Override
	protected String getNewInputObject() {
		final InputDialog inputDialog = new InputDialog(getShell(), DIALOG_TITLE, DIALOG_MESSAGE, StringUtils.EMPTY, null);
		
		if (inputDialog.open() != Window.OK) {
            return null;
        }
		
		final String notQaType = inputDialog.getValue();

        if (StringUtils.isBlank(notQaType)) {
            return null;
        }

        return StringUtils.trim(notQaType);
	}

	@Override
	protected String[] parseString(String stringList) {
		List<String> list = Splitter.on(PreferenceConstants.SEPARATOR).splitToList(stringList);
		return list.toArray(new String[list.size()]);
	}
}
