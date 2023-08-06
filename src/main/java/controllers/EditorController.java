package controllers;

import aardvark.MainAppRouter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class EditorController {
    private AardWritableImage fxImage;

    public final Project project;
    public final Scene scene;
    public Label projectName;
    public Button resizeBtn;
    public Button clearBtn;
    public Button exportBtn;
    public Button undoBtn;
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
    @FXML
    public VBox brushDiv;
    @FXML
    public VBox textDiv;
    @FXML
    public VBox shapesDiv;
    @FXML
    public VBox eraserDiv;
    @FXML
    public ToggleGroup selectTool;
    private Color currentColorDraw = Color.BLACK;
    private Color currentColorText = Color.BLACK;
    @FXML
    private TextField brushSize;
    @FXML
    private TextField eraserSize;
    public TextField textField;
    public GraphicsContext gc;
    public static Stage primaryStage;
    private CanvasResizerController resizerController;

    Font defaultFont = Font.font("Verdana", 16);
    String [] defaultInput = new String[]{""};
//    private ArrayList<VisualElement>

    public EditorController(Project p, Scene s) {
        project = p;
        scene = s;
    }

    public EditorController(Project p, Scene s, AardWritableImage fxImage) {
        this.project = p;
        this.scene = s;
        this.fxImage = fxImage;
    }

    public void initialize() {
        /* INITIALIZE */
        gc = canvas.getGraphicsContext2D();
        resizerController = new CanvasResizerController(canvas, project);

        projectName.setText(project.getName());

        // default button
        freeDrawBtn.setSelected(true);
        checkBoxShapeFill.setSelected(true);

        /* CLEAR */

        clearBtn.setOnMousePressed(e -> {
            project.addVisualElement(new AardSquare(
                    0, 0, Math.max(canvas.getWidth(), canvas.getHeight()),
                    true, true, Color.WHITE, Color.WHITE, 0));
        });

        // Event handler for Ctrl+Z (Undo)
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN).match(event)) {
                undo(null);
                event.consume();
            }
        });

        // Event handler for Ctrl+Shift+Z (Redo)
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN).match(event)) {
                redo(null);
                event.consume();
            }
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

        /* WHITE CANVAS BACKGROUND */

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        /* SETTINGS */

