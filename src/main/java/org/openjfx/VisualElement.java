package org.openjfx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public interface VisualElement {

    public void draw(GraphicsContext gc);

    /**
     * This function should return a dictionary containing
     * the name of the class as its only key, and as the value
     * an array of the parameters needed to initalize.
     */
    public void toDict();
}
