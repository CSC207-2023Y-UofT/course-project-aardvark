package controllers;

import aardvark.MainAppRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import models.Project;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 The ProjectItemController class is a controller class for a project item in the application's user interface.

 It implements the Initializable interface to initialize the controller after its root element has been processed.
 */
public class ProjectItemController implements Initializable {

    @FXML
    private Label name;

    @FXML
    private Label date;

    @FXML
    private Button delete;

    @FXML
    private Button project;

    /**
     Set the data for the project item.

     @param project The Project object representing the project to be displayed in the item.
     */
    public void setData(Project project) {
        name.setText(project.getName());
        date.setText(project.getDate().toString());
    }

    /**
     Initializes the controller after its root element has been processed.

     This method is called automatically when the corresponding FXML file is loaded.

     @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @FXML
    public void openProject(ActionEvent event) {
        MainAppRouter.openEditor(event, name.getText());
    }
    @FXML
    public void deleteButton(ActionEvent event) throws IOException{
        String filePath = "src/main/java/user_features/DataModel.json";

        try {
            JSONParser jsonParser = new JSONParser();
            Object jsonObject = jsonParser.parse(new FileReader(filePath));

            // Cast the parsed object to a JSONObject
            JSONObject users = (JSONObject) jsonObject;

            // Locate the user's entry by email
            JSONObject user = (JSONObject) users.get(MainAppRouter.currUser.getEmail());
            JSONArray projects = (JSONArray) user.get("Projects");

            // Remove the specified project by project name
            for (int i = 0; i < projects.size(); ++i) {
                JSONObject project = (JSONObject) projects.get(i);
                if (name.getText().equals(project.get("ProjectName"))) {
                    projects.remove(i);
                    break;
                }
            }

            // Write the updated JSON content back to the file
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(users.toJSONString());
            }

            System.out.println("Project removed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        MainAppRouter.deleteButton(event);
    }
}
