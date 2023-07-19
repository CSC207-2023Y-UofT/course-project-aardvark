package org.openjfx;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class Project {
    private String projectName;
    private List<VisualElement> elements;
    private Stack<VisualElement> redoStack;

    public Project(String name) {
        projectName = name;
        elements = new ArrayList<>();
        redoStack = new Stack<>();
    }

    public void setProjectName(String name) {
        projectName = name;
    }

    public String getProjectName() {
        return projectName;
    }

    public void addVisualElement(VisualElement v) {
        elements.add(v);
    }

    public void undoVisualElement() {
        VisualElement v = elements.remove(elements.size() - 1);
        redoStack.add(v);
    }

    /**
     * If redoStack is not empty, adds the last undone
     * visualElement back to the element list.
     * Otherwise, does nothing.
     */
    public void redoVisualElement() {
        if( redoStack.empty() ) {
            return;
        }
        VisualElement v = redoStack.pop();
        elements.add(v);
    }

    public boolean canRedo() {
        return !redoStack.empty();
    }
}
