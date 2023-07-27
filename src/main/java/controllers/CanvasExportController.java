package controllers;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Pair;
import models.Project;

public class CanvasExportController {
    private Canvas canvas;
    private Project project;

    public CanvasExportController(Canvas canvas, Project project) {
        this.canvas = canvas;
        this.project = project;
    }
}
