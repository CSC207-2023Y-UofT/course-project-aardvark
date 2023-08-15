package controllers;

import aardvark.MainAppRouter;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import models.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Date;


/**
 The EditorController class is the controller responsible for handling user interactions and managing the
 image editing functionality in the Aardvark application.

 It allows users to draw freehand lines, shapes, text, and use the eraser tool on the canvas.

 Users can also resize the canvas, clear the canvas, and export the edited image as a PNG file.

 This class is part of the Aardvark application, which is an image editor and project management tool.

 Users can create, open, and edit projects, as well as perform various image editing operations.

 The EditorController class interacts with the associated editor.fxml file that defines the graphical user interface.

 It also communicates with the Project class to manage the visual elements drawn on the canvas.
 */
public class EditorController {

    public final Project project;
    public final Scene scene;
    public Label projectName;
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
    @FXML
    private TextField brushSize;
    @FXML
    private TextField eraserSize;
    public TextField textField;
    public GraphicsContext gc;
    private CanvasResizerController resizerController;

    private MediaPlayer mediaPlayer;

    final Font defaultFont = Font.font("Verdana", 16);
    final String [] defaultInput = new String[]{""};

    /**
     * Constructor for EditorController.
     * Initializes the EditorController with the provided Project and Scene.
     *
     * @param p The Project associated with the EditorController.
     * @param s The Scene associated with the EditorController.
     */
    public EditorController(Project p, Scene s) {
        project = p;
        scene = s;
    }

    /**
     * Initializes the EditorController after the FXML elements have been loaded.
     * Sets up event handlers, default settings, and drawing tools.
     */
    public void initialize() {
        /* INITIALIZE */
        gc = canvas.getGraphicsContext2D();
        resizerController = new CanvasResizerController(canvas, project);
        resizerController.resizeCanvas(project.getWidth(), project.getHeight());

        //Setting Project Name
        projectName.setText(project.getName());

        //Setting up Arthur theme song
        try {
            Media arthur = new Media(new File("src/main/resources/sound/arthur.mp3").toURI().toString());
            mediaPlayer = new MediaPlayer(arthur);
        } catch (Exception e) {
            System.out.println("InvocationTargetException occurred: " + e.getMessage());
        }

        // default button
        freeDrawBtn.setSelected(true);
        checkBoxShapeFill.setSelected(true);

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

        project.draw(gc);

        /* SETTINGS */

//      default state of settings boxes
        hideDiv();
        selectTool.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (freeDrawBtn.isSelected()) {
                hideDiv();
                brushDiv.setVisible(true);
                brushDiv.setManaged(true);
            }
            else if (radioButtonCircle.isSelected() || radioButtonSquare.isSelected()) {
                hideDiv();
                shapesDiv.setVisible(true);
                shapesDiv.setManaged(true);
            }
            else if (textBoxBtn.isSelected()) {
                hideDiv();
                textDiv.setVisible(true);
                textDiv.setManaged(true);
                textField.requestFocus();
            }
            else if (eraserBtn.isSelected()) {
                hideDiv();
                eraserDiv.setVisible(true);
                eraserDiv.setManaged(true);
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
                if(textField.getText().equals("Arthur")) {
                    mediaPlayer.play();
                }
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
    }

    private void hideDiv() {
        brushDiv.setVisible(false);
        brushDiv.setManaged(false);
        eraserDiv.setVisible(false);
        eraserDiv.setManaged(false);
        textDiv.setVisible(false);
        textDiv.setManaged(false);
        shapesDiv.setVisible(false);
        shapesDiv.setManaged(false);
    }

    /**
     * Saves the canvas content as a PNG image file.
     * Displays a FileChooser dialog to select the destination file and exports the image.
     */
    @SuppressWarnings("BlockingMethodInNonBlockingContext")
    public void onSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export as PNG");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files",
                        "*.png", "*.jpeg", "*.jpg"));
        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());
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

    /**
     * Switches to the Projects view.
     * Called when the user wants to go back to the Projects view from the Editor view.
     *
     * @param event The ActionEvent that triggers the switch.
     * @throws IOException If there is an error while loading the projects.fxml file.
     */
    @SuppressWarnings("unused")
    @FXML
    public void switchToProjects(javafx.event.ActionEvent event) throws IOException {
        project.setDate(new Date());
        MainAppRouter switcher = new MainAppRouter();
        switcher.switchToProjects(event);
    }

    /**
     * Resizes the canvas to the specified dimensions.
     * Called when the user wants to resize the canvas from the Editor view.
     *
     * @param ignoredActionEvent The ActionEvent that triggers the resize.
     */
    public void resizeCanvas(ActionEvent ignoredActionEvent) {
        this.resizerController.resize();
    }

    /**
     * Clears the canvas by filling it with a white color.
     * Called when the user wants to clear the canvas from the Editor view.
     *
     * @param ignoredActionEvent The ActionEvent that triggers the clear action.
     */
    public void clearCanvas(ActionEvent ignoredActionEvent) {
        project.addVisualElement(new AardSquare(
                0, 0, Math.max(canvas.getWidth(), canvas.getHeight()),
                true, true, Color.WHITE, Color.WHITE, 0));
    }

    /**
     * Undoes the last visual element drawn on the canvas.
     * Called when the user wants to undo the last action from the Editor view.
     *
     * @param event The ActionEvent that triggers the undo action.
     */
    @FXML
    public void undo(ActionEvent event) {
        this.clearCanvas(event);
        project.undoVisualElement();
        project.draw(gc);
        if(textField.getText().equals("Arthur")) {
            mediaPlayer.stop();
        }
    }

    /**
     * Redoes the last undone visual element on the canvas.
     * Called when the user wants to redo an undone action from the Editor view.
     *
     * @param ignoredEvent The ActionEvent that triggers the redo action.
     */
    @FXML
    public void redo(ActionEvent ignoredEvent) {
        project.redoVisualElement(gc);
    }

    /**
     * Validates and returns a double value from the provided TextField.
     * If the value is not a valid double or is outside the range (0, 1000), it sets the preferredSize and returns it.
     *
     * @param generalTextField The TextField from which to retrieve the value.
     * @param preferredSize The default size to use if the value is invalid or out of range.
     * @return The validated double value or the preferredSize if the value is invalid or out of range.
     */
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