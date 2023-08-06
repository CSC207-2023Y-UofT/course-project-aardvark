package aardvark;

import controllers.EditorController;
import controllers.ProjectItemController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import models.*;
import org.json.simple.parser.ParseException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.awt.geom.Point2D;
import models.FreeDrawLine;
import models.Project;
import user_features.User;
import user_features.UserDSGateway;

import models.VisualElement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import user_features.User;
import user_features.UserDSGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.*;

/**
 The MainAppRouter class is the controller class responsible for handling the main application flow and navigation.
>>>>>>> 65654ae89caa38bf91ea988fb6abbb4973bde9e9

 It implements the Initializable interface to initialize the JavaFX components defined in the associated FXML file.
 */
public class MainAppRouter implements Initializable {
    @FXML
    private VBox projectsLayout;
    @FXML
    private TextField emailText;
    @FXML
    private TextField nameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private PasswordField repeatPasswordText;
    @FXML
    private Label nameError;
    @FXML
    private TextField textField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField newProjectName;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Color currentColor = Color.BLACK;

    private Stage stage;
    private Scene scene;
    private Parent root;

    static public User currUser = null;
    static private Project currProj = null;

    @FXML
    private Label name;
    @FXML
    private Button delete;

    /**
     Initializes the MainAppRouter by loading the list of projects and populating the projectsLayout VBox with

     ProjectItemController instances for each project.

     @param location The URL location of the FXML file.
     @param resources The ResourceBundle.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (currUser == null)
            return;

        UserDSGateway gateway = new UserDSGateway();
        List<Project> projects = gateway.projectsList(currUser);

        for (int i=0; i<projects.size(); i++) {
            FXMLLoader fxmlloader = new FXMLLoader();
            fxmlloader.setLocation(getClass().getResource("/aardvark/project_item.fxml"));

            try {
                HBox hbox = fxmlloader.load();
                ProjectItemController pic = fxmlloader.getController();
                pic.setData(projects.get(i).getName(), projects.get(i).getDate());
                projectsLayout.getChildren().add(hbox);
            } catch (IOException e) {
                System.out.println("Something went wrong, FXML Load" + e);
            } catch (NullPointerException e) {
                System.out.println("Something went wrong, FXML Load" + e);
            }
        }
    }


    /**
    Displays an error alert dialog with the specified error message.
    @param message The error message to display in the alert dialog.
     */
    @FXML
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     Switches the application's current scene to the sign-up page.

     @param event The ActionEvent that triggers the method, a button click.

