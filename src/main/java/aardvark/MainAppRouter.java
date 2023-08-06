package aardvark;

import controllers.EditorController;
import controllers.ProjectItemController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.json.simple.parser.ParseException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class MainAppRouter implements Initializable {

    @FXML
    private VBox projectsLayout;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Color currentColor = Color.BLACK;

    private Stage stage;
    private Scene scene;
    private Parent root;

    static public User currUser = null;
    static private Project currProj = null;
    static private UserDSGateway userGate = null;
    @FXML
    private Label name;
    @FXML
    private Button delete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (currUser == null)
            return;

        List<Project> projects = new ArrayList<>();



        String filePath = "src/main/java/user_features/DataModel.json";
        JSONParser jsonParser = new JSONParser();

        try {
            // Read the JSON file and parse it
            Object obj = jsonParser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;

            JSONObject jsonUser = (JSONObject)jsonObject.get(currUser.getEmail());

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

    @FXML
    public void switchToSignUp(javafx.event.ActionEvent event) throws IOException {
        Parent newPage = FXMLLoader.load(getClass().getResource("/aardvark/signup.fxml"));
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

        currUser = new User(name, email, password);

        UserDSGateway gateway = new UserDSGateway();
        User newUser = gateway.userRegister(name, email, password);

        if (!password.equals("") && !email.equals("")) {
            if (password.equals(repeatPassword) && !gateway.checkUserExists(newUser)) {

                gateway.addUser(newUser);
                gateway.saveChanges();
                switchToProjects(event);
            } else if (!password.equals(repeatPassword) && !gateway.checkUserExists(newUser)) {
                showErrorAlert("Passwords don't match, please try again!");
            } else if (gateway.checkUserExists(newUser)) {
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
            String filePath = "src/main/java/user_features/DataModel.json";
            JSONParser jsonParser = new JSONParser();

            try {
                JSONObject rootObject = (JSONObject) jsonParser.parse(new FileReader((filePath)));

                JSONObject userA = (JSONObject) rootObject.get(currUser.getEmail());
                JSONArray projectsArray = (JSONArray) userA.get("Projects");
                for (Object e : projectsArray) {
                    JSONObject o = (JSONObject) e;
                    if (((String)o.get("ProjectName")).equals(currProj.getName())) {
                        o.put("VisualElements", currProj.idfk());
                        break;
                    }
                }

                String updatedJsonString = rootObject.toString();
                try (FileWriter fileWriter = new FileWriter(filePath)) {
                    fileWriter.write(updatedJsonString);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

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

        currUser = new User("", email, password);

        UserDSGateway gateway = new UserDSGateway();
        User loginUser = gateway.userLogin(email, password);

        if (password.equals("") && email.equals("")) {
            showErrorAlert("Fields cannot be left blank, please try again.");
        }
        if (gateway.checkUserExists(loginUser) && gateway.checkPassword(email, password)) {
                switchToProjects(event);
        }
        else if (!gateway.checkUserExists(loginUser)) {
            showErrorAlert("User does not exists, sign up.");
        }
        else if (!gateway.checkPassword(email, password)) {
            showErrorAlert("Password is incorrect, please try again.");
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

            JSONObject jsonUser = (JSONObject)jsonObject.get(currUser.getEmail());

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

    static public void openEditor(ActionEvent event, String projName) {
        String filePath = "src/main/java/user_features/DataModel.json";
        JSONParser jsonParser = new JSONParser();

        try {
            // Read the JSON file and parse it
            Object obj = jsonParser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;

            JSONObject jsonUser = (JSONObject)jsonObject.get(currUser.getEmail());

            // Get the "Projects" array
            JSONArray projectsArray = (JSONArray) jsonUser.get("Projects");
            for (Object proj : projectsArray)
            {
                JSONObject jsonobj = (JSONObject) proj;

                if (jsonobj.get("ProjectName").equals(projName)) {
                    JSONArray elems = (JSONArray)jsonobj.get("VisualElements");
                    Date date = new Date((String)jsonobj.get("UpdateDate"));
                    long x = (Long)jsonobj.get("Width");
                    long y = (Long)jsonobj.get("Height");

                    List<VisualElement> lst = new ArrayList<>();

                    for (Object o : elems) {
                        JSONObject jo = (JSONObject) o;
                        String type = (String) jo.get("Name");
                        if (type.equals("FreeDrawLine")) {
                            lst.add(FreeDrawLine.fromDict(jo));
                        }
                    }

                    Project project = new Project(projName, lst, date, (int)x, (int)y);

                    currProj = project;

                    FXMLLoader fxmlLoader = new FXMLLoader(MainAppRouter.class.getResource("editor.fxml"));
                    fxmlLoader.setController(new EditorController(project, ((Node) event.getSource()).getScene()));
                    Parent newPage = fxmlLoader.load();
                    ((Node) event.getSource()).getScene().setRoot(newPage);

                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void deleteButton(javafx.event.ActionEvent event) throws IOException{
        Parent newPage = FXMLLoader.load(MainAppRouter.class.getResource("projects.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }
}
