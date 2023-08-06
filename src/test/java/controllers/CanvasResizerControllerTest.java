package controllers;

import javafx.scene.canvas.Canvas;
import models.Project;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CanvasResizerControllerTest {

    @Test
    void resizeCanvasTest() {
        Canvas canvas = new Canvas(500, 600);
        Project project = new Project("Hello World");
        CanvasResizerController canvasResizerController = new CanvasResizerController(canvas, project);
        canvasResizerController.resizeCanvas(1500, 1000);
        Assertions.assertEquals(1000, canvas.getHeight());
        Assertions.assertEquals(1500, canvas.getWidth());
    }
}