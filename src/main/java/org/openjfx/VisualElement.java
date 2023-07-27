package org.openjfx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashMap;
import java.util.Objects;

public interface VisualElement {

    public void draw(GraphicsContext gc);

    /**
     * This function should return a dictionary containing
     * the name of the class as its only key, and as the value
     * an Array of the parameters needed to initalize.
     */
    public HashMap<String, Object> toDict();
}
