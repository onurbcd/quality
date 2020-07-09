package com.onurbcd.qa.helper;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

public class ProjectHelper {

    private ProjectHelper() {
    }

    public static IProject getProject(String projectName) {
        if (StringUtils.isBlank(projectName)) {
            return null;
        }
        
        IWorkspace workspace = ResourcesPlugin.getWorkspace();

        if (workspace == null) {
            return null;
        }

        IWorkspaceRoot root = workspace.getRoot();

        if (root == null) {
            return null;
        }
        
        return root.getProject(projectName);
    }
}
