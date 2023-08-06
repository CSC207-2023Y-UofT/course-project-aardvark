package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.setLineWidth(strokeSize);
        gc.strokeLine(x1, y1, x2, y2);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> dict = new HashMap<>();
        dict.put("Name", "AardLine");
        return dict;
    }

//    public static AardLine fromDict(HashMap<String, Object> dict) {
//        return new AardLine(dict.);
//    }
}
