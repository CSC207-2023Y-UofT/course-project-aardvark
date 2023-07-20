package org.openjfx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectItemController implements Initializable {

    @FXML
    private Label name;

    @FXML
    private Label date;

    @FXML
    private Button delete;

    @FXML
    private Button project;

    public void setData(Project project) {
        name.setText(project.getName());
        date.setText(project.getDate());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