//      default state of settings boxes
        brushDiv.setVisible(true);
        brushDiv.setManaged(true);
        eraserDiv.setVisible(false);
        eraserDiv.setManaged(false);
        textDiv.setVisible(false);
        textDiv.setManaged(false);
        shapesDiv.setVisible(false);
        shapesDiv.setManaged(false);
        selectTool.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (freeDrawBtn.isSelected()) {
                    brushDiv.setVisible(true);
                    brushDiv.setManaged(true);
                    eraserDiv.setVisible(false);
                    eraserDiv.setManaged(false);
                    textDiv.setVisible(false);
                    textDiv.setManaged(false);
                    shapesDiv.setVisible(false);
                    shapesDiv.setManaged(false);
                }
                else if (radioButtonCircle.isSelected()) {
                    brushDiv.setVisible(false);
                    brushDiv.setManaged(false);
                    eraserDiv.setVisible(false);
                    eraserDiv.setManaged(false);
                    textDiv.setVisible(false);
                    textDiv.setManaged(false);
                    shapesDiv.setVisible(true);
                    shapesDiv.setManaged(true);
                }
                else if (radioButtonSquare.isSelected()) {
                    brushDiv.setVisible(false);
                    brushDiv.setManaged(false);
                    eraserDiv.setVisible(false);
                    eraserDiv.setManaged(false);
                    textDiv.setVisible(false);
                    textDiv.setManaged(false);
                    shapesDiv.setVisible(true);
                    shapesDiv.setManaged(true);
                }
                else if (textBoxBtn.isSelected()) {
                    brushDiv.setVisible(false);
                    brushDiv.setManaged(false);
                    eraserDiv.setVisible(false);
                    eraserDiv.setManaged(false);
                    textDiv.setVisible(true);
                    textDiv.setManaged(true);
                    shapesDiv.setVisible(false);
                    shapesDiv.setManaged(false);
                    textField.requestFocus();
                }
                else if (eraserBtn.isSelected()) {
                    brushDiv.setVisible(false);
                    brushDiv.setManaged(false);
                    eraserDiv.setVisible(true);
                    eraserDiv.setManaged(true);
                    textDiv.setVisible(false);
                    textDiv.setManaged(false);
                    shapesDiv.setVisible(false);
                    shapesDiv.setManaged(false);
                }
            }
        });

        canvas.setOnMousePressed(e -> {
            if (freeDrawBtn.isSelected()) {
                double size = checkValidSize(brushSize, 3);

                FreeDrawLine newLine = new FreeDrawLine(colorPickerDraw.getValue(), size);
                newLine.addPoint(e.getX(), e.getY());

                project.addVisualElement(newLine);
            }
            else if (radioButtonCircle.isSelected()) {
                double circleSize = checkValidSize(textFieldShapeStroke, 3);
                project.addVisualElement(new AardCircle(
                        e.getX() - 1, e.getY() - 1, 2,
                        checkBoxShapeFill.isSelected(),
                        checkBoxShapeStroke.isSelected(),
                        colourPickerShapeFill.getValue(),
                        colourPickerShapeStroke.getValue(),
                        circleSize));
            }
            else if (radioButtonSquare.isSelected()) {
                double squareSize = checkValidSize(textFieldShapeStroke, 3);
                project.addVisualElement(new AardSquare(
                        e.getX() - 1, e.getY() - 1, 2,
                        checkBoxShapeFill.isSelected(),
                        checkBoxShapeStroke.isSelected(),
                        colourPickerShapeFill.getValue(),
                        colourPickerShapeStroke.getValue(),
                        squareSize));
            }
            else if (textBoxBtn.isSelected()) {
                double textCheckedSize = checkValidSize(fontSize, 16);
                project.addVisualElement(new AardText(
                        textField.getText(),
                        colorPickerText.getValue(),
                        new Font(fontComboBox.getValue(), textCheckedSize),
                        e.getX(), e.getY()));
            }
            else if (eraserBtn.isSelected()) {
                double size = checkValidSize(eraserSize, 3);

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

                double circleSize = checkValidSize(textFieldShapeStroke, 3);
                double r = Math.sqrt(Math.pow(last.x - e.getX(), 2) + Math.pow(last.y - e.getY(), 2));
                project.addVisualElement(new AardCircle(
                        last.x - (r-last.r)/2, last.y-(r-last.r)/2, r,
                        checkBoxShapeFill.isSelected(),
                        checkBoxShapeStroke.isSelected(),
                        colourPickerShapeFill.getValue(),
                        colourPickerShapeStroke.getValue(),
                        circleSize));
            }
            else if (radioButtonSquare.isSelected()) {
                AardSquare last = project.getLastAndRemoveSquare();

                double squareSize = checkValidSize(textFieldShapeStroke, 3);
                double r = Math.sqrt(Math.pow(last.x - e.getX(), 2) + Math.pow(last.y - e.getY(), 2));
                project.addVisualElement(new AardSquare(
                        last.x - (r-last.r)/2, last.y-(r-last.r)/2, r,
                        checkBoxShapeFill.isSelected(),
                        checkBoxShapeStroke.isSelected(),
                        colourPickerShapeFill.getValue(),
                        colourPickerShapeStroke.getValue(),
                        squareSize));
            }
            else if (eraserBtn.isSelected()) {
                FreeDrawLine eraser = project.getCurrentLine();
                eraser.addPoint(e.getX(), e.getY());
            }

            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            project.draw(gc);
        });
        if (fxImage != null) {
            resizerController.resizeCanvas(fxImage.getWidth(), fxImage.getHeight());
            gc.drawImage(fxImage, 0, 0);
            project.addVisualElement(fxImage);
        }
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
        MainAppRouter switcher = new MainAppRouter();
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

    public double checkValidSize(TextField generalTextField, double preferredSize) {
        try {
            double newDouble = Double.parseDouble(generalTextField.getText());
            if (newDouble > 0 && newDouble < 1000){
                return newDouble;
            }
            else {
                generalTextField.setText(Double.toString(preferredSize));
                return preferredSize;
            }
        }
        catch (NumberFormatException numberFormatException) {
            generalTextField.setText(Double.toString(preferredSize));
            return preferredSize;
        }
    }


}