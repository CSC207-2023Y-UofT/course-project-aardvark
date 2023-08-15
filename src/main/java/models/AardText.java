package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.HashMap;

/**
 * The AardText class represents a text object to be drawn on a JavaFX Canvas.
 * It implements the interface VisualElement by implementing its draw and toDict methods.
 */
@SuppressWarnings("CanBeFinal")
public class AardText implements VisualElement {
    /** The text to be drawn on the screen. */
    public String text;
    public Font font;

    /** The color of the indicated text.*/
    public Color color;

    /** The coordinates of the specified text, stored as an array of doubles with
     * two elements representing x and y respectively.*/
    public double[] coordinates;

    /**
     * Creates a new AardText object.
     */
    public AardText(String text, Color color, Font font, double x, double y){
        this.text = text;
        this.font = new Font(font.getFamily(), font.getSize());
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
        gc.setFont(font);
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
        HashMap<String, Object> m = new HashMap<>();
        m.put("Name", "AardText");
        m.put("text", text);
        m.put("color", String.valueOf(color));
        m.put("fontFamily", font.getFamily());
        m.put("fontSize", font.getSize());
        m.put("x", coordinates[0]);
        m.put("y", coordinates[1]);
        return m;
    }

    public static AardText fromDict(HashMap<String, Object> m) {
        return new AardText(
                (String)m.get("text"), Color.valueOf((String)m.get("color")),
                new Font((String)m.get("fontFamily"), (double)m.get("fontSize")),
                (double)m.get("x"), (double)m.get("y"));
    }

}
