package org.openjfx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Shape;
import org.w3c.dom.css.Rect;

public class MainApp extends Application {

//    @Override
//    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("scene.fxml"));
//
//        Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
//
//        stage.setTitle("JavaFX and Gradle");
//        stage.setScene(scene);
//        stage.show();
//    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button btn1=new Button("Say, Hello World");
        Rectangle rect = new Rectangle();
        rect.setX(10); rect.setY(20); rect.setWidth(100); rect.setHeight(200);
        Group rootGroup = new Group();
        rootGroup.getChildren().add(rect);
        btn1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("hello world");
            }
        });
        StackPane root = new StackPane();
        root.getChildren().add(btn1);
        Scene scene=new Scene(root,600,400);
        primaryStage.setTitle("First JavaFX Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}