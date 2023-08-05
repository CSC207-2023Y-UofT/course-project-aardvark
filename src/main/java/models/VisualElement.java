package models;

import javafx.scene.canvas.GraphicsContext;

import java.util.HashMap;

public interface VisualElement {

    void draw(GraphicsContext gc);

    /**
     * This function should return a dictionary containing
     * the name of the class as its only key, and as the value
     * an Array of the parameters needed to initalize.
     */
    HashMap<String, Object> toDict();
}
