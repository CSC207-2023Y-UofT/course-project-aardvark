package free_draw;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.Stage;
import models.VisualElement;


import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

public class FreeDrawLine implements VisualElement{

    private final String colour;
    private final double strokeSize;
    private ArrayList<Point2D.Double> path = new ArrayList<>();

    public FreeDrawLine(String colour, double strokeSize) {
        this.colour = colour;
        this.strokeSize = strokeSize;
    }

    public FreeDrawLine(String colour, int strokeSize, ArrayList<Point2D.Double> path) {
        this.colour = colour;
        this.strokeSize = strokeSize;
        this.path = path;
    }

    public void addPoint(double x, double y) {
        path.add(new Point2D.Double(x, y));
    }

    /**
     * Precondition: There must be at least one point added to this FreeDrawLine
     *
     * @param gc
     */
    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.valueOf(colour));
        gc.setLineWidth(strokeSize);
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.setLineJoin(StrokeLineJoin.ROUND);

        gc.beginPath();
        Point2D.Double start = path.get(0);
        gc.moveTo(start.x, start.y);
        gc.stroke();

        for(int i = 1; i < path.size(); i++) {
            Point2D.Double next = path.get(i);
            gc.lineTo(next.x, next.y); //note the use of lineTo to fill in the gaps
            gc.stroke();
        }

    }

    public HashMap<String, Object> toDict() {
        return null;
    }

}
