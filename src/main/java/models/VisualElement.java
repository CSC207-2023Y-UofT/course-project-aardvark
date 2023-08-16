package models;

import javafx.scene.canvas.GraphicsContext;

import java.util.HashMap;

public interface VisualElement {

    /**
     Draw the graphical element on the specified GraphicsContext.
     @param gc The GraphicsContext on which the element should be drawn.
     */
    void draw(GraphicsContext gc);

    /**
     * This function should return a dictionary containing
     * the name of the class as its only key, and as the value
     * an Array of the parameters needed to initialize.
     */
    HashMap<String, Object> toDict();
}
