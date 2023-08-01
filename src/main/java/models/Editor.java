//package models;
//
//import controllers.CanvasExportController;
//import controllers.CanvasResizerController;
//import javafx.application.Application;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.control.Button;
//import javafx.scene.control.ColorPicker;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.RadioButton;
//import javafx.scene.control.Slider;
//import javafx.scene.control.ToggleButton;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.stage.Stage;
//import org.openjfx.FXMLController;
//import text.AardText;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//import controllers.CanvasExportController;
//import controllers.CanvasResizerController;
//import javafx.application.Application;
//import javafx.beans.property.IntegerProperty;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Orientation;
//import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.control.*;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Font;
//import javafx.stage.Stage;
//import text.AardText;
//
//import java.util.ArrayList;
//
//        public class Editor extends Application {
//            @FXML
//            private Button renameBtn;
//            @FXML
//            private Canvas canvas;
//            @FXML
//            private Button resizeBtn;
//            @FXML
//            private Button clearBtn;
//            @FXML
//            private Button exportBtn;
//            @FXML
//            private RadioButton freeDrawBtn;
//            @FXML
//            private RadioButton textBoxBtn;
//            @FXML
//            private VBox projectsLayout;
//            private GraphicsContext graphicsContext;
//            private Color currentColor = Color.BLACK;
//            private ColorPicker colorPicker;
//            private ToggleGroup toggleGroup;
//            private double x;
//            private double y;
//            private Stage stage;
//            private Scene scene;
//            private Parent root;
//            private ArrayList<AardText> textArrayList = new ArrayList<>();
//            // Set up a new Project
//            Project projectItem = new Project("Hello World");
//
//            // Create a resize controller and set the name of the project to the
//            CanvasResizerController canvasResizer = new CanvasResizerController(canvas);
//            // Create a export controller
//            CanvasExportController canvasExporter = new CanvasExportController(canvas, projectItem);
//
//            public static models.Editor instance;
//
//            public static models.Editor getInstance() {
//                if (instance == null) {
//                    instance = new models.Editor();
//                }
//                return instance;
//            }
//
//            public Editor() {
//                instance = this;
//    }

//    @FXML
//    private void clearCanvas() {
//        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//    }
//    @FXML
//    public void resizeCanvas(ActionEvent actionEvent) {
//        canvasResizer.resize();
//    }
//    @FXML
//    public void switchToProjects(javafx.event.ActionEvent event) throws IOException {
//        FXMLController temp = new FXMLController();
//        temp.switchToProjects(event);
//    }

//    private Button createColorButton(Color color) {
//        Button button = new Button();
//        button.setPrefSize(30, 30);
//        button.setStyle("-fx-background-color: #" + color.toString().substring(2) + ";");
//        button.setOnAction(e -> setCurrentColor(color));
//        return button;
//    }
//    // Helper method to set the current drawing color
//
//    private void setCurrentColor(Color color) {
//        currentColor = color;
//        graphicsContext.setStroke(color);
//        graphicsContext.setFill(color);
//    }
//    // Helper method to clear the canvas
//
//    @FXML
//    public void exportCanvas(ActionEvent actionEvent) {
//        canvasExporter.export(this.stage, projectItem);
//    }
//    public void actPressed(MouseEvent mouseEvent) {
//        this.x = mouseEvent.getX();
//        this.y = mouseEvent.getY();
//
//        if (freeDrawBtn.isPressed()) {
//            graphicsContext.beginPath();
//            graphicsContext.moveTo(x, y);
//            graphicsContext.setStroke(currentColor);
//            graphicsContext.stroke();
//        }
//    }
//    public void actDragged(MouseEvent mouseEvent) {
//        this.x = mouseEvent.getX();
//        this.y = mouseEvent.getY();
//        if (freeDrawBtn.isPressed()) {
//            graphicsContext.lineTo(x, y);
//            graphicsContext.stroke();
//        }
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @FXML
//
//    public void start(Stage primaryStage) throws IOException {
//        // Create canvas and graphics context
//        textBoxBtn.isPressed();
//        Parent root = FXMLLoader.load(getClass().getResource("/resources/org.openjfx/editor.fxml"));
//        Scene scene = new Scene(root, 1920, 1080);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle(projectItem.getName());
//        this.graphicsContext = canvas.getGraphicsContext2D();
//        this.graphicsContext.setLineWidth(3.0);
//
//        this.stage = primaryStage;
//        primaryStage.show();

