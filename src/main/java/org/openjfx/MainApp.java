package org.openjfx;

import controllers.CanvasResizerController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Pair;
import javafx.scene.control.Dialog;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;

public class MainApp extends Application {
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Color currentColor = Color.BLACK;
//    @Override
//    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("signin.fxml"));
//
//        Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
//
//        Image icon = new Image("images/icon.png");
//        stage.getIcons().add(icon);
//        stage.setResizable(false);
////        stage.setMinWidth(800);
////        stage.setMinHeight(800);
//
//        stage.setTitle("Aardvark");
//        stage.setScene(scene);
//        stage.show();
//    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Paint Drawing Application");

        // Create canvas and graphics context
        canvas = new Canvas(800, 600);
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(2.0);

        // Color picker to allow selecting custom colors
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
        colorPicker.setOnAction(e -> setCurrentColor(colorPicker.getValue()));

        // Create a canvas resizer object
        CanvasResizerController canvasResizer = new CanvasResizerController(canvas);

        // Clear button to reset the canvas
        Button clearBtn = new Button("Clear Canvas");
        clearBtn.setOnAction(e -> clearCanvas());

        // BorderPane to arrange the canvas and control buttons
        BorderPane root = new BorderPane();

        // Button to resize the canvas
        Button resizeBtn = new Button("Resize Canvas");
        resizeBtn.setOnAction(e -> {
            // Create a custom dialog to get the new dimensions from the user
            Dialog<Pair<Double, Double>> dialog = new Dialog<>();
            dialog.setTitle("Resize Canvas");

            // Set the dialog buttons (OK and Cancel)
            ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
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
                        showErrorDialog("Invalid input. Please enter numeric values for width and height.");
                    }
                }
                return null;
            });

            // Show the dialog and get the result
            Optional<Pair<Double, Double>> result = dialog.showAndWait();

            // If the user clicked "OK" and provided valid input, resize the canvas
            result.ifPresent(dimensions -> {
                canvasResizer.resizeCanvas(dimensions.getKey(), dimensions.getValue());

                // Reattach the resized canvas to the BorderPane's center
                root.setCenter(canvas);
            });
        });

        Button exportBtn = new Button("Export as PNG");
        exportBtn.setOnAction(e -> {
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
                    showErrorDialog("Error exporting as PNG. Please try again.");
                }
            }
        });

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

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
