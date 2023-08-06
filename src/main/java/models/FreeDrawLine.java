package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

/**
 The FreeDrawLine class represents a freehand-drawn line element that can be drawn on a JavaFX GraphicsContext.

 It allows adding points to the path to create a continuous line with a specified color and stroke size.
 */
public class FreeDrawLine implements VisualElement{

    private final Color colour;
    private final double strokeSize;
    private ArrayList<Point2D.Double> path = new ArrayList<>();

    /**
     Constructs a FreeDrawLine with the given color and stroke size.
     @param colour The color of the freehand-drawn line.
     @param strokeSize The stroke size of the freehand-drawn line.
     */
    public FreeDrawLine(Color colour, double strokeSize) {
        this.colour = colour;
        this.strokeSize = strokeSize;
    }

    /**
     Constructs a FreeDrawLine with the given color, stroke size, and path.
     @param colour The color of the freehand-drawn line.
     @param strokeSize The stroke size of the freehand-drawn line.
     @param path The ArrayList of Point2D.Double representing the path of the line.
     */
    public FreeDrawLine(Color colour, double strokeSize, ArrayList<Point2D.Double> path) {
        this.colour = colour;
        this.strokeSize = strokeSize;
        this.path = path;
    }

    /**
     Add a point to the path of the FreeDrawLine.
     @param x The x-coordinate of the point.
     @param y The y-coordinate of the point.
     */
    public void addPoint(double x, double y) {
        path.add(new Point2D.Double(x, y));
    }

    /**
     * Precondition: There must be at least one point added to this FreeDrawLine

     Draw the FreeDrawLine on the specified GraphicsContext.
     @param gc The GraphicsContext on which the FreeDrawLine should be drawn.
     */
    public void draw(GraphicsContext gc) {
        gc.setStroke(colour);
        gc.setLineWidth(strokeSize);
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.setLineJoin(StrokeLineJoin.ROUND);

        gc.beginPath();
        Point2D.Double start = path.get(0);
        gc.moveTo(start.x, start.y);
        gc.stroke();

        int i = 1;
        while(i <= path.size() - 3) {
            gc.bezierCurveTo(path.get(i).x, path.get(i).y,
                    path.get(i+1).x, path.get(i+1).y,
                    path.get(i+2).x, path.get(i+2).y);
            gc.stroke();
            i += 3;
        }
        switch (path.size() - i) {
            case 2:
                gc.quadraticCurveTo(path.get(i).x, path.get(i).y,
                        path.get(i+1).x, path.get(i+1).y);
            case 1:
                gc.lineTo(path.get(i).x, path.get(i).y);
        }
        gc.stroke();
    }

    public ArrayList<Point2D.Double> getPath() {
        return path;
    }

    /**
     Convert the FreeDrawLine properties to a dictionary format.
     @return A HashMap containing the FreeDrawLine's properties in dictionary format.
     */
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> dict = new HashMap<>();
        dict.put("Name", "FreeDrawLine");
        dict.put("Color", colour.toString());
        dict.put("StrokeSize", strokeSize);
        dict.put("PointList", pointMap());
        return dict;
    }

    /**
     Create a FreeDrawLine object from a dictionary representation.
     @param dict The HashMap representing the FreeDrawLine object.
     @return The constructed FreeDrawLine object.
     */
    @SuppressWarnings("unchecked")
    public static FreeDrawLine fromDict(HashMap<String, Object> dict) {
        //points
        ArrayList<Point2D.Double> path = new ArrayList<>();
        for(Object i : (ArrayList) dict.get("PointList")) {
            String j = (String) i;
            Double x = Double.parseDouble(j.split(",")[0]);
            Double y = Double.parseDouble(j.split(",")[1]);
            Point2D.Double p = new Point2D.Double(x, y);
            path.add(p);
        }
        FreeDrawLine ret = new FreeDrawLine(Color.valueOf((String) dict.get("Color")),
                (Double) dict.get("StrokeSize"), path);
        return ret;

    }

    /**
     Create a list of String representations of points in the path.
     @return An ArrayList of Strings representing the points in the path.
     */
    public ArrayList<String> pointMap() {
        ArrayList<String> lst = new ArrayList<>();
        for(Point2D.Double i : path) {
            lst.add(i.getX() + "," + i.getY());
        }
        return lst;
    }

}
