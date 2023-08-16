package controllers;

import aardvark.MainAppRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import user_features.UserDSGateway;

import java.io.IOException;
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

     @param n The name of the project
     @param d the updated date of the project
     */
    public void setData(String n, String d) {
        name.setText(n);
        date.setText(d);
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

        UserDSGateway gateway = new UserDSGateway();

        gateway.deleteProject(MainAppRouter.currUser, name.getText());
        gateway.saveChanges();

        MainAppRouter.deleteButton(event);
    }
}
