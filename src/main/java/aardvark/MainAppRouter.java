package aardvark;

import controllers.EditorController;
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
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.AardWritableImage;
import models.Project;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import user_features.User;
import user_features.UserDSGateway;

import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 The MainAppRouter class is the controller class responsible for handling the main application flow and navigation.

 It implements the Initializable interface to initialize the JavaFX components defined in the associated FXML file.
 */
public class MainAppRouter implements Initializable {
    @FXML
    public MenuButton createProjectBtn;
    @FXML
    public MenuItem newProjectBtn;
    @FXML
    public MenuItem openBtn;
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

    /**
     Initializes the MainAppRouter by loading the list of projects and populating the projectsLayout VBox with

     ProjectItemController instances for each project.

     @param location The URL location of the FXML file.
     @param resources The ResourceBundle.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<models.Project> projects = new ArrayList<>(projects());
        for (int i=0; i<projects.size(); i++) {
            FXMLLoader fxmlloader = new FXMLLoader();
            fxmlloader.setLocation(getClass().getResource("/aardvark/project_item.fxml"));

            try {
                HBox hbox = fxmlloader.load();
                ProjectItemController pic = fxmlloader.getController();
                pic.setData(projects.get(i));
                projectsLayout.getChildren().add(hbox);
            } catch (IOException e) {
                System.out.println("Something went wrong, FXML Load" + e);
            } catch (NullPointerException e) {
                System.out.println("Something went wrong, FXML Load" + e);
            }
        }
    }

    //  Temporary code to simulate project list
    private List<Project> projects() {

        List<models.Project> ls = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            models.Project project = new models.Project("Untitled Project");
            ls.add(project);
        }

        return ls;
    };

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

        // Create a new User object using the provided information
        User newUser = gateway.UserRegister(name, email, password);

        // Validate the input and proceed accordingly
        if (!password.equals("") && !email.equals("")) {
            if (password.equals(repeatPassword) && !gateway.checkUserExists(newUser)) {
                // If passwords match and the user does not already exist, add the user and save changes
                gateway.addUser(newUser);
                gateway.saveChanges();
                switchToProjects(event); // Switch to the projects page after successful sign-up
            } else if (!password.equals(repeatPassword) && !gateway.checkUserExists(newUser)) {
                // If passwords don't match, show an error alert
                showErrorAlert("Passwords don't match, please try again!");
            } else if (gateway.checkUserExists(newUser)) {
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

        // Attempt to log in the user using the provided email and password
        User loginUser = gateway.UserLogin(email, password);

        // Check if the email and password fields are not empty
        if (!password.equals("") && !email.equals("")) {
            // Check if the user exists and the password is correct
            if (gateway.checkUserExists(loginUser) && gateway.checkPassword(email, password)) {
                // Switch to the projects page
                switchToProjects(event);
            } else if (!gateway.checkUserExists(loginUser)) {
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

    @FXML
    public void switchToProjects(javafx.event.ActionEvent event) throws IOException {
        Parent newPage = FXMLLoader.load(getClass().getResource("projects.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    public void switchToNameProject(javafx.event.ActionEvent event) throws IOException {
        Parent newPage = FXMLLoader.load(getClass().getResource("new_project.fxml"));
        ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow().getScene().setRoot(newPage);
    }

    @FXML
    public void createNewProject(javafx.event.ActionEvent event) throws IOException {
        Project project = new Project(newProjectName.getText());
        switchToEditor(event, project);
    }

    @FXML
    public void deleteProject(javafx.event.ActionEvent event) throws IOException{
        System.out.println("deleted");
    }

    @FXML
    private void onOpenProjectClicked(javafx.event.ActionEvent event) {
        // This method will be called when "Open" is clicked from the dropdown menu
        // Implement the logic to open the file explorer and load a PNG file.

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open PNG Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(createProjectBtn.getScene().getWindow());

        if (selectedFile != null) {
            // Load the PNG image and create a WritableImage from it
            try {
                BufferedImage bufferedImage = ImageIO.read(selectedFile);
                WritableImage fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
                AardWritableImage writableImage = new AardWritableImage((int) Math.ceil(fxImage.getWidth()),
                        (int) Math.ceil(fxImage.getHeight()));
                SwingFXUtils.toFXImage(bufferedImage, writableImage);

                switchToEditor(event, new Project(selectedFile.getName()), writableImage);

            } catch (IOException e) {
                // Handle the exception if the file cannot be read or is not a valid PNG image.
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void switchToEditor(javafx.event.ActionEvent event, Project project) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editor.fxml"));
        fxmlLoader.setController(new EditorController(project, ((Node) event.getSource()).getScene()));
        Parent newPage = fxmlLoader.load();
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    public void switchToEditor(javafx.event.ActionEvent event, Project project,
                               AardWritableImage fxImage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editor.fxml"));
        fxmlLoader.setController(new EditorController(project, ((MenuItem) event.getSource()).getParentPopup().
                getOwnerWindow().getScene(), fxImage));
        Parent newPage = fxmlLoader.load();
        ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow().getScene().setRoot(newPage);
    }
}
