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
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Project;
import models.VisualElement;
import org.openjfx.FXMLController;
import text.AardText;
import text.ChangeSettingsUseCase;
import text.WriteTextUseCase;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EditorController {
    public Button renameBtn;
    public Button resizeBtn;
    public Button clearBtn;
    public Button exportBtn;
    public ToggleGroup selectTool;
    public RadioButton freeDrawBtn;
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
//    private ArrayList<VisualElement>
    IntegerProperty sizeLabelProperty = new SimpleIntegerProperty(16);

    public void initialize() {
        // Initialize the canvas GraphicsContext, resizerController, colorPickerDraw
        gc = canvas.getGraphicsContext2D();
        resizerController = new CanvasResizerController(canvas, project);
        colorPickerDraw.setOnAction(e -> setCurrentColorDraw(colorPickerDraw.getValue()));
        colorPickerText.setOnAction(e -> setCurrentColorText(colorPickerText.getValue()));

        /*===== Text Features =====*/
        gc.setFont(defaultFont);
        fontSize.setText("16");
        colorPickerText.setValue(Color.BLACK);
        textField.setOnKeyReleased(e -> defaultInput[0] = textField.getText());

        EventHandler<MouseEvent> writeTextHandler = event -> {
            if (textBoxBtn.isSelected()) {
                WriteTextUseCase writeTextUseCase = new WriteTextUseCase(gc, event);
                AardText newText = writeTextUseCase.writeText(defaultInput[0], currentColorText);
            }
        };

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
            ChangeSettingsUseCase changeFont = new ChangeSettingsUseCase(gc,
                    fontComboBox.getValue(), sizeLabelProperty.get());
            changeFont.changeFontFamily();
        });

        /* Changing Text Colour */
        EventHandler<ActionEvent> changeColorHandler = event -> {
            ChangeSettingsUseCase changeFontColor = new ChangeSettingsUseCase(gc);
            changeFontColor.changeFontColor(colorPickerText);
        };
        colorPickerText.addEventFilter(ActionEvent.ACTION, changeColorHandler);

        /* Changing Font Size */
        fontSize.setOnKeyReleased(event -> {
            ChangeSettingsUseCase changeFontSize = new ChangeSettingsUseCase(gc,
                    fontComboBox.getValue(), Integer.parseInt(fontSize.getText()));
            changeFontSize.changeFontSize(sizeLabelProperty);
        });

        /* Increasing Text Size */
        textIncreaseBtn.setOnAction(event -> {
            ChangeSettingsUseCase changeFontSize = new ChangeSettingsUseCase(gc);
            changeFontSize.incrementFontSize(sizeLabelProperty, fontSize, "+");
        });

        /* Decreasing Text Size */
        textDecreaseBtn.setOnAction(event -> {
            ChangeSettingsUseCase changeFontSize = new ChangeSettingsUseCase(gc);
            changeFontSize.incrementFontSize(sizeLabelProperty, fontSize, "-");
        });

        /*===== Brush/FreeDraw Features =====*/
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);

        freeDrawBtn.setSelected(true);

        canvas.setOnMousePressed(e -> {
            if (freeDrawBtn.isSelected()) {
                canvas.removeEventFilter(MouseEvent.MOUSE_CLICKED, writeTextHandler);
                colorPickerText.removeEventFilter(ActionEvent.ACTION, changeColorHandler);

                currentColorDraw = colorPickerDraw.getValue();
                double size = Double.parseDouble(brushSize.getText());
                double x = e.getX();
                double y = e.getY();
                gc.setLineWidth(size);
                gc.setLineCap(StrokeLineCap.ROUND);
                gc.setLineJoin(StrokeLineJoin.ROUND);
                gc.beginPath();
                gc.moveTo(x, y);
                gc.setStroke(currentColorDraw);
                gc.stroke();
            } else if (textBoxBtn.isSelected()) {
                colorPickerText.addEventFilter(ActionEvent.ACTION, changeColorHandler);
                canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, writeTextHandler);
            } else if (eraserBtn.isSelected()) {
                canvas.removeEventFilter(MouseEvent.MOUSE_CLICKED, writeTextHandler);
                colorPickerText.removeEventFilter(ActionEvent.ACTION, changeColorHandler);

                double size = Double.parseDouble(brushSize.getText());
                double x = e.getX();
                double y = e.getY();
                gc.setLineWidth(size);
                gc.setLineCap(StrokeLineCap.ROUND);
                gc.setLineJoin(StrokeLineJoin.ROUND);
                gc.beginPath();
                gc.moveTo(x, y);
                gc.setStroke(Color.WHITE);
                gc.stroke();
            }
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
                gc.setLineCap(StrokeLineCap.ROUND);
                gc.setLineJoin(StrokeLineJoin.ROUND);
                gc.lineTo(x, y);
                gc.setStroke(currentColorDraw);
                gc.stroke();
            } else if (eraserBtn.isSelected()) {
                canvas.removeEventFilter(MouseEvent.MOUSE_CLICKED, writeTextHandler);
                colorPickerText.removeEventFilter(ActionEvent.ACTION, changeColorHandler);

                double size = Double.parseDouble(brushSize.getText());
                double x = e.getX();
                double y = e.getY();
                gc.setLineWidth(size);
                gc.setLineCap(StrokeLineCap.ROUND);
                gc.setLineJoin(StrokeLineJoin.ROUND);
                gc.lineTo(x, y);
                gc.setStroke(Color.WHITE);
                gc.stroke();
            }
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