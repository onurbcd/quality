package com.onurbcd.qa.preferences;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class QaListEditor extends ListEditor {
	
	private String dialogTitle;
	
	private String dialogMessage;
	
	protected QaListEditor() {
	}

	public QaListEditor(String name, String labelText, Composite parent, String dialogTitle, String dialogMessage) {
        init(name, labelText);
        createControl(parent);
        this.dialogTitle = dialogTitle;
        this.dialogMessage = dialogMessage;
    }

	@Override
	protected String createList(String[] items) {
		return Joiner.on(PreferenceConstants.SEPARATOR).join(items);
	}

	@Override
	protected String getNewInputObject() {
		final InputDialog inputDialog = new InputDialog(getShell(), dialogTitle, dialogMessage, StringUtils.EMPTY, null);
		
		if (inputDialog.open() != Window.OK) {
            return null;
        }
		
		final String value = inputDialog.getValue();

        if (StringUtils.isBlank(value)) {
            return null;
        }

        return StringUtils.trim(value);
	}

	@Override
	protected String[] parseString(String stringList) {
		List<String> list = Splitter.on(PreferenceConstants.SEPARATOR).splitToList(stringList);
		return list.toArray(new String[list.size()]);
	}
}