//        ToggleButton freeDrawBtn = (ToggleButton) loader.getNamespace().get("freeDrawBtn");
//        ToggleButton textboxBtn = (ToggleButton) loader.getNamespace().get("textboxBtn");
//        freeDrawBtn.setSelected(true); // Start in Free Draw mode
//
//        freeDrawBtn.setOnAction(e -> {
//            isFreeDrawMode = true;
//            textboxBtn.setSelected(false);
//        });
//
//        textboxBtn.setOnAction(e -> {
//            isFreeDrawMode = false;
//            freeDrawBtn.setSelected(false);
//        });
//
//        // Clear button to reset the canvas
//        Button clearBtn = (Button) loader.getNamespace().get("clear-btn");
//        clearBtn.setOnAction(e -> clearCanvas());
//
//        // Button to resize and export the canvas
//        Button resizeBtn = (Button) loader.getNamespace().get("resize-btn");
//        resizeBtn.setOnAction(e -> canvasResizer.resize());
//
//        Button exportBtn = (Button) loader.getNamespace().get("export-btn");
//        exportBtn.setOnAction(e -> canvasExporter.export(primaryStage, projectItem));
//
//        // Create the "Free Draw" and "Textbox" toggle buttons
//        freeDrawBtn.setSelected(true); // Start in Free Draw mode
//
//        HBox modeToggleButtonBox = new HBox(10, freeDrawBtn, textboxBtn);
//        HBox controlBox = new HBox(10);
//        VBox toolsRoot = new VBox();
//        VBox canvasRoot = new VBox();
//        Separator HBoxSeparator = new Separator();
//        HBoxSeparator.setMaxWidth(10);
//        HBoxSeparator.setOrientation(Orientation.VERTICAL);
//
//        Font defaultFont = Font.font("Verdana", 16);
//        graphicsContext.setFont(defaultFont);
//        String [] defaultInput = new String[]{"Hello World!"};
//        IntegerProperty sizeLabelProperty = new SimpleIntegerProperty(15);
//
//        /*===== Creating Text =====*/
//
//        /* Setting the Text */
//        TextField textField = new TextField("");
//        EventHandler<ActionEvent> setTextHandler = event -> defaultInput[0] = textField.getText();
//
//        textField.setOnAction(setTextHandler);
//        TextInputDialog textDialog = new TextInputDialog();
//
//        textDialog.setTitle("Input Text");
//        textDialog.setHeaderText("Please input text");
//
//        textDialog.setX(8);
//        textDialog.setY(8);
//
//        EventHandler<DialogEvent> getTextHandler = event -> defaultInput[0] = textDialog.getResult();
//        textDialog.setOnCloseRequest(getTextHandler);
//
//        /* Creating Text at a Specified Location */
//        EventHandler<MouseEvent> writeTextHandler = event -> {
//            if (!(isFreeDrawMode)) {
//                double xValue = event.getX();
//                double yValue = event.getY();
//                textDialog.showAndWait();
//                Color currentColor = (Color) graphicsContext.getFill();
//                AardText newText = new AardText(defaultInput[0], currentColor.toString(),
//                        graphicsContext.getFont(), xValue, yValue);
//                textArrayList.add(newText);
//                newText.draw(graphicsContext);
//            }
//        };
//        canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, writeTextHandler);
//
//        // canvasRoot.getChildren().add(canvas);
//        canvasRoot.getChildren().add(textField);
//
//        /*===== Changing and Adjusting Text-Related Settings on the Canvas =====*/
//
//        /* Changing Font Family */
//        System.out.println(Font.getFamilies());
//        final ComboBox<String> fontComboBox = new ComboBox<String>();
//        fontComboBox.getItems().addAll(
//                "Algerian",
//                "Arial",
//                "Cooper Black",
//                "Century Gothic",
//                "Verdana",
//                "Times New Roman"
//        );
//        fontComboBox.setValue("Verdana");
//        EventHandler<ActionEvent> changeFontHandler = new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                Font newFont = new Font(fontComboBox.getValue(), sizeLabelProperty.get());
//                graphicsContext.setFont(newFont);
//            }
//        };
//        fontComboBox.addEventFilter(ActionEvent.ACTION, changeFontHandler);
//        toolsRoot.getChildren().add(fontComboBox);
//
//        /* Changing Text Colour */
//        colorPicker.setValue(Color.BLACK);
//        EventHandler<ActionEvent> changeColorHandler = new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                graphicsContext.setFill(colorPicker.getValue());
//            }
//        };
//        colorPicker.addEventFilter(ActionEvent.ACTION, changeColorHandler);
//        toolsRoot.getChildren().add(colorPicker);
//
//        /* Setting Text Size */
//        Label sizeLabel = new Label("15");
//        sizeLabel.textProperty().bind(sizeLabelProperty.asString("Font size: %d"));
//        toolsRoot.getChildren().add(sizeLabel);
//
//        /* Increasing Text Size */
//        Button btIncreaseText = new Button("Text+");
//        EventHandler<MouseEvent> increaseFontHandler = new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                Font currentFont = graphicsContext.getFont();
//                Font newFont = Font.font(currentFont.getFamily(), currentFont.getSize() + 1);
//                sizeLabelProperty.set(sizeLabelProperty.get() + 1);
//                graphicsContext.setFont(newFont);
//            }
//        };
//        btIncreaseText.addEventFilter(MouseEvent.MOUSE_CLICKED, increaseFontHandler);
//        toolsRoot.getChildren().add(btIncreaseText);
//
//        /* Decreasing Text Size */
//        Button btDecreaseText = new Button("Text-");
//        EventHandler<MouseEvent> decreaseFontHandler = new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                Font currentFont = graphicsContext.getFont();
//                Font newFont = Font.font(currentFont.getFamily(), currentFont.getSize() - 1);
//                sizeLabelProperty.set(sizeLabelProperty.get() - 1);
//                graphicsContext.setFont(newFont);
//            }
//        };
//        btDecreaseText.addEventFilter(MouseEvent.MOUSE_CLICKED, decreaseFontHandler);
//        toolsRoot.getChildren().add(btDecreaseText);
//
//
//        // Set up the scene
//        Scene scene = new Scene(root, 1920, 1080);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//        controlBox.getChildren().addAll(colorPicker, clearBtn, resizeBtn, exportBtn);
//        controlBox.getChildren().addAll(toolsRoot, HBoxSeparator, canvasRoot, modeToggleButtonBox);
//        root.setCenter(canvas);
//        root.setTop(controlBox);


        // Event handler for drawing on the canvas

//    }
//}
