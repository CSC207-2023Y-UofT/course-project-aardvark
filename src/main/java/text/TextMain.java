package text;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class TextMain extends Application{

    public void start(Stage stage) throws Exception {

        ArrayList<AardText> textArrayList = new ArrayList<AardText>();
        stage.setTitle("Writing Text");
        stage.setResizable(false);

        HBox root = new HBox();
        VBox toolsRoot = new VBox();
        VBox canvasRoot = new VBox();
        Separator HBoxSeparator = new Separator();
        HBoxSeparator.setMaxWidth(10);
        HBoxSeparator.setOrientation(Orientation.VERTICAL);

        Canvas textCanvas = new Canvas(300, 300);
        GraphicsContext gc = textCanvas.getGraphicsContext2D();

        Font defaultFont = Font.font("Verdana", 15);
        gc.setFont(defaultFont);
        String [] defaultInput = {"ant"};
        IntegerProperty sizeLabelProperty = new SimpleIntegerProperty(15);

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

        TextInputDialog textDialog = new TextInputDialog();
        textDialog.setTitle("Input Text");
        // TextField dialogTextField = new TextField();
        textDialog.setHeaderText("Please write");

        textDialog.setX(10);
        textDialog.setY(10);

        EventHandler<DialogEvent> getTextHandler = new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent event) {
                defaultInput[0] = textDialog.getResult();
                System.out.println(defaultInput[0]);
            }
        };
        textDialog.setOnCloseRequest(getTextHandler);

        /* Creating Text at a Specified Location */
        EventHandler<MouseEvent> writeTextHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double xValue = event.getX();
                double yValue = event.getY();
                textDialog.showAndWait();
                AardText newText = new AardText(defaultInput[0], (Color) gc.getFill(), gc.getFont(), xValue, yValue);
                textArrayList.add(newText);
                newText.draw(gc);
                printTextArray(textArrayList);
            }
        };
        textCanvas.addEventFilter(MouseEvent.MOUSE_CLICKED, writeTextHandler);


        canvasRoot.getChildren().add(textCanvas);
        canvasRoot.getChildren().add(textField);
        /*===== Changing and Adjusting Text-Related Settings on the Canvas =====*/

        /* Changing Font Family */
        System.out.println(Font.getFamilies());
        final ComboBox<String> fontComboBox = new ComboBox<String>();
//        for (String family: Font.getFamilies()){
//            fontComboBox.getItems().add(family);
//        }
        fontComboBox.getItems().addAll(
                "Algerian",
                "Arial",
                "Cooper Black",
                "Century Gothic",
                "Verdana"
        );
        fontComboBox.setValue("Verdana");
        EventHandler<ActionEvent> changeFontHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Font newFont = new Font(fontComboBox.getValue(), sizeLabelProperty.get());
                gc.setFont(newFont);
            }
        };
        fontComboBox.addEventFilter(ActionEvent.ACTION, changeFontHandler);
        toolsRoot.getChildren().add(fontComboBox);


        /* Changing Text Colour */
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        EventHandler<ActionEvent> changeColorHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gc.setFill(colorPicker.getValue());
            }
        };
        colorPicker.addEventFilter(ActionEvent.ACTION, changeColorHandler);
        toolsRoot.getChildren().add(colorPicker);

        /* Setting Text Size */
        Label sizeLabel = new Label("15");
        sizeLabel.textProperty().bind(sizeLabelProperty.asString("Font size: %d"));
        toolsRoot.getChildren().add(sizeLabel);

        /* Increasing Text Size */
        Button btIncreaseText = new Button("Text++");
        EventHandler<MouseEvent> increaseFontHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Font currentFont = gc.getFont();
                Font newFont = Font.font(currentFont.getFamily(), currentFont.getSize() + 1);
                sizeLabelProperty.set(sizeLabelProperty.get() + 1);
                gc.setFont(newFont);
            }
        };
        btIncreaseText.addEventFilter(MouseEvent.MOUSE_CLICKED, increaseFontHandler);
        toolsRoot.getChildren().add(btIncreaseText);

        /* Decreasing Text Size */
        Button btDecreaseText = new Button("Text--");
        EventHandler<MouseEvent> decreaseFontHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Font currentFont = gc.getFont();
                Font newFont = Font.font(currentFont.getFamily(), currentFont.getSize() - 1);
                sizeLabelProperty.set(sizeLabelProperty.get() - 1);
                gc.setFont(newFont);
            }
        };
        btDecreaseText.addEventFilter(MouseEvent.MOUSE_CLICKED, decreaseFontHandler);
        toolsRoot.getChildren().add(btDecreaseText);

        root.getChildren().add(toolsRoot);
        root.getChildren().add(HBoxSeparator);
        root.getChildren().add(canvasRoot);
        Scene sc = new Scene(root);
        stage.setScene(sc);
        stage.show();
    }

    public void printTextArray(ArrayList<AardText> textArrayList){
        for (AardText item: textArrayList){
            System.out.println(item);
        }
    }

}


