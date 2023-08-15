package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 The AardCircle class represents a circular shape to be drawn on a JavaFX GraphicsContext.

 It implements the interface VisualElement by providing its draw and toDict methods.
 */
public class AardCircle implements VisualElement  {
    /*
    The x-coordinate of the circle's center, y-coordinate of the circle's center,
    and the radius of the circle.
    */
    public final double x;
    public final double y;
    public final double r;

    /*
    isFill represents whether the circle should be filled with the Colour fill, or not.

    Likewise, isStroke specifies whether the circle should have the Colour stroke as its outline.
     */
    public final boolean isFill;
    public final boolean isStroke;

    /*
    Specifies the colour of the circle's fill, and outline respectively.
     */
    public final Color fill;
    public final Color stroke;

    /*
    Specifies the thickness of the circle's outline.
     */
    public final double strokeSize;

    /**
     Constructs a new AardCircle object with the specified properties.
     @param _x The x-coordinate of the circle's center.
     @param _y The y-coordinate of the circle's center.
     @param _r The radius of the circle.
     @param _isFill Specifies whether the circle should be filled with a color.
     @param _isStroke Specifies whether the circle should have an outline.
     @param _fill The fill color of the circle.
     @param _stroke The outline color of the circle.
     @param _strokeSize The outline thickness of the circle.
     */
    public AardCircle(double _x, double _y, double _r, boolean _isFill, boolean _isStroke, Color _fill, Color _stroke, double _strokeSize)
    {
        x = _x;
        y = _y;
        r = _r;
        isFill = _isFill;
        isStroke = _isStroke;
        fill = Color.color(_fill.getRed(), _fill.getGreen(), _fill.getBlue(), _fill.getOpacity());
        stroke = Color.color(_stroke.getRed(), _stroke.getGreen(), _stroke.getBlue(), _stroke.getOpacity());
        strokeSize = _strokeSize;
    }

    /**
     Draw the AardCircle object on the canvas.

     Overrides the draw method of the VisualElement interface.

     @param gc A GraphicsContext that can be drawn on, with changes reflected on a canvas.
     */
    public void draw(GraphicsContext gc)
    {
        gc.setFill(isFill
                ? fill
                : Color.TRANSPARENT);

        gc.fillOval(x, y, r, r);

        gc.setLineWidth(strokeSize);
        gc.setStroke(isStroke
                ? stroke
                : Color.TRANSPARENT);

        gc.strokeOval(x, y, r, r);
    }

    /**
     Convert the AardCircle properties to a dictionary format.

     Overrides the toDict method of the VisualElement interface.

     @return A HashMap containing "AardCircle" as its key, and an array of the
     AardCircle object's parameters as the HashMap's value.
     */
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> m = new HashMap<>();
        m.put("Name", "AardCircle");
        return getStringObjectHashMap(m, x, y, r, isFill, isStroke, fill, stroke, strokeSize);
    }

    @NotNull
    static HashMap<String, Object> getStringObjectHashMap(HashMap<String, Object> m, double x, double y, double r, boolean isFill, boolean isStroke, Color fill, Color stroke, double strokeSize) {
        m.put("x", x);
        m.put("y", y);
        m.put("r", r);
        m.put("isFill", isFill);
        m.put("isStroke", isStroke);
        m.put("fill", String.valueOf(fill));
        m.put("stroke", String.valueOf(stroke));
        m.put("strokeSize", strokeSize);
        return m;
    }

    public static AardCircle fromDict(HashMap<String, Object> m) {
        return new AardCircle(
                (double)m.get("x"), (double)m.get("y"), (double)m.get("r"),
                (boolean)m.get("isFill"), (boolean)m.get("isStroke"),
                Color.valueOf((String)m.get("fill")),
                Color.valueOf((String)m.get("stroke")),
                (double)m.get("strokeSize"));
    }
}
