package controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Project;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class CanvasExportController {
    private Canvas canvas;
    private Project project;

    public CanvasExportController(Canvas canvas, Project project) {
        this.canvas = canvas;
        this.project = project;
    }
    public CanvasExportController(Canvas canvas) {
        this(canvas, new Project("untitled project"));
    }

    public CanvasExportController(Project project) {
        this(new Canvas(800, 600), project);
    }
    public CanvasExportController() {
        this(new Canvas(800, 600), new Project("untitled project"));
    }

    public void export(Stage primaryStage, Project projectItem) {
        // Show a file chooser to get the save location and name for the PNG image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export as PNG");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Image (*.png)", "*.png"));
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            try {
                // Convert the canvas to a writable image
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(null, writableImage);

                // Write the writable image to the specified file as a PNG image
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
            } catch (IOException ex) {
                // Handle the exception if there was an error saving the image
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error exporting as PNG. Please try again.");
                alert.showAndWait();
            }
        }
    }
}
