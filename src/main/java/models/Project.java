package models;

import javafx.scene.control.Alert;

import java.util.*;

public class Project {
    private String projectName;
    private Date updatedAt;
    private int height;
    public static int DEFAULT_HEIGHT = 600;
    private int width;
    public static int DEFAULT_WIDTH = 800;
    private List<VisualElement> elements;
    private Stack<VisualElement> redoStack;

    public Project(String name) {
        projectName = name;
        elements = new ArrayList<>();
        redoStack = new Stack<>();
        updatedAt = new Date();
        height = DEFAULT_HEIGHT;
        width = DEFAULT_WIDTH;
    }

    public Project(String name, List<VisualElement> elements, Date updatedAt, int x, int y) {
        projectName = name;
        this.elements = elements;
        redoStack = new Stack<>();
        this.updatedAt = updatedAt;
        height = y;
        width = x;
    }

    public Date getDate() {
        return this.updatedAt;
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

    /**
     *
     * @return Hashmap containing
     */
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> dictionary = new HashMap<>();

        dictionary.put("ProjectName", projectName);
        dictionary.put("UpdateDate", updatedAt);
        dictionary.put("Width", width);
        dictionary.put("Height", height);

        List<HashMap<String, Object>> elementList = new ArrayList<>();
        for(int i = 0; i < elements.size(); i++) {
            elementList.add(elements.get(i).toDict());
        }
        dictionary.put("VisualElements", elementList);

        return dictionary;
    }
}

