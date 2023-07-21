package controllers;

import javafx.scene.canvas.Canvas;

public class CanvasResizerController {
    private Canvas canvas;

    public CanvasResizerController(Canvas canvas) {
        this.canvas = canvas;
    }

    public void resizeCanvas(double newWidth, double newHeight) {
        canvas.setWidth(newWidth);
        canvas.setHeight(newHeight);
    }
}
