package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.HashMap;

public class AardSquare implements VisualElement {
    public double x, y, r;
    public boolean isFill, isStroke;
    public Color fill, stroke;
    public double strokeSize;

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

    public HashMap<String, Object> toDict()
    {
        HashMap<String, Object> aardMap = new HashMap<>();
        Object[] arr = { x, y, r, isFill, isStroke, fill, stroke, strokeSize };
        aardMap.put("AardSquare", arr);
        return aardMap;
    }
}
