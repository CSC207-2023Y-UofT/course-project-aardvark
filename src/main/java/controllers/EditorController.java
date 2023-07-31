package controllers;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Project;
import org.openjfx.FXMLController;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class EditorController {
    public Button renameBtn;
    public Button resizeBtn;
    public Button clearBtn;
    public Button exportBtn;
    public RadioButton freeDrawBtn;
    public ToggleGroup selectTool;
    public RadioButton textBoxBtn;
    public Button exitBtn;
    private Project project = new Project("untitled project");
    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colorPicker;
    private Color currentColor = Color.BLACK;
    @FXML
    private TextField brushSize;
    public GraphicsContext gc;
    public static Stage primaryStage;
    public void initialize() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(3.0);

        canvas.setOnMousePressed(e -> {
            double x = e.getX();
            double y = e.getY();
            gc.beginPath();
            gc.moveTo(x, y);
            gc.setStroke(currentColor);
            gc.stroke();
        });
        canvas.setOnMouseDragged(e -> {
            double x = e.getX();
            double y = e.getY();
            gc.lineTo(x, y);
            gc.stroke();
        });
    }

    private void setCurrentColor(Color color) {
        currentColor = color;
        gc.setStroke(color);
        gc.setFill(color);
    }

    public void onSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export as PNG");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Image (*.png)", "*.png"));
        File file = fileChooser.showSaveDialog(primaryStage);
        try {
            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            Image snapshot = canvas.snapshot(null, writableImage);

            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error exporting as PNG. Please try again.");
            alert.showAndWait();
        }
    }
    public void onExit() {
        Platform.exit();
    }
    @FXML
    public void switchToProjects(javafx.event.ActionEvent event) throws IOException {
        FXMLController switcher = new FXMLController();
        switcher.switchToProjects(event);
    }

    public void resizeCanvas(ActionEvent actionEvent) {
    }

    public void clearCanvas(ActionEvent actionEvent) {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
