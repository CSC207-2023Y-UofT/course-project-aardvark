package models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class FreeDrawLineTest {
    @Test
    void toDict() {
        FreeDrawLine freeDrawLine = new FreeDrawLine(Color.YELLOW, 3,
                new ArrayList<Point2D.Double>());
        freeDrawLine.addPoint(1, 1);
        freeDrawLine.addPoint(1, 2);
        freeDrawLine.addPoint(1, 3);
        freeDrawLine.addPoint(2, 3);
        freeDrawLine.addPoint(3, 3);
        freeDrawLine.addPoint(3, 4);
        freeDrawLine.addPoint(3, 5);
        Assertions.assertEquals("{Path=[{1.0, 1.0}, {1.0, 2.0}, {1.0, 3.0}, {2.0, 3.0}, {3.0, 3.0}, {3.0, 4.0}," +
                " {3.0, 5.0}], Size=3.0, Color=0xffff00ff, Name=FreeDrawLine}", freeDrawLine.toDict().toString());

    }
}
