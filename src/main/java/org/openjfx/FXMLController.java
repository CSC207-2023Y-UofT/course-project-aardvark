package org.openjfx;

import controllers.EditorController;
import controllers.ProjectItemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Project;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class FXMLController implements Initializable {

    @FXML
    private VBox projectsLayout;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Color currentColor = Color.BLACK;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<models.Project> projects = new ArrayList<>(projects());
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

    //  Temporary code to simulate project list
    private  List<Project> projects() {
       
        List<models.Project> ls = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            models.Project project = new models.Project("Untitled Project");
            ls.add(project);
        }

        return ls;
    };


    @FXML
    public void switchToSignUp(javafx.event.ActionEvent event) throws IOException {
        Parent newPage = FXMLLoader.load(getClass().getResource("signup.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    public void switchToSignIn(javafx.event.ActionEvent event) throws IOException {
        Parent newPage = FXMLLoader.load(getClass().getResource("signin.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    public void switchToProjects(javafx.event.ActionEvent event) throws IOException {
        Parent newPage = FXMLLoader.load(getClass().getResource("projects.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    public void switchToNameProject(javafx.event.ActionEvent event) throws IOException {
        Parent newPage = FXMLLoader.load(getClass().getResource("new_project.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    public void switchToEditor(javafx.event.ActionEvent event) throws IOException {
        Parent newPage = FXMLLoader.load(getClass().getResource("editor.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    Label nameError;

    @FXML TextField textField;
    @FXML PasswordField passwordField;
    @FXML
    public void signIn(javafx.event.ActionEvent event) throws IOException {
        if (textField.getText().equals("") && passwordField.getText().equals("")) {
            switchToProjects(event);
        }
    }

    @FXML TextField newProjectName;
    @FXML
    public void createNewProject(javafx.event.ActionEvent event) throws IOException {
        Project project = new Project(newProjectName.getText());
        EditorController.setProject(project);
        switchToEditor(event);
    }


}
