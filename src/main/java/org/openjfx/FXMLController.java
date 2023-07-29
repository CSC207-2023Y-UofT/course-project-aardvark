package org.openjfx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import text.AardText;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    @FXML
    private VBox projectsLayout;

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

    private  List<Project> projects() {
//      Temporary code to simulate project list
        List<Project> ls = new ArrayList<Project>();

        for (int i = 0; i < 15; i++) {
            Project project = new Project("Untitled Project");
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
    public void createNewProject(javafx.event.ActionEvent event) {
        switchToEditor(event);
    }

    @FXML
    public void switchToEditor(javafx.event.ActionEvent event){
        ArrayList<AardText> textArrayList = new ArrayList<AardText>();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Writing Text");
        stage.setResizable(false);

        HBox root = new HBox();
        VBox toolsRoot = new VBox();
        VBox canvasRoot = new VBox();
        Separator HBoxSeparator = new Separator();
        HBoxSeparator.setMaxWidth(10);
        HBoxSeparator.setOrientation(Orientation.VERTICAL);

        Canvas textCanvas = new Canvas(300, 300);
        GraphicsContext gc = textCanvas.getGraphicsContext2D();

        Font defaultFont = Font.font("Verdana", 15);
        gc.setFont(defaultFont);
        String [] defaultInput = {"ant"};
        IntegerProperty sizeLabelProperty = new SimpleIntegerProperty(15);

        /*===== Creating Text =====*/

        /* Setting the Text */
        TextField textField = new TextField("ant");
        EventHandler<ActionEvent> setTextHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                defaultInput[0] = textField.getText();
            }
        };
        textField.setOnAction(setTextHandler);

        TextInputDialog textDialog = new TextInputDialog();
        textDialog.setTitle("Input Text");
        textDialog.setHeaderText("Please write");

        textDialog.setX(10);
        textDialog.setY(10);

        EventHandler<DialogEvent> getTextHandler = new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent event) {
                defaultInput[0] = textDialog.getResult();
            }
        };
        textDialog.setOnCloseRequest(getTextHandler);

        /* Creating Text at a Specified Location */
        EventHandler<MouseEvent> writeTextHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double xValue = event.getX();
                double yValue = event.getY();
                textDialog.showAndWait();
                Color currentColor = (Color) gc.getFill();
                AardText newText = new AardText(defaultInput[0], currentColor.toString(), gc.getFont(), xValue, yValue);
                textArrayList.add(newText);
                newText.draw(gc);
            }
        };
        textCanvas.addEventFilter(MouseEvent.MOUSE_CLICKED, writeTextHandler);


        canvasRoot.getChildren().add(textCanvas);
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
                "Verdana"
        );
        fontComboBox.setValue("Verdana");
        EventHandler<ActionEvent> changeFontHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Font newFont = new Font(fontComboBox.getValue(), sizeLabelProperty.get());
                gc.setFont(newFont);
            }
        };
        fontComboBox.addEventFilter(ActionEvent.ACTION, changeFontHandler);
        toolsRoot.getChildren().add(fontComboBox);


        /* Changing Text Colour */
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        EventHandler<ActionEvent> changeColorHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gc.setFill(colorPicker.getValue());
            }
        };
        colorPicker.addEventFilter(ActionEvent.ACTION, changeColorHandler);
        toolsRoot.getChildren().add(colorPicker);

        /* Setting Text Size */
        Label sizeLabel = new Label("15");
        sizeLabel.textProperty().bind(sizeLabelProperty.asString("Font size: %d"));
        toolsRoot.getChildren().add(sizeLabel);

        /* Increasing Text Size */
        Button btIncreaseText = new Button("Text++");
        EventHandler<MouseEvent> increaseFontHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Font currentFont = gc.getFont();
                Font newFont = Font.font(currentFont.getFamily(), currentFont.getSize() + 1);
                sizeLabelProperty.set(sizeLabelProperty.get() + 1);
                gc.setFont(newFont);
            }
        };
        btIncreaseText.addEventFilter(MouseEvent.MOUSE_CLICKED, increaseFontHandler);
        toolsRoot.getChildren().add(btIncreaseText);

        /* Decreasing Text Size */
        Button btDecreaseText = new Button("Text--");
        EventHandler<MouseEvent> decreaseFontHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Font currentFont = gc.getFont();
                Font newFont = Font.font(currentFont.getFamily(), currentFont.getSize() - 1);
                sizeLabelProperty.set(sizeLabelProperty.get() - 1);
                gc.setFont(newFont);
            }
        };
        btDecreaseText.addEventFilter(MouseEvent.MOUSE_CLICKED, decreaseFontHandler);
        toolsRoot.getChildren().add(btDecreaseText);

        root.getChildren().add(toolsRoot);
        root.getChildren().add(HBoxSeparator);
        root.getChildren().add(canvasRoot);
        Scene sc = new Scene(root);
        stage.setScene(sc);
        stage.show();
    }
}
