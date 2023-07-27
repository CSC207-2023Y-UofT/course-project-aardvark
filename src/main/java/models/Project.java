package models;

import javafx.scene.control.Alert;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Date;

public class Project {
    private String projectName;
    private Date createdAt;
    private List<VisualElement> elements;
    private Stack<VisualElement> redoStack;

    public Project(String name) {
        projectName = name;
        elements = new ArrayList<>();
        redoStack = new Stack<>();
        createdAt = new Date();
    }

    public Date getDate() {
        return createdAt;
    }

    public void setDate(Date date) {
        this.createdAt = date;}

    public void setProjectName(String name) {
        projectName = name;
    }

    public String getName() {
        return projectName;
    }

    public void addVisualElement(VisualElement v) {
        elements.add(v);
    }

    public void undoVisualElement() {
        if (elements.isEmpty()) {
            return;
        }
        VisualElement v = elements.remove(elements.size() - 1);
        redoStack.add(v);
    }

    /**
     * If redoStack is not empty, adds the last undone
     * visualElement back to the element list.
     * Otherwise, does nothing.
     */
    public void redoVisualElement() {
        if (canRedo()) {
            VisualElement v = redoStack.pop();
            elements.add(v);
        }
    }

    public boolean canRedo() {
        return !redoStack.empty();
    }

    public void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

