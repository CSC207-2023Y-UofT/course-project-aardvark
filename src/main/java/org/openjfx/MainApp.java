package org.openjfx;

import controllers.EditorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;



public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("signin.fxml"));
        EditorController.primaryStage = stage;
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        Image icon = new Image("images/icon.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setMinWidth(400);
        stage.setMinHeight(400);

        stage.setTitle("Aardvark");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
