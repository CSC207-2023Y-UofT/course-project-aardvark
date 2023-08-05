package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class AardCircle implements VisualElement  {
    public double x, y, r;
    public boolean isFill, isStroke;
    public Color fill, stroke;
    public double strokeSize;

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

    public HashMap<String, Object> toDict()
    {
        HashMap<String, Object> aardMap = new HashMap<>();
        Object[] arr = { x, y, r, isFill, isStroke, fill, stroke, strokeSize };
        aardMap.put("AardCircle", arr);
        return aardMap;
    }
}
