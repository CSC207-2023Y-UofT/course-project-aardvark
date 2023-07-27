package controllers;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Pair;
import models.Project;

import java.util.Optional;

public class CanvasResizerController {
    private Canvas canvas;
    private Project project;

    public CanvasResizerController() {
        this(new Canvas(), new Project("untitled project"));
    }

    public CanvasResizerController(Canvas canvas) {
        this(canvas, new Project("untitled project"));
    }
    public CanvasResizerController(Canvas canvas, Project project) {
        this.canvas = canvas;
        this.project = project;
    }

    public void resizeCanvas(double newWidth, double newHeight) {
        canvas.setWidth(newWidth);
        canvas.setHeight(newHeight);
    }

    public void resize() {
        // Create a custom dialog to get the new dimensions from the user
        Dialog<Pair<Double, Double>> dialog = new Dialog<>();
        dialog.setTitle("Resize Canvas");

        // Set the dialog buttons (OK and Cancel)
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        // Create labels and text fields for width and height inputs
        Label widthLabel = new Label("Width:");
        Label heightLabel = new Label("Height:");

        TextField widthField = new TextField();
        TextField heightField = new TextField();

        // Add the labels and text fields to the dialog
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setMaxWidth(Double.MAX_VALUE);
        grid.add(widthLabel, 1, 1);
        grid.add(widthField, 2, 1);
        grid.add(heightLabel, 1, 2);
        grid.add(heightField, 2, 2);

        // Allow the width and height text fields to grow horizontally
        GridPane.setHgrow(widthField, Priority.ALWAYS);
        GridPane.setHgrow(heightField, Priority.ALWAYS);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a pair of width and height when the OK button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                try {
                    double newWidth = Double.parseDouble(widthField.getText());
                    double newHeight = Double.parseDouble(heightField.getText());
                    return new Pair<>(newWidth, newHeight);
                } catch (NumberFormatException ex) {
                    // Handle invalid input (non-numeric values)
                    project.showErrorDialog("Invalid input. Please enter numeric values for width and height.");
                }
            }
            return null;
        });

        // Show the dialog and get the result
        Optional<Pair<Double, Double>> result = dialog.showAndWait();

        // If the user clicked "OK" and provided valid input, resize the canvas
        result.ifPresent(dimensions -> {
            this.resizeCanvas(dimensions.getKey(), dimensions.getValue());
        });
    }
}
