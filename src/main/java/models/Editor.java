package models;

import controllers.CanvasExportController;
import controllers.CanvasResizerController;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Editor extends Application {
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Color currentColor = Color.BLACK;

    @Override
    public void start(Stage primaryStage) {
        // Create canvas and graphics context
        canvas = new Canvas(800, 600);
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(2.0);

        // Set up a new Project
        Project projectItem = new Project("Hello World");


        // Create a resize controller and set the name of the project to the
        CanvasResizerController canvasResizer = new CanvasResizerController(canvas);
        primaryStage.setTitle(projectItem.getName());

        // Create a export controller
        CanvasExportController canvasExporter = new CanvasExportController(canvas, projectItem);


        // Color picker to allow selecting custom colors
        ColorPicker colorPicker = new ColorPicker(currentColor);
        colorPicker.setOnAction(e -> setCurrentColor(colorPicker.getValue()));

        // Clear button to reset the canvas
        Button clearBtn = new Button("Clear Canvas");
        clearBtn.setOnAction(e -> clearCanvas());

        // BorderPane to arrange the canvas and control buttons
        BorderPane root = new BorderPane();

        // Button to resize the canvas
        Button resizeBtn = new Button("Resize Canvas");
        resizeBtn.setOnAction(e -> canvasResizer.resize());
        root.setCenter(canvas);

        Button exportBtn = new Button("Export as PNG");
        exportBtn.setOnAction(e -> canvasExporter.export(primaryStage, projectItem));

        // HBox to hold the color picker, color buttons, and resize button
        HBox controlBox = new HBox(10);
        controlBox.getChildren().addAll(colorPicker, clearBtn, resizeBtn, exportBtn);
        root.setCenter(canvas);
        root.setTop(controlBox);

        // Set up the scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Event handler for drawing on the canvas
        canvas.setOnMousePressed(e -> {
            graphicsContext.beginPath();
            graphicsContext.moveTo(e.getX(), e.getY());
            graphicsContext.setStroke(currentColor);
            graphicsContext.stroke();
        });

        canvas.setOnMouseDragged(e -> {
            graphicsContext.lineTo(e.getX(), e.getY());
            graphicsContext.stroke();
        });
    }

    // Helper method to create color buttons
    private Button createColorButton(Color color) {
        Button button = new Button();
        button.setPrefSize(30, 30);
        button.setStyle("-fx-background-color: #" + color.toString().substring(2) + ";");
        button.setOnAction(e -> setCurrentColor(color));
        return button;
    }

    // Helper method to set the current drawing color
    private void setCurrentColor(Color color) {
        currentColor = color;
    }

    // Helper method to clear the canvas
    private void clearCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
    public static void main(String[] args) {
        launch(args);
    }
}