     @throws IOException If an error occurs while loading the sign-up page FXML file.
     */
    @FXML
    public void switchToSignUp(javafx.event.ActionEvent event) throws IOException {
        Parent newPage = FXMLLoader.load(getClass().getResource("/aardvark/signup.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    /**
     Handles the sign-up process when the sign-up button is clicked.

     Creates a new user based on the provided information and adds it to the data file if the input is valid.

     @param event The ActionEvent that triggers the method, a button click.

     @throws IOException If an error occurs while loading the next page or interacting with the data file.
     */
    @FXML
    public void signUp(javafx.event.ActionEvent event) throws IOException {
        // Get the user input from the form fields
        String password = passwordText.getText();
        String repeatPassword = repeatPasswordText.getText();
        String email = emailText.getText();
        String name = nameText.getText();


        // Create a new instance of the UserDSGateway to interact with the user data store
        UserDSGateway gateway = new UserDSGateway();


        // Validate the input and proceed accordingly
        if (!password.equals("") && !email.equals("")) {
            if (password.equals(repeatPassword) && !gateway.checkUserExists(email)) {
                // Create a new User object using the provided information
                currUser = gateway.userRegister(name, email, password);
                // If passwords match and the user does not already exist, add the user and save changes
                gateway.addUser(currUser);
                gateway.saveChanges();
                switchToProjects(event); // Switch to the projects page after successful sign-up
            } else if (!password.equals(repeatPassword) && !gateway.checkUserExists(email)) {
                // If passwords don't match, show an error alert
                showErrorAlert("Passwords don't match, please try again!");
            } else if (gateway.checkUserExists(email)) {
                // If user already exists, show an error alert
                showErrorAlert("User already exists, sign in.");
            }
        }
        else {
            // If email or password fields are blank, show an error alert
            showErrorAlert("Email and password cannot be left blank, please try again.");
        }
    }

    /**
     Switches the scene to the sign-in page when the corresponding button is clicked.

     @param event The ActionEvent that triggers the method, a button click.

     @throws IOException If an error occurs while loading the sign-in page.
     */
    @FXML
    public void switchToSignIn(javafx.event.ActionEvent event) throws IOException {
        Parent newPage = FXMLLoader.load(getClass().getResource("signin.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    /**
     Attempts to sign in the user with the provided email and password.

     If the provided email and password are valid and match an existing user,
     the scene will be switched to the projects page.

     If the email does not correspond to an existing user,
     an error alert will be displayed prompting the user to sign up.

     If the password is incorrect, an error alert will be displayed
     asking the user to try again.

     If any of the email or password fields are left blank,
     an error alert will be displayed asking the user to fill them.

     @param event The ActionEvent that triggers the method, usually a button click.

     @throws IOException If an error occurs while switching to the projects page or loading the error alert.
     */
    @FXML
    public void signIn(javafx.event.ActionEvent event) throws IOException {
        // Get the email and password from the input fields
        String email = textField.getText();
        String password = passwordField.getText();


        // Create a UserDSGateway instance to access user data
        UserDSGateway gateway = new UserDSGateway();


        // Check if the email and password fields are not empty
        if (!password.equals("") && !email.equals("")) {
            // Check if the user exists and the password is correct
            if (gateway.checkUserExists(email) && gateway.checkPassword(email, password)) {
                // log in the user using the provided email and password
                currUser = gateway.userLogin(email, password);
                // Switch to the projects page
                switchToProjects(event);
            } else if (!gateway.checkUserExists(email)) {
                // Display an error alert if the user does not exist
                showErrorAlert("User does not exists, sign up.");
            } else if (!gateway.checkPassword(email, password)) {
                // Display an error alert if the password is incorrect
                showErrorAlert("Password is incorrect, please try again.");
            }
        }
        else {
            // Display an error alert if any of the email or password fields are left blank
            showErrorAlert("Fields cannot be left blank, please try again.");
        }
    }

    /**
     Switches the current scene to the "projects.fxml" view.

     @param event The ActionEvent that triggers the method, usually a button click.

     @throws IOException If an error occurs while loading the "projects.fxml" view or setting the new scene.
     */
    @FXML
    public void switchToProjects(javafx.event.ActionEvent event) throws IOException {
        if (currProj != null) {

            UserDSGateway gateway = new UserDSGateway();

            gateway.updateProject(currUser, currProj);
            gateway.saveChanges();


            currProj = null;

        }

        Parent newPage = FXMLLoader.load(getClass().getResource("projects.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    /**
     Switches the current scene to the "new_project.fxml" view.

     @param event The ActionEvent that triggers the method, usually a button click or a menu item selection.

     @throws IOException If an error occurs while loading the "new_project.fxml" view or setting the new scene.
     */
    @FXML
    public void switchToNameProject(javafx.event.ActionEvent event) throws IOException {
        Parent newPage = FXMLLoader.load(getClass().getResource("new_project.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    /**
     Creates a new project with the name given name from the name project text field,
     and then switches to the editor view for the newly created project.

     @param event The ActionEvent that triggers the method, usually a button click or a menu item selection.

     @throws IOException If an error occurs while loading the editor view or setting the new scene.
     */
    @FXML
    public void createNewProject(javafx.event.ActionEvent event) throws IOException {

        currProj = new Project(newProjectName.getText());

        UserDSGateway gateway = new UserDSGateway();

        if (!gateway.checkUniqueProject(currUser, newProjectName.getText())){
            showErrorAlert("Project names must be unique!");
            return;
        }

        gateway.addNewProject(currUser, currProj);
        gateway.saveChanges();

        switchToEditor(event, currProj);
    }

    /**
     Deletes the selected project.

     @param event The ActionEvent that triggers the method, usually a button click or a menu item selection.

     @throws IOException If an error occurs while deleting the project or handling the project deletion process.
     */
    @FXML
    public void deleteProject(javafx.event.ActionEvent event) throws IOException{
        System.out.println("deleted");
    }

    /**
     Switches the scene to the editor view when triggered by a button click.

     @param event The ActionEvent that triggers the method.

     @param project The Project object representing the project to be opened in the editor.

     @throws IOException If there is an error while loading the editor.fxml file.
     */
    @FXML
    public void switchToEditor(javafx.event.ActionEvent event, Project project) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editor.fxml"));
        fxmlLoader.setController(new EditorController(project, ((Node) event.getSource()).getScene()));
        Parent newPage = fxmlLoader.load();
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }


    static public void openEditor(ActionEvent event, String projName) {

        UserDSGateway gateway = new UserDSGateway();
        currProj= gateway.projectFromJSON(currUser, projName);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainAppRouter.class.getResource("editor.fxml"));
            fxmlLoader.setController(new EditorController(currProj, ((Node) event.getSource()).getScene()));
            Parent newPage = fxmlLoader.load();
            ((Node) event.getSource()).getScene().setRoot(newPage);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    static public void deleteButton(javafx.event.ActionEvent event) throws IOException{
        Parent newPage = FXMLLoader.load(MainAppRouter.class.getResource("projects.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }
}
