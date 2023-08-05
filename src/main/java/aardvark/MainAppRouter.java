package aardvark;

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
import javafx.stage.Stage;
import models.Project;
import javafx.scene.control.Alert;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import user_features.UserRegisterUseCase;
import user_features.UserLoginUseCase;

public class MainAppRouter implements Initializable {

    @FXML
    private VBox projectsLayout;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Color currentColor = Color.BLACK;

    private Stage stage;
    private Scene scene;
    private Parent root;

    static private String currEmail = "";
    static private Project currProj = null;
    static private UserRegisterUseCase register = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (currEmail.isEmpty())
            return;

        List<Project> projects = new ArrayList<>();



        String filePath = "src/main/java/user_features/DataModel.json";
        JSONParser jsonParser = new JSONParser();

        try {
            // Read the JSON file and parse it
            Object obj = jsonParser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;

            JSONObject jsonUser = (JSONObject)jsonObject.get(currEmail);

            // Get the "Projects" array
            JSONArray projectsArray = (JSONArray) jsonUser.get("Projects");

            for (Object jsonElement : projectsArray) {
                JSONObject JSONProj = (JSONObject) jsonElement;
                projects.add(new Project((String)JSONProj.get("ProjectName")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




        for (int i=0; i<projects.size(); i++) {
            FXMLLoader fxmlloader = new FXMLLoader();
            fxmlloader.setLocation(getClass().getResource("project_item.fxml"));

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

    @FXML
    public void switchToSignUp(javafx.event.ActionEvent event) throws IOException {
        Parent newPage = FXMLLoader.load(getClass().getResource("signup.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
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

        currEmail = email;

        register = new UserRegisterUseCase(name, email,
                password);

        if (!password.equals("") && !email.equals("")) {
            if (password.equals(repeatPassword) && !register.checkExists()) {

                register.addUser();
                switchToProjects(event);
            } else if (!password.equals(repeatPassword) && !register.checkExists()) {
                showErrorAlert("Passwords don't match, please try again!");
            } else if (register.checkExists()) {
                showErrorAlert("User already exists, sign in.");
            }
        }
        else{
            showErrorAlert("Email and password cannot be left blank, please try again.");
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
        Parent newPage = FXMLLoader.load(getClass().getResource("signin.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    public void switchToProjects(javafx.event.ActionEvent event) throws IOException {
        if (currProj != null) {


            currProj = null;
        }

        Parent newPage = FXMLLoader.load(getClass().getResource("projects.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    public void switchToNameProject(javafx.event.ActionEvent event) throws IOException {
        Parent newPage = FXMLLoader.load(getClass().getResource("new_project.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    public void switchToEditor(javafx.event.ActionEvent event, Project project) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editor.fxml"));
        fxmlLoader.setController(new EditorController(project, ((Node) event.getSource()).getScene()));
        Parent newPage = fxmlLoader.load();
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    Label nameError;

    @FXML TextField textField;
    @FXML PasswordField passwordField;
    @FXML
    public void signIn(javafx.event.ActionEvent event) throws IOException {

        String email = textField.getText();
        String password = passwordField.getText();

        currEmail = email;

        UserLoginUseCase loginUser = new UserLoginUseCase(email, password);

        if (!password.equals("") && !email.equals("")) {
            if (loginUser.checkExists() && loginUser.checkPassword()) {
                switchToProjects(event);
            } else if (!loginUser.checkExists()) {
                showErrorAlert("User does not exists, sign up.");
            } else if (!loginUser.checkPassword()) {
                showErrorAlert("Password is incorrect, please try again.");
            }
        }
        else {
            showErrorAlert("Fields cannot be left blank, please try again.");
        }

    }

    @FXML TextField newProjectName;
    @FXML
    public void createNewProject(javafx.event.ActionEvent event) throws IOException {
        currProj = new Project(newProjectName.getText());

        String filePath = "src/main/java/user_features/DataModel.json";
        JSONParser jsonParser = new JSONParser();

        try {
            // Read the JSON file and parse it
            Object obj = jsonParser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;

            JSONObject jsonUser = (JSONObject)jsonObject.get(currEmail);

            // Get the "Projects" array
            JSONArray projectsArray = (JSONArray) jsonUser.get("Projects");
            projectsArray.add(currProj.toDict());

            // Write the updated data back to the JSON file
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(jsonObject.toJSONString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        switchToEditor(event, currProj);
    }

    @FXML
    public void deleteProject(javafx.event.ActionEvent event) throws IOException{
        System.out.println("deleted");
    }
}
