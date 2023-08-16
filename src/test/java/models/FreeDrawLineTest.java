package models;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;

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
        FreeDrawLine f = new FreeDrawLine(new Color(0,0,0, 0), 10);
        for(int i = 0; i<3; i++) {
            f.addPoint(i,i+3);
        }
        System.out.println(f.toDict().toString());
        assert f.toDict().toString().equals(
                "{StrokeSize=10.0, PointList=[0.0,3.0, 1.0,4.0, 2.0,5.0], " +
                        "Color=0x00000000, Name=FreeDrawLine}");
    }

    @Test
    void fromDictTest() {
        FreeDrawLine f = new FreeDrawLine(new Color(0,0,0, 0), 10);
        for(int i = 0; i<3; i++) {
            f.addPoint(i,i+3);
        }
        FreeDrawLine g = FreeDrawLine.fromDict(f.toDict());
        assert f.toDict().equals(g.toDict());
    }

    @Test
    void pointMapTest() {
        FreeDrawLine f = new FreeDrawLine(new Color(0,0,0, 0), 10);
        for(int i = 0; i<10; i++) {
            f.addPoint(i,i+3);
        }
        assert f.pointMap().get(3).equals("3.0,6.0");
    }
}