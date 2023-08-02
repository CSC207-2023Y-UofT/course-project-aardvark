package org.openjfx;

import controllers.EditorController;
import controllers.ProjectItemController;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import user_features.UserRegisterUseCase;
import user_features.UserLoginUseCase;

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

    private  List<Project> projects() {
//      Temporary code to simulate project list
        List<models.Project> ls = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            models.Project project = new models.Project("Untitled Project");
            ls.add(project);
        }

        return ls;
    };


    @FXML
    public void switchToSignUp(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML TextField emailText;
    @FXML TextField nameText;
    @FXML PasswordField passwordText;
    @FXML PasswordField repeatPasswordText;
    @FXML
    public void signUp(javafx.event.ActionEvent event) throws IOException
    /*
     * Handles the signUp button and creates a new user register use case which in turn creates a new user and adds
     * it to the data file.
     */
    {

        String password = passwordText.getText();
        String repeatPassword = repeatPasswordText.getText();
        String email = emailText.getText();
        String name = nameText.getText();

        UserRegisterUseCase register = new UserRegisterUseCase(name, email,
                password);
        if (password.equals(repeatPassword) && !register.checkExists()){

            register.addUser();
            switchToProjects(event);
        }
        else if(!password.equals(repeatPassword) && !register.checkExists()){
            showErrorAlert("Passwords don't match, please try again!");
        }
        else if(register.checkExists()){
            showErrorAlert("User already exists, sign in");
        }
    }

    @FXML
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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


    @FXML TextField textField;
    @FXML PasswordField passwordField;
    @FXML
    public void signIn(javafx.event.ActionEvent event) throws IOException {

        String email = textField.getText();
        String password = passwordField.getText();

        UserLoginUseCase loginUser = new UserLoginUseCase(email, password);
        if (loginUser.checkExists() && loginUser.checkPassword(password)){
            switchToProjects(event);
        }
        else if(!loginUser.checkExists()){
            showErrorAlert("User does not exists, please sign up.");
        }
        else if (!loginUser.checkPassword(password)){
            showErrorAlert("Password is incorrect, please try again.");
        }

    }
    @FXML
    public void switchToProjects(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("projects.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false); //what is this for?
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    Text nameError;

    @FXML
    public void switchToNameProject(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("new_project.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML TextField newProjectName;
    @FXML
    public void createNewProject(javafx.event.ActionEvent event) throws IOException {
        Project project = new Project(newProjectName.getText());
        EditorController.setProject(project);
        switchToEditor(event);
    }

    @FXML
    public void switchToEditor(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("editor.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        // Editor editor = new Editor();
        stage.setResizable(true);
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setX(0);
        stage.setY(0);
        // editor.start(stage);
        stage.show();
    }
}
