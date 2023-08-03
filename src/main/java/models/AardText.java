package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import models.VisualElement;
import java.util.HashMap;

/**
 * The AardText class represents a text object to be drawn on a JavaFX Canvas.
 * It implements the interface VisualElement by implementing its draw and toDict methods.
 */
public class AardText implements VisualElement {
    /** The text to be drawn on the screen. */
    private final String text;

    /** The font family of the specified text.*/
    private final String fontFamily;

    /** The font size of the specified text.*/
    private final double fontSize;

    /** The color of the indicated text.*/
    private final Color color;

    /** The coordinates of the specified text, stored as an array of doubles with
     * two elements representing x and y respectively.*/
    private final double[] coordinates;

    /**
     * Creates a new AardText object.
     */
    public AardText(String text, Color color, Font font, double x, double y){
        this.text = text;
        this.fontFamily = font.getFamily();
        this.fontSize = font.getSize();
        this.color = color;
        this.coordinates = new double[]{x, y};
    }

    /**
     * Draws the AardText object to the canvas.
     * Overrides the draw method of the VisualElement interface.
     * @param gc A GraphicsContext that can be drawn on, with changes reflected to a canvas.
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFont(Font.font(fontFamily, fontSize));
        Color newColor = color;
        gc.setFill(newColor);
        gc.fillText(text, coordinates[0], coordinates[1]);
    }

    /**
     * Creates a hashmap for the given AardText object and its instance variables.
     * Overrides the toDict method of the VisualElement interface.
     * @return A hashmap containing "AardText" as its key, and an array of the
     * AardText object's parameters as the hashmap's value.
     */
    @Override
    public HashMap<String, Object> toDict(){
        HashMap<String, Object> aardTextMap = new HashMap<>();
        Object[] textArray = {text, fontFamily, fontSize, color, coordinates};
        aardTextMap.put("AardText", textArray);
        return aardTextMap;
    }

}
