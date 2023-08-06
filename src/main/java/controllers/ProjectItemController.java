package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import models.Project;

import java.net.URL;
import java.util.ResourceBundle;


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
}
