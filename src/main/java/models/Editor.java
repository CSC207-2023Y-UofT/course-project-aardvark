package models;

import controllers.CanvasExportController;
import controllers.CanvasResizerController;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import text.AardText;

import java.util.ArrayList;

public class Editor extends Application {
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Color currentColor = Color.BLACK;
    private boolean isFreeDrawMode = true;

    public void start(Stage primaryStage) {
        // Create canvas and graphics context
        canvas = new Canvas(800, 600);
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(2.5);

        // Set up a new Project
        Project projectItem = new Project("Hello World");

        ArrayList<AardText> textArrayList = new ArrayList<>();

        // Create a resize controller and set the name of the project to the
        CanvasResizerController canvasResizer = new CanvasResizerController(canvas);

        // Create a export controller
        CanvasExportController canvasExporter = new CanvasExportController(canvas, projectItem);

        primaryStage.setTitle(projectItem.getName());

        // Color picker to allow selecting custom colors
        ColorPicker colorPicker = new ColorPicker(currentColor);
        colorPicker.setOnAction(e -> setCurrentColor(colorPicker.getValue()));

        // BorderPane to arrange the canvas and control buttons
        BorderPane root = new BorderPane();
        root.setCenter(canvas);

        // Clear button to reset the canvas
        Button clearBtn = new Button("Clear Canvas");
        clearBtn.setOnAction(e -> clearCanvas());

        // Button to resize and export the canvas
        Button resizeBtn = new Button("Resize Canvas");
        resizeBtn.setOnAction(e -> canvasResizer.resize());

        Button exportBtn = new Button("Export as PNG");
        exportBtn.setOnAction(e -> canvasExporter.export(primaryStage, projectItem));

        // Create the "Free Draw" and "Textbox" toggle buttons
        ToggleButton freeDrawBtn = new ToggleButton("Free Draw");
        ToggleButton textboxBtn = new ToggleButton("Textbox");
        freeDrawBtn.setSelected(true); // Start in Free Draw mode

        freeDrawBtn.setOnAction(e -> {
            isFreeDrawMode = true;
            textboxBtn.setSelected(false);
        });

        textboxBtn.setOnAction(e -> {
            isFreeDrawMode = false;
            freeDrawBtn.setSelected(false);
        });


        HBox modeToggleButtonBox = new HBox(10, freeDrawBtn, textboxBtn);
        HBox controlBox = new HBox(10);
        VBox toolsRoot = new VBox();
        VBox canvasRoot = new VBox();
        Separator HBoxSeparator = new Separator();
        HBoxSeparator.setMaxWidth(10);
        HBoxSeparator.setOrientation(Orientation.VERTICAL);

        Font defaultFont = Font.font("Verdana", 16);
        graphicsContext.setFont(defaultFont);
        String [] defaultInput = new String[]{"Hello World!"};
        IntegerProperty sizeLabelProperty = new SimpleIntegerProperty(15);

        /*===== Creating Text =====*/

        /* Setting the Text */
        TextField textField = new TextField("");
        EventHandler<ActionEvent> setTextHandler = event -> defaultInput[0] = textField.getText();

        textField.setOnAction(setTextHandler);
        TextInputDialog textDialog = new TextInputDialog();

        textDialog.setTitle("Input Text");
        textDialog.setHeaderText("Please input text");

        textDialog.setX(8);
        textDialog.setY(8);

        EventHandler<DialogEvent> getTextHandler = event -> defaultInput[0] = textDialog.getResult();
        textDialog.setOnCloseRequest(getTextHandler);

        /* Creating Text at a Specified Location */
        EventHandler<MouseEvent> writeTextHandler = event -> {
            if (!(isFreeDrawMode)) {
                double xValue = event.getX();
                double yValue = event.getY();
                textDialog.showAndWait();
                Color currentColor = (Color) graphicsContext.getFill();
                AardText newText = new AardText(defaultInput[0], currentColor.toString(),
                        graphicsContext.getFont(), xValue, yValue);
                textArrayList.add(newText);
                newText.draw(graphicsContext);
            }
        };
        canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, writeTextHandler);

