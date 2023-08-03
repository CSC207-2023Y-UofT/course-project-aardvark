package controllers;

import free_draw.FreeDrawLine;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.AardCircle;
import models.AardSquare;
import models.Project;
import models.VisualElement;
import org.openjfx.FXMLController;
import text.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class EditorController {

    public final Project project;
    public Label projectName;
    public Button resizeBtn;
    public Button clearBtn;
    public Button exportBtn;
    public Button undoBtn;
    public ToggleGroup selectTool;
    public RadioButton freeDrawBtn;
    @FXML
    public RadioButton radioButtonCircle;
    @FXML
    public RadioButton radioButtonSquare;
    public RadioButton textBoxBtn;
    public RadioButton eraserBtn;
    @FXML
    public ComboBox<String> fontComboBox;
    @FXML
    public TextField fontSize;
    @FXML
    public Button textIncreaseBtn;
    @FXML
    public Button textDecreaseBtn;
    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colorPickerDraw;
    @FXML
    public ColorPicker colorPickerText;
    @FXML
    public ColorPicker colourPickerShapeFill;
    @FXML
    public ColorPicker colourPickerShapeStroke;
    @FXML
    public CheckBox checkBoxShapeFill;
    @FXML
    public CheckBox checkBoxShapeStroke;
    @FXML
    public TextField textFieldShapeStroke;
    private Color currentColorDraw = Color.BLACK;
    private Color currentColorText = Color.BLACK;
    @FXML
    private TextField brushSize;
    public TextField textField;
    public GraphicsContext gc;
    public static Stage primaryStage;
    private CanvasResizerController resizerController;

    Font defaultFont = Font.font("Verdana", 16);
    String [] defaultInput = new String[]{""};
//    private ArrayList<VisualElement>
    IntegerProperty sizeLabelProperty = new SimpleIntegerProperty(16);

    public EditorController(Project p) {
        project = p;
    }

    public void initialize() {
        /* INITIALIZE */

        gc = canvas.getGraphicsContext2D();
        resizerController = new CanvasResizerController(canvas, project);

        projectName.setText(project.getName());

        // default button
        freeDrawBtn.setSelected(true);

        /* CLEAR */

        clearBtn.setOnMousePressed(e -> {
            project.addVisualElement(new AardSquare(
                canvas.getWidth() / 2, canvas.getHeight() / 2, Math.max(canvas.getWidth(), canvas.getHeight()),
                true, true, Color.WHITE, Color.WHITE, 0));
        });

        /* TEXT */

        gc.setFont(defaultFont);
        textField.setOnKeyReleased(e -> defaultInput[0] = textField.getText());

        fontComboBox.getItems().addAll(
                "Arial",
                "Arial Narrow",
                "Calibri",
                "Cambria",
                "Courier New",
                "Algerian",
                "Comic Sans MS",
                "Cooper Black",
                "Century Gothic",
                "Verdana",
                "Times New Roman"
        );

        fontComboBox.setValue("Verdana");

        textIncreaseBtn.setOnAction(event -> {
            fontSize.setText(Integer.parseInt(fontSize.getText()) + 1 + "");
        });

        textDecreaseBtn.setOnAction(event -> {
            fontSize.setText(Integer.parseInt(fontSize.getText()) - 1 + "");
        });

        /* WHITE CANVAS BACKGROUND */

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        /* SETTINGS */

        canvas.setOnMousePressed(e -> {
            if (freeDrawBtn.isSelected()) {
                double size = Double.parseDouble(brushSize.getText());

                FreeDrawLine newLine = new FreeDrawLine(colorPickerDraw.getValue(), size);
                newLine.addPoint(e.getX(), e.getY());

                project.addVisualElement(newLine);
            }
            else if (radioButtonCircle.isSelected()) {
                project.addVisualElement(new AardCircle(
                        e.getX() - 1, e.getY() - 1, 2,
                        checkBoxShapeFill.isSelected(),
                        checkBoxShapeStroke.isSelected(),
                        colourPickerShapeFill.getValue(),
                        colourPickerShapeStroke.getValue(),
                        Integer.parseInt(textFieldShapeStroke.getText())));
            }
            else if (radioButtonSquare.isSelected()) {
                project.addVisualElement(new AardSquare(
                        e.getX() - 1, e.getY() - 1, 2,
                        checkBoxShapeFill.isSelected(),
                        checkBoxShapeStroke.isSelected(),
                        colourPickerShapeFill.getValue(),
                        colourPickerShapeStroke.getValue(),
                        Integer.parseInt(textFieldShapeStroke.getText())));
            }
            else if (textBoxBtn.isSelected()) {
                project.addVisualElement(new AardText(
                        textField.getText(),
                        colorPickerText.getValue(),
                        new Font(fontComboBox.getValue(), Double.parseDouble(fontSize.getText())),
                        e.getX(), e.getY()));
            }
            else if (eraserBtn.isSelected()) {
                double size = Double.parseDouble(brushSize.getText());

                FreeDrawLine eraser = new FreeDrawLine(Color.WHITE, size);
                eraser.addPoint(e.getX(), e.getY());

                project.addVisualElement(eraser);
            }

            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            project.draw(gc);
        });

        canvas.setOnMouseDragged(e -> {
            if (freeDrawBtn.isSelected()) {
                FreeDrawLine line = project.getCurrentLine();
                line.addPoint(e.getX(), e.getY());
            }
            else if (radioButtonCircle.isSelected()) {
                AardCircle last = project.getLastAndRemoveCircle();

                double r = Math.sqrt(Math.pow(last.x - e.getX(), 2) + Math.pow(last.y - e.getY(), 2));
                project.addVisualElement(new AardCircle(
                        last.x - (r-last.r)/2, last.y-(r-last.r)/2, r,
                        checkBoxShapeFill.isSelected(),
                        checkBoxShapeStroke.isSelected(),
                        colourPickerShapeFill.getValue(),
                        colourPickerShapeStroke.getValue(),
                        Integer.parseInt(textFieldShapeStroke.getText())));
            }
            else if (radioButtonSquare.isSelected()) {
                AardSquare last = project.getLastAndRemoveSquare();

                double r = Math.sqrt(Math.pow(last.x - e.getX(), 2) + Math.pow(last.y - e.getX(), 2));
                project.addVisualElement(new AardSquare(
                        last.x - (r-last.r)/2, last.y-(r-last.r)/2, r,
                        checkBoxShapeFill.isSelected(),
                        checkBoxShapeStroke.isSelected(),
                        colourPickerShapeFill.getValue(),
                        colourPickerShapeStroke.getValue(),
                        Integer.parseInt(textFieldShapeStroke.getText())));
            }
            else if (eraserBtn.isSelected()) {
                FreeDrawLine eraser = project.getCurrentLine();
                eraser.addPoint(e.getX(), e.getY());
            }

            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            project.draw(gc);
        });
    }

    private void setCurrentColorDraw(Color color) {
        currentColorDraw = color;
        gc.setStroke(color);
        gc.setFill(color);
    }
    private void setCurrentColorText(Color color) {
        currentColorText = color;
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

    @FXML
    public void switchToProjects(javafx.event.ActionEvent event) throws IOException {
        FXMLController switcher = new FXMLController();
        switcher.switchToProjects(event);
    }

    public void resizeCanvas(ActionEvent actionEvent) {
        this.resizerController.resize();
    }

    public void clearCanvas(ActionEvent actionEvent) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(colorPickerDraw.getValue());
    }
    @FXML
    public void undo(ActionEvent event) {
        this.clearCanvas(event);
        project.undoVisualElement();
        project.draw(gc);
    }

    @FXML
    public void redo(ActionEvent event) {
        project.redoVisualElement(gc);
    }
}