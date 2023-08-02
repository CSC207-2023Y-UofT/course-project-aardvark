package controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    public Button renameBtn;
    public Button resizeBtn;
    public Button clearBtn;
    public Button exportBtn;
    public ToggleGroup selectTool;
    public RadioButton freeDrawBtn;
    @FXML
    public RadioButton radioButtonCircle;
    @FXML
    public RadioButton radioButtonSquare;
    public RadioButton textBoxBtn;
    public RadioButton eraserBtn;
    private static Project project = new Project("untitled project");
    @FXML
    public ComboBox<String> fontComboBox;
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
    @FXML
    public TextField fontSize;
    public TextField textField;
    public GraphicsContext gc;
    public static Stage primaryStage;
    private CanvasResizerController resizerController;
    Font defaultFont = Font.font("Verdana", 16);
    String [] defaultInput = new String[]{""};
    private ArrayList<AardText> textArrayList = new ArrayList<>();
    IntegerProperty sizeLabelProperty = new SimpleIntegerProperty(16);
    public void initialize() {
        // Initialize the canvas GraphicsContext, resizerController, colorPickerDraw
        gc = canvas.getGraphicsContext2D();
        resizerController = new CanvasResizerController(canvas, project);
        colorPickerDraw.setOnAction(e -> setCurrentColorDraw(colorPickerDraw.getValue()));
        colorPickerText.setOnAction(e -> setCurrentColorText(colorPickerText.getValue()));

        // Default font and font size
        gc.setFont(defaultFont);
        fontSize.setText("16");


        textField.setOnKeyReleased(e -> defaultInput[0] = textField.getText());

        /* Creating Text at a Specified Location */
        EventHandler<MouseEvent> writeTextHandler = event -> {
            if (textBoxBtn.isSelected()) {
                double xValue = event.getX();
                double yValue = event.getY();
                AardText newText = new AardText(defaultInput[0], currentColorText.toString(),
                        gc.getFont(), xValue, yValue);
                textArrayList.add(newText);
                newText.draw(gc);
            }
        };

        /*===== Changing and Adjusting Text-Related Settings on the Canvas =====*/

        /* Changing Font Family */
        // System.out.println(Font.getFamilies());
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

        fontComboBox.setOnAction(event -> {
            Font newFont = new Font(fontComboBox.getValue(), sizeLabelProperty.get());
            gc.setFont(newFont);
        });

        fontSize.setOnKeyReleased(event -> {
            Font newFont = new Font(fontComboBox.getValue(), Integer.parseInt(fontSize.getText()));
            sizeLabelProperty.set(Integer.parseInt(fontSize.getText()));
            gc.setFont(newFont);
        });

        /* Changing Text Colour */
        colorPickerText.setValue(Color.BLACK);
        EventHandler<ActionEvent> changeColorHandler = event -> gc.setFill(colorPickerText.getValue());
        colorPickerText.addEventFilter(ActionEvent.ACTION, changeColorHandler);

        /* Increasing Text Size */
        EventHandler<ActionEvent> increaseFontHandler = event -> {
            Font currentFont = gc.getFont();
            Font newFont = Font.font(currentFont.getFamily(), currentFont.getSize() + 1);
            sizeLabelProperty.set(sizeLabelProperty.get() + 1);
            fontSize.setText(Integer.toString(sizeLabelProperty.get()));
            gc.setFont(newFont);
        };
        textIncreaseBtn.setOnAction(increaseFontHandler);

        /* Decreasing Text Size */
        EventHandler<MouseEvent> decreaseFontHandler = event -> {
            Font currentFont = gc.getFont();
            Font newFont = Font.font(currentFont.getFamily(), currentFont.getSize() - 1);
            sizeLabelProperty.set(sizeLabelProperty.get() - 1);
            fontSize.setText(Integer.toString(sizeLabelProperty.get()));
            gc.setFont(newFont);
        };
        textDecreaseBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, decreaseFontHandler);

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);

        canvas.setOnMousePressed(e -> {
            if (freeDrawBtn.isSelected()) {
                canvas.removeEventFilter(MouseEvent.MOUSE_CLICKED, writeTextHandler);
                colorPickerText.removeEventFilter(ActionEvent.ACTION, changeColorHandler);

                currentColorDraw = colorPickerDraw.getValue();
                double size = Double.parseDouble(brushSize.getText());
                double x = e.getX();
                double y = e.getY();
                gc.setLineWidth(size);
                gc.beginPath();
                gc.moveTo(x, y);
                gc.setStroke(currentColorDraw);
                gc.stroke();
            }
            else if (radioButtonCircle.isSelected()) {
                //canvas.removeEventFilter(MouseEvent.MOUSE_CLICKED, writeTextHandler);
                //colorPickerText.removeEventFilter(ActionEvent.ACTION, changeColorHandler);

                double x = e.getX();
                double y = e.getY();

                AardVisualElement.addCircle(new AardCircle(
                        x - 1, y - 1, 2,
                        checkBoxShapeFill.isSelected(),
                        checkBoxShapeStroke.isSelected(),
                        colourPickerShapeFill.getValue(),
                        colourPickerShapeStroke.getValue(),
                        Integer.parseInt(textFieldShapeStroke.getText())));
            }
            else if (radioButtonSquare.isSelected()) {
                double x = e.getX();
                double y = e.getY();

                AardVisualElement.addSquare(new AardSquare(
                        x - 1, y - 1, 2,
                        checkBoxShapeFill.isSelected(),
                        checkBoxShapeStroke.isSelected(),
                        colourPickerShapeFill.getValue(),
                        colourPickerShapeStroke.getValue(),
                        Integer.parseInt(textFieldShapeStroke.getText())));
            }
            else if (textBoxBtn.isSelected()) {
                colorPickerText.addEventFilter(ActionEvent.ACTION, changeColorHandler);
                canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, writeTextHandler);
            }
            else if (eraserBtn.isSelected()) {
                canvas.removeEventFilter(MouseEvent.MOUSE_CLICKED, writeTextHandler);
                colorPickerText.removeEventFilter(ActionEvent.ACTION, changeColorHandler);

                double size = Double.parseDouble(brushSize.getText());
                double x = e.getX();
                double y = e.getY();
                gc.setLineWidth(size);
                gc.beginPath();
                gc.moveTo(x, y);
                gc.setStroke(Color.WHITE);
                gc.stroke();

                AardVisualElement.clear();
            }

            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            AardVisualElement.draw(gc);
        });

        canvas.setOnMouseDragged(e -> {
            if (freeDrawBtn.isSelected()) {
                canvas.removeEventFilter(MouseEvent.MOUSE_CLICKED, writeTextHandler);
                colorPickerText.removeEventFilter(ActionEvent.ACTION, changeColorHandler);

                currentColorDraw = colorPickerDraw.getValue();
                double size = Double.parseDouble(brushSize.getText());
                double x = e.getX();
                double y = e.getY();
                gc.setLineWidth(size);
                gc.lineTo(x, y);
                gc.setStroke(currentColorDraw);
                gc.stroke();
            }
            else if (radioButtonCircle.isSelected()) {
                double x = e.getX();
                double y = e.getY();

                AardCircle last = AardVisualElement.circles.get(AardVisualElement.circles.size() - 1);

                double r = Math.sqrt(Math.pow(last.x - x, 2) + Math.pow(last.y - y, 2));
                AardVisualElement.addCircle(new AardCircle(
                        last.x - (r-last.r)/2, last.y-(r-last.r)/2, r,
                        checkBoxShapeFill.isSelected(),
                        checkBoxShapeStroke.isSelected(),
                        colourPickerShapeFill.getValue(),
                        colourPickerShapeStroke.getValue(),
                        Integer.parseInt(textFieldShapeStroke.getText())));

                AardVisualElement.circles.remove(AardVisualElement.circles.size() - 2);
            }
            else if (radioButtonSquare.isSelected()) {
                double x = e.getX();
                double y = e.getY();

                AardSquare last = AardVisualElement.squares.get(AardVisualElement.squares.size() - 1);

                double r = Math.sqrt(Math.pow(last.x - x, 2) + Math.pow(last.y - y, 2));
                AardVisualElement.addSquare(new AardSquare(
                        last.x - (r-last.r)/2, last.y-(r-last.r)/2, r,
                        checkBoxShapeFill.isSelected(),
                        checkBoxShapeStroke.isSelected(),
                        colourPickerShapeFill.getValue(),
                        colourPickerShapeStroke.getValue(),
                        Integer.parseInt(textFieldShapeStroke.getText())));

                AardVisualElement.squares.remove(AardVisualElement.squares.size() - 2);
            }
            else if (eraserBtn.isSelected()) {
                canvas.removeEventFilter(MouseEvent.MOUSE_CLICKED, writeTextHandler);
                colorPickerText.removeEventFilter(ActionEvent.ACTION, changeColorHandler);

                double size = Double.parseDouble(brushSize.getText());
                double x = e.getX();
                double y = e.getY();
                gc.setLineWidth(size);
                gc.lineTo(x, y);
                gc.setStroke(Color.WHITE);
                gc.stroke();

                AardVisualElement.clear();
            }

            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            AardVisualElement.draw(gc);
        });
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
    public static void setProject(Project p) {
        EditorController.project = p;
    }

    public void resizeCanvas(ActionEvent actionEvent) {
        this.resizerController.resize();
    }

    public void clearCanvas(ActionEvent actionEvent) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(colorPickerDraw.getValue());
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
}