package text;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import models.VisualElement;

import java.util.HashMap;

public class AardText implements VisualElement {
    private final String text;
    private final String fontFamily;
    private final double fontSize;
    private final String color;

    private final double[] coordinates;

    public AardText(String text, String color, Font font, double x, double y){
        this.text = text;
        this.fontFamily = font.getFamily();
        this.fontSize = font.getSize();
        this.color = color;
        coordinates = new double[2];
        coordinates[0] = x;
        coordinates[1] = y;
    }

    public String getStringCoordinates(){
        return "(" + coordinates[0] + ", " + coordinates[1] + ")";
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFont(Font.font(fontFamily, fontSize));
        Color newColor = Color.valueOf(color);
        gc.setFill(newColor);
        gc.fillText(text, coordinates[0], coordinates[1]);
    }

    @Override
    public String toString(){
        return this.text + ", " + getStringCoordinates() + ", " + this.fontFamily + ", " + this.fontSize + ", " + this.color;
    }

    @Override
    public HashMap<String, Object> toDict(){
        HashMap<String, Object> aardTextMap = new HashMap<String, Object>();
        Object[] textArray = {text, fontFamily, fontSize, color, coordinates};
        aardTextMap.put("AardText", textArray);
        return aardTextMap;
    }

}
