//package controllers;
//
//import aardvark.MainAppRouter;
//import models.*;
//import javafx.beans.property.IntegerProperty;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.embed.swing.SwingFXUtils;
//import javafx.event.ActionEvent;
//import javafx.scene.Scene;
//import javafx.scene.input.*;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Font;
//import javafx.fxml.FXML;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.control.*;
//import javafx.scene.image.Image;
//import javafx.scene.image.WritableImage;
//import javafx.scene.paint.Color;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//
//import javax.imageio.ImageIO;
//import java.io.File;
//import java.io.IOException;
//
//public class EditorSettingsController {
//    public final Project project;
//    public final Scene scene;
//    public Label projectName;
//    public Button resizeBtn;
//    public Button clearBtn;
//    public Button exportBtn;
//    public Button undoBtn;
//    public RadioButton freeDrawBtn;
//    @FXML
//    public RadioButton radioButtonCircle;
//    @FXML
//    public RadioButton radioButtonSquare;
//    public RadioButton textBoxBtn;
//    public RadioButton eraserBtn;
//    @FXML
//    public ComboBox<String> fontComboBox;
//    @FXML
//    public TextField fontSize;
//    @FXML
//    private Canvas canvas;
//    @FXML
//    private ColorPicker colorPickerDraw;
//    @FXML
//    public ColorPicker colorPickerText;
//    @FXML
//    public ColorPicker colourPickerShapeFill;
//    @FXML
//    public ColorPicker colourPickerShapeStroke;
//    @FXML
//    public CheckBox checkBoxShapeFill;
//    @FXML
//    public CheckBox checkBoxShapeStroke;
//    @FXML
//    public TextField textFieldShapeStroke;
//    @FXML
//    public VBox brushDiv;
//    @FXML
//    public VBox textDiv;
//    @FXML
//    public VBox shapesDiv;
//    @FXML
//    public ToggleGroup selectTool;
//    private Color currentColorDraw = Color.BLACK;
//    private Color currentColorText = Color.BLACK;
//    @FXML
//    private TextField brushSize;
//    public TextField textField;
//    public GraphicsContext gc;
//    public static Stage primaryStage;
//    private CanvasResizerController resizerController;
//
//    Font defaultFont = Font.font("Verdana", 16);
//    String [] defaultInput = new String[]{""};
//    IntegerProperty sizeLabelProperty = new SimpleIntegerProperty(16);
//
//    public void initialize() {
//
//        // default button
//        freeDrawBtn.setSelected(true);
//        checkBoxShapeFill.setSelected(true);
//    }
//
//}
