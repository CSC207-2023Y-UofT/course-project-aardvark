package org.openjfx;

import controllers.ProjectItemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Project;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    @FXML
    private VBox projectsLayout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Project> projects = new ArrayList<>(projects());
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

    private List<Project> projects() {
//      Temporary code to simulate project list
        List<Project> ls = new ArrayList<Project>();

        for (int i = 0; i < 15; i++) {
            Project project = new Project();
            project.setName("Untitled Project");
            project.setDate("Modified July 20");
            ls.add(project);
        }

        return ls;
    };


    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void switchToSignUp(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
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
    @FXML
    public void switchToProjects(javafx.event.ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("projects.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}