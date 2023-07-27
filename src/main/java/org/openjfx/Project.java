package org.openjfx;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Date;

public class Project {
    private String projectName;
    private Date updatedAt;
    private List<VisualElement> elements;
    private Stack<VisualElement> redoStack;

    public Project(String name) {
        projectName = name;
        elements = new ArrayList<>();
        redoStack = new Stack<>();
        updatedAt = new Date();
    }

    public Project(String name, List<VisualElement> elements, Date updatedAt) {
        projectName = name;
        this.elements = elements;
        redoStack = new Stack<>();
        this.updatedAt = updatedAt;
    }

    public Date getDate() {
        return updatedAt;
    }

    public void setDate(Date date) {
        this.updatedAt = date;}

    public void setProjectName(String name) {
        projectName = name;
    }

    public String getName() {
        return projectName;
    }

    public void addVisualElement(VisualElement v) {
        elements.add(v);
        redoStack.clear();
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
        if (redoStack.empty()) {
            return;
        }
        VisualElement v = redoStack.pop();
        elements.add(v);
    }

    public boolean canRedo() {
        return !redoStack.empty();
    }
}

