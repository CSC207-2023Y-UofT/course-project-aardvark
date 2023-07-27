package org.openjfx;

import controllers.CanvasExportController;
import controllers.CanvasResizerController;
import controllers.ProjectItemController;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Project;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    @FXML
    private VBox projectsLayout;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Color currentColor = Color.BLACK;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Project> projects = new ArrayList<>(projects());
        for (int i=0; i<projects.size(); i++) {
            FXMLLoader fxmlloader = new FXMLLoader();
            fxmlloader.setLocation(getClass().getResource("project_item.fxml"));

            try {
                HBox hbox = fxmlloader.load();
                ProjectItemController pic = fxmlloader.getController();
                pic.setData(projects.get(i));
                projectsLayout.getChildren().add(hbox);
            } catch (IOException e) {
                System.out.println("Something went wrong, FXML Load");
            } catch (NullPointerException e) {
                System.out.println("Something went wrong, FXML Load");
            }
        }
    }

    private List<Project> projects() {
//      Temporary code to simulate project list
        List<Project> ls = new ArrayList<Project>();

        for (int i = 0; i < 15; i++) {
            Project project = new Project("Hello World");
            project.setProjectName("Untitled Project");
            ls.add(project);
        }

        return ls;
    };


    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void switchToSignUp(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToSignIn(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("signin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToProjects(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("projects.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToEditor2(javafx.event.ActionEvent event) throws IOException {
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext graphicsContext;
        Color currentColor = Color.BLACK;

        // Create canvas and graphics context
        canvas = new Canvas(800, 600);
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(2.0);

        // Set up a new Project
        Project projectItem = new Project("Hello World");


        // Create a resize controller and set the name of the project to the
        CanvasResizerController canvasResizer = new CanvasResizerController(canvas);
        primaryStage.setTitle(projectItem.getName());

        // Create a export controller
        CanvasExportController canvasExporter = new CanvasExportController(canvas, projectItem);


        // Color picker to allow selecting custom colors
        ColorPicker colorPicker = new ColorPicker(currentColor);
        colorPicker.setOnAction(e -> setCurrentColor(colorPicker.getValue()));

        // Clear button to reset the canvas
        Button clearBtn = new Button("Clear Canvas");
        clearBtn.setOnAction(e -> clearCanvas());

        // BorderPane to arrange the canvas and control buttons
        BorderPane root = new BorderPane();

        // Button to resize the canvas
        Button resizeBtn = new Button("Resize Canvas");
        resizeBtn.setOnAction(e -> canvasResizer.resize());
        root.setCenter(canvas);

        Button exportBtn = new Button("Export as PNG");
        Canvas finalCanvas = canvas;
        exportBtn.setOnAction(e -> {
            // Show a file chooser to get the save location and name for the PNG image
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export as PNG");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Image (*.png)", "*.png"));
            File file = fileChooser.showSaveDialog(primaryStage);

            if (file != null) {
                try {
                    // Convert the canvas to a writable image
                    WritableImage writableImage = new WritableImage((int) finalCanvas.getWidth(), (int) finalCanvas.getHeight());
                    finalCanvas.snapshot(null, writableImage);

                    // Write the writable image to the specified file as a PNG image
                    ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
                } catch (IOException ex) {
                    // Handle the exception if there was an error saving the image
                    projectItem.showErrorDialog("Error exporting as PNG. Please try again.");
                }
            }
        });

        // HBox to hold the color picker, color buttons, and resize button
        HBox controlBox = new HBox(10);
        controlBox.getChildren().addAll(colorPicker, clearBtn, resizeBtn, exportBtn);
        root.setCenter(canvas);
        root.setTop(controlBox);

        // Set up the scene
        Scene scene = new Scene(root, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.show();


        // Event handler for drawing on the canvas
        canvas.setOnMousePressed(e -> {
            graphicsContext.beginPath();
            graphicsContext.moveTo(e.getX(), e.getY());
            graphicsContext.setStroke(currentColor);
            graphicsContext.stroke();
        });

        canvas.setOnMouseDragged(e -> {
            graphicsContext.lineTo(e.getX(), e.getY());
            graphicsContext.stroke();
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
    }

    // Helper method to clear the canvas
    private void clearCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
