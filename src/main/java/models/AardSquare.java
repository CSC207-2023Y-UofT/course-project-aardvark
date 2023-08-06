package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 The AardSquare class represents a square shape to be drawn on a JavaFX GraphicsContext.

 It implements the interface VisualElement by providing its draw and toDict methods.
 */
public class AardSquare implements VisualElement {
    /*
    The x-coordinate of the square's top-left corner, y-coordinate of the square's top-left corner,
    and the side length of the square.
    */
    public double x, y, r;
    /*
    isFill represents whether the square should be filled with the Colour fill, or not.

    Likewise, isStroke specifies whether the square should have the Colour stroke as its outline.
     */
    public boolean isFill, isStroke;

    /*
    Specifies the colour of the square's fill, and outline respectively.
     */
    public Color fill, stroke;

    /*
    Specifies the thickness of the square's outline.
     */
    public double strokeSize;

    /**
     Constructs a new AardSquare object with the specified properties.
     @param _x The x-coordinate of the square's top-left corner.
     @param _y The y-coordinate of the square's top-left corner.
     @param _r The side length of the square.
     @param _isFill Specifies whether the square should be filled with a color.
     @param _isStroke Specifies whether the square should have an outline.
     @param _fill The fill color of the square.
     @param _stroke The outline color of the square.
     @param _strokeSize The outline thickness of the square.
     */
    public AardSquare(double _x, double _y, double _r, boolean _isFill, boolean _isStroke, Color _fill, Color _stroke, double _strokeSize)
    {
        r = _r;
        x = _x;
        y = _y;
        isFill = _isFill;
        isStroke = _isStroke;
        fill = Color.color(_fill.getRed(), _fill.getGreen(), _fill.getBlue(), _fill.getOpacity());
        stroke = Color.color(_stroke.getRed(), _stroke.getGreen(), _stroke.getBlue(), _stroke.getOpacity());
        strokeSize = _strokeSize;
    }

    /**
     Draw the AardSquare object on the canvas.

     Overrides the draw method of the VisualElement interface.

     @param gc A GraphicsContext that can be drawn on, with changes reflected on a canvas.
     */
    public void draw(GraphicsContext gc)
    {
        gc.setFill(isFill
                ? fill
                : Color.TRANSPARENT);

        gc.fillRect(x, y, r, r);

        gc.setLineWidth(strokeSize);
        gc.setStroke(isStroke
                ? stroke
                : Color.TRANSPARENT);

        gc.strokeRect(x, y, r, r);
    }

    /**
     Convert the AardSquare properties to a dictionary format.

     Overrides the toDict method of the VisualElement interface.

     @return A HashMap containing "AardSquare" as its key, and an array of the
     AardSquare object's parameters as the HashMap's value.
     */
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> m = new HashMap<>();
        m.put("Name", "AardSquare");
        m.put("x", x);
        m.put("y", y);
        m.put("r", r);
        m.put("isFill", isFill);
        m.put("isStroke", isStroke);
        m.put("fill", fill + "");
        m.put("stroke", stroke + "");
        m.put("strokeSize", strokeSize);
        return m;
    }

    public static AardSquare fromDict(HashMap<String, Object> m) {
        return new AardSquare(
                (double)m.get("x"), (double)m.get("y"), (double)m.get("r"),
                (boolean)m.get("isFill"), (boolean)m.get("isStroke"),
                Color.valueOf((String)m.get("fill")),
                Color.valueOf((String)m.get("stroke")),
                (double)m.get("strokeSize"));
    }
}
