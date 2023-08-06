package controllers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.Project;

import java.util.Optional;

/**
 The CanvasResizerController class is a controller class responsible for resizing the Canvas.

 It allows the user to input new dimensions for the canvas and then resizes it accordingly.
 */
public class CanvasResizerController {
    private Canvas canvas;
    private Project project;

    /**
     Constructs a new CanvasResizerController object with the specified Canvas and Project.

     @param canvas The Canvas to be resized.
     @param project The Project associated with the Canvas.
     */
    public CanvasResizerController(Canvas canvas, Project p) {
        this.canvas = canvas;
        this.project = p;
    }

    /**
     Resizes the Canvas to the specified width and height.
     @param newWidth The new width of the Canvas.
     @param newHeight The new height of the Canvas.
     */
    public void resizeCanvas(double newWidth, double newHeight) {
        canvas.setWidth(newWidth);
        canvas.setHeight(newHeight);
        project.setWidth((int)newWidth);
        project.setHeight((int)newHeight);
    }

    /**
     Displays a dialog to get new dimensions from the user and resizes the Canvas accordingly.

     The existing content on the Canvas is preserved during the resizing process.
     */
    public void resize() {
        // Take a snapshot of the current content on the Canvas
        WritableImage snapshot = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, snapshot);

        // Create a custom dialog to get the new dimensions from the user
        Dialog<Pair<Double, Double>> dialog = new Dialog<>();
        dialog.setTitle("Resize Canvas");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/images/icon.png").toString()));

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
                    project.setWidth((int)newWidth);
                    project.setHeight((int)newHeight);
                    return new Pair<>(newWidth, newHeight);
                } catch (NumberFormatException ex) {
                    // Handle invalid input (non-numeric values)
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid input. Please enter numeric values for width and height.");
                    alert.showAndWait();
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
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Fill the canvas with white
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw the preserved snapshot back onto the resized canvas
        gc.drawImage(snapshot, 0, 0);
    }
}
