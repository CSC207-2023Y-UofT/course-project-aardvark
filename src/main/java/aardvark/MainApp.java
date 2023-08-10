package aardvark;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 The MainApp class is the main entry point of the Aardvark application.

 It extends the javafx.application.Application class to launch the JavaFX application.
 */
public class MainApp extends Application {

    /**
     The start method is called when the JavaFX application is launched.

     It loads the "signin.fxml" file and sets up the main stage with the necessary components.

     @param stage The primary stage of the JavaFX application.

     @throws Exception If an error occurs during the application startup process.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("signin.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        Image icon = new Image("images/icon.png");
        stage.getIcons().add(icon);
        stage.setMinWidth(600);
        stage.setMinHeight(650);

        stage.setTitle("Aardvark");
        stage.setScene(scene);
        stage.show();
    }

    /**
     The main method is the entry point of the Java application.

     It launches the JavaFX application by calling the launch method.
     */
    public static void main(String[] args) {
        launch(args);
    }

}
