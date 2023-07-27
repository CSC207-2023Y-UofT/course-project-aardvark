package org.openjfx;

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
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        Image icon = new Image("images/icon.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
//        stage.setMinWidth(800);
//        stage.setMinHeight(800);

        stage.setTitle("Aardvark");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);

        // Boiler plate for Tests, delete later
        for (int i = 1; i <= 100; i++) {
            System.out.println(convert(i));
        }
    }

    // Boiler plate for Tests, delete later
    public static String convert(int decide) {
        if (decide % 15 == 0) {
            return "tutorial.HelloWorld";
        }
        if (decide % 3 == 0) {
            return "Hello";
        }
        if (decide % 5 == 0) {
            return "World";
        }
        return String.valueOf(decide);
    }

}
