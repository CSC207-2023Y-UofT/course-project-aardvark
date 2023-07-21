package text;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class TextMain extends Application{


    public void start(Stage stage) throws Exception {

        stage.setTitle("Writing Text");

        HBox root = new HBox();
        VBox toolsRoot = new VBox();
        Separator HBoxSeparator = new Separator();
        HBoxSeparator.setMaxWidth(10);
        HBoxSeparator.setOrientation(Orientation.VERTICAL);

        Canvas textCanvas = new Canvas(300, 300);
        GraphicsContext gc = textCanvas.getGraphicsContext2D();
        /* This is the default font and text that will be used */
        Font defaultFont = Font.font("Verdana", 15);
        gc.setFont(defaultFont);
        String [] defaultInput = {"ant"};

        /*===== Creating Text =====*/

        /* Setting the Text */

        TextField textField = new TextField("ant");
        EventHandler<ActionEvent> setTextHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                defaultInput[0] = textField.getText();
            }
        };
        textField.setOnAction(setTextHandler);
        toolsRoot.getChildren().add(textField);

        /* Creating Text at a Specified Location */
        EventHandler<MouseEvent> writeTextHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double xValue = event.getX();
                double yValue = event.getY();
                AardText newText = new AardText(defaultInput[0], gc.getFont(), xValue, yValue);
                newText.draw(gc);
            }
        };
        textCanvas.addEventFilter(MouseEvent.MOUSE_CLICKED, writeTextHandler);

        /*===== Changing and Adjusting Text-Related Settings on the Canvas =====*/

        /* Increasing Text Size */
        Button btIncreaseText = new Button("Text++");
        EventHandler<MouseEvent> increaseFontHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Font currentFont = gc.getFont();
                Font newFont = Font.font(currentFont.getFamily(), currentFont.getSize() + 1);
                gc.setFont(newFont);
            }
        };
        btIncreaseText.addEventFilter(MouseEvent.MOUSE_CLICKED, increaseFontHandler);

        toolsRoot.getChildren().add(btIncreaseText);
        root.getChildren().add(toolsRoot);
        root.getChildren().add(HBoxSeparator);
        root.getChildren().add(textCanvas);
        Scene sc = new Scene(root);
        stage.setScene(sc);
        stage.show();
    }
}


