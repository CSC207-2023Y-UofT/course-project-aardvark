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
import javafx.scene.input.KeyEvent;
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
    private Project project;
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
    private ArrayList<AardText> textArrayList = new ArrayList<>();
    private ArrayList<VisualElement> visualElementArrayList = new ArrayList<>();
    IntegerProperty sizeLabelProperty = new SimpleIntegerProperty(16);

    public void initialize() {
        // Initialize the canvas GraphicsContext, resizerController, colorPickerDraw
        gc = canvas.getGraphicsContext2D();
        resizerController = new CanvasResizerController(canvas);
        colorPickerDraw.setOnAction(e -> setCurrentColorDraw(colorPickerDraw.getValue()));
        colorPickerText.setOnAction(e -> setCurrentColorText(colorPickerText.getValue()));

        // Default font and font size
        gc.setFont(defaultFont);
        fontSize.setText("16");
        textField.setOnKeyReleased(e -> defaultInput[0] = textField.getText());

        /*===== Changing and Adjusting Text-Related Settings on the Canvas =====*/
        /* Changing Font Family
        * Adding a list of font options
        * Setting the default font to Verdana */
        addFonts(fontComboBox);
        fontComboBox.setValue("Verdana");
        fontComboBox.setOnAction(this::changeFont);

        /* Implementing functionality of fontSize textfield  */
        fontSize.setOnKeyReleased(this::changeFontSize);
        /* Setting Default Colours in Color Pickers */
        colorPickerDraw.setValue(currentColorDraw);
        colorPickerText.setValue(currentColorText);

        /* Setting Default Colours in Color Pickers */
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
        freeDrawBtn.setSelected(true);

        canvas.setOnMousePressed(e -> {
            if (freeDrawBtn.isSelected()) {
                canvas.removeEventFilter(MouseEvent.MOUSE_CLICKED, this::writeTextHandler);
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
                canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, this::writeTextHandler);
            } else if (eraserBtn.isSelected()) {
                canvas.removeEventFilter(MouseEvent.MOUSE_CLICKED, this::writeTextHandler);
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
                canvas.removeEventFilter(MouseEvent.MOUSE_CLICKED, this::writeTextHandler);
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
                canvas.removeEventFilter(MouseEvent.MOUSE_CLICKED, this::writeTextHandler);
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
    public void setProject(Project p) {
        this.project = p;
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

    /* Creating Text at a Specified Location */
    private void writeTextHandler(MouseEvent e) {
        if (textBoxBtn.isSelected()) {
            double xValue = e.getX();
            double yValue = e.getY();
            AardText newText = new AardText(defaultInput[0], currentColorText.toString(),
                    gc.getFont(), xValue, yValue);
            textArrayList.add(newText);
            newText.draw(gc);
        }
    }
    private void addFonts(ComboBox<String> fontComboBox) {
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
    }
    private void changeFont(ActionEvent e) {
        Font newFont = new Font(fontComboBox.getValue(), sizeLabelProperty.get());
        gc.setFont(newFont);
    }

    private void changeFontSize(KeyEvent e) {
        Font newFont = new Font(fontComboBox.getValue(), Integer.parseInt(fontSize.getText()));
        sizeLabelProperty.set(Integer.parseInt(fontSize.getText()));
        gc.setFont(newFont);
    }
}