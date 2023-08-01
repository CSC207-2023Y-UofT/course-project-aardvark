package text;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AardCircle {
    double x, y, r;
    Color fill, stroke;
    double strokeSize;

    public AardCircle(double _x, double _y, double _r, Color _fill, Color _stroke, double _strokeSize)
    {
        x = _x;
        y = _y;
        r = _r;
        fill = _fill;
        stroke = _stroke;
        strokeSize = _strokeSize;
    }

    public void draw(GraphicsContext gc, boolean isFill, boolean isStroke)
    {
        gc.setFill(isFill
                ? fill
                : Color.TRANSPARENT);

        gc.fillOval(x - 25, y - 25, 50, 50);

        gc.setLineWidth(strokeSize);
        gc.setStroke(isStroke
                ? stroke
                : Color.TRANSPARENT);

        gc.strokeOval(x - 25, y - 25, 50, 50);
    }
}
