package models;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FreeDrawLineTest {

    @Test
    void getPathTest() {
        ArrayList<Point2D.Double> pathSoFar = new ArrayList<>();
        Point2D.Double point1 = new Point2D.Double(1, 2);
        pathSoFar.add(point1);
        FreeDrawLine line = new FreeDrawLine(Color.TOMATO, 4, pathSoFar);
        Assertions.assertEquals(point1, line.getPath().get(0));
    }

    @Test
    void addPointTestFromEmpty() {
        FreeDrawLine line = new FreeDrawLine(Color.TOMATO, 4);
        line.addPoint(2, 3);
        Point2D.Double newPoint = new Point2D.Double(2, 3);
        Assertions.assertEquals(newPoint, line.getPath().get(0));
    }

    @Test
    void addPointTestNonEmpty() {
        ArrayList<Point2D.Double> pathSoFar = new ArrayList<>();
        Point2D.Double point1 = new Point2D.Double(1, 2);
        pathSoFar.add(point1);
        FreeDrawLine line = new FreeDrawLine(Color.TOMATO, 4, pathSoFar);
        line.addPoint(2, 3);
        Point2D.Double point2 = new Point2D.Double(2, 3);
        Assertions.assertEquals(point2, line.getPath().get(1));
    }

    @Test
    void toDictTest() {

    }
}