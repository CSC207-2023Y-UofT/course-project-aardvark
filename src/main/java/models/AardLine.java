package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import java.util.HashMap;

public class AardLine implements VisualElement{
    private double x1, x2, y1, y2;
    private Color color;
    private double strokeSize;

    public AardLine(double x1, double y1, double x2, double y2, Color color, double strokeSize) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.strokeSize = strokeSize;
    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    public Color getColor() {
        return color;
    }

    public double getStrokeSize() {
        return strokeSize;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.setStroke(color);
        gc.setLineWidth(strokeSize);
        gc.strokeLine(x1, y1, x2, y2);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> dict = new HashMap<>();
        dict.put("Name", "AardLine");
        dict.put("X1", getX1());
        dict.put("Y1", getY1());
        dict.put("X2", getX2());
        dict.put("Y2", getY2());
        dict.put("Color", getColor().toString());
        dict.put("StrokeSize", getStrokeSize());
        return dict;
    }

    public static AardLine fromDict(HashMap<String, Object> dict) {
        return new AardLine((double) dict.get("X1"), (double) dict.get("Y1"), (double) dict.get("X2"), (double) dict.get("Y2"),
                Color.valueOf((String) dict.get("Color")), (double) dict.get("StrokeSize"));
    }
}