        // canvasRoot.getChildren().add(canvas);
        canvasRoot.getChildren().add(textField);

        /*===== Changing and Adjusting Text-Related Settings on the Canvas =====*/

        /* Changing Font Family */
        System.out.println(Font.getFamilies());
        final ComboBox<String> fontComboBox = new ComboBox<String>();
        fontComboBox.getItems().addAll(
                "Algerian",
                "Arial",
                "Cooper Black",
                "Century Gothic",
                "Verdana",
                "Times New Roman"
        );
        fontComboBox.setValue("Verdana");
        EventHandler<ActionEvent> changeFontHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Font newFont = new Font(fontComboBox.getValue(), sizeLabelProperty.get());
                graphicsContext.setFont(newFont);
            }
        };
        fontComboBox.addEventFilter(ActionEvent.ACTION, changeFontHandler);
        toolsRoot.getChildren().add(fontComboBox);

        /* Changing Text Colour */
        colorPicker.setValue(Color.BLACK);
        EventHandler<ActionEvent> changeColorHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                graphicsContext.setFill(colorPicker.getValue());
            }
        };
        colorPicker.addEventFilter(ActionEvent.ACTION, changeColorHandler);
        toolsRoot.getChildren().add(colorPicker);

        /* Setting Text Size */
        Label sizeLabel = new Label("15");
        sizeLabel.textProperty().bind(sizeLabelProperty.asString("Font size: %d"));
        toolsRoot.getChildren().add(sizeLabel);

        /* Increasing Text Size */
        Button btIncreaseText = new Button("Text+");
        EventHandler<MouseEvent> increaseFontHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Font currentFont = graphicsContext.getFont();
                Font newFont = Font.font(currentFont.getFamily(), currentFont.getSize() + 1);
                sizeLabelProperty.set(sizeLabelProperty.get() + 1);
                graphicsContext.setFont(newFont);
            }
        };
        btIncreaseText.addEventFilter(MouseEvent.MOUSE_CLICKED, increaseFontHandler);
        toolsRoot.getChildren().add(btIncreaseText);

        /* Decreasing Text Size */
        Button btDecreaseText = new Button("Text-");
        EventHandler<MouseEvent> decreaseFontHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Font currentFont = graphicsContext.getFont();
                Font newFont = Font.font(currentFont.getFamily(), currentFont.getSize() - 1);
                sizeLabelProperty.set(sizeLabelProperty.get() - 1);
                graphicsContext.setFont(newFont);
            }
        };
        btDecreaseText.addEventFilter(MouseEvent.MOUSE_CLICKED, decreaseFontHandler);
        toolsRoot.getChildren().add(btDecreaseText);


        // Set up the scene
        Scene scene = new Scene(root, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.show();

        controlBox.getChildren().addAll(colorPicker, clearBtn, resizeBtn, exportBtn);
        controlBox.getChildren().addAll(toolsRoot, HBoxSeparator, canvasRoot, modeToggleButtonBox);
        root.setCenter(canvas);
        root.setTop(controlBox);


        // Event handler for drawing on the canvas
        canvas.setOnMousePressed(e -> {
            if (isFreeDrawMode) {
                graphicsContext.beginPath();
                graphicsContext.moveTo(e.getX(), e.getY());
                graphicsContext.setStroke(currentColor);
                graphicsContext.stroke();
            }
        });

        canvas.setOnMouseDragged(e -> {
            if (isFreeDrawMode) {
                graphicsContext.lineTo(e.getX(), e.getY());
                graphicsContext.stroke();
            }
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
        graphicsContext.setStroke(color);
        graphicsContext.setFill(color);
    }

    // Helper method to clear the canvas
    private void clearCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
    public static void main(String[] args) {
        launch(args);
    }
}
