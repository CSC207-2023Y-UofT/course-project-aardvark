package models;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FreeDrawLineTest {

    @Test
    void toDict() {
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
    void fromDict() {
        FreeDrawLine f = new FreeDrawLine(new Color(0,0,0, 0), 10);
        for(int i = 0; i<3; i++) {
            f.addPoint(i,i+3);
        }
        FreeDrawLine g = FreeDrawLine.fromDict(f.toDict());
        assert f.toDict().equals(g.toDict());
    }

    @Test
    void pointMap() {
        FreeDrawLine f = new FreeDrawLine(new Color(0,0,0, 0), 10);
        for(int i = 0; i<10; i++) {
            f.addPoint(i,i+3);
        }
        assert f.pointMap().get(3).equals("3.0,6.0");
    }
}