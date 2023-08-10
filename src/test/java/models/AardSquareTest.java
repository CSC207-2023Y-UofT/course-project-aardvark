package models;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AardSquareTest {

    @Test
    void toDictTest() {
        AardSquare square = new AardSquare(50.0, 50.0, 5.0, true, false,
                Color.GREEN, Color.DARKOLIVEGREEN, 3.0);

        HashMap<String, Object> m = square.toDict();
        Assertions.assertTrue(m.containsKey("Name"));

        Assertions.assertAll(
                () -> assertEquals("AardSquare", m.get("Name")),
                () -> assertEquals(50.0, m.get("x")),
                () -> assertEquals(50.0, m.get("y")),
                () -> assertEquals(5.0, m.get("r")),
                () -> assertTrue((Boolean) m.get("isFill")),
                () -> assertFalse((Boolean) m.get("isStroke")),
                () -> assertEquals(Color.GREEN, Color.valueOf((String)m.get("fill"))),
                () -> assertEquals(Color.DARKOLIVEGREEN, Color.valueOf((String)m.get("stroke"))),
                () -> assertEquals(3.0, m.get("strokeSize"))
        );
    }

    @Test
    void fromDict() {
        HashMap<String, Object> m = new HashMap<>();
        m.put("Name", "AardSquare");
        m.put("x", 51.0);
        m.put("y", 51.0);
        m.put("r", 5.0);
        m.put("isFill", true);
        m.put("isStroke", false);
        m.put("fill", Color.LEMONCHIFFON.toString());
        m.put("stroke", Color.LIGHTYELLOW.toString());
        m.put("strokeSize", 5.0);
        AardSquare lemonSquare = AardSquare.fromDict(m);
        Assertions.assertAll(
                () -> assertEquals(51.0, lemonSquare.x),
                () -> assertEquals(51.0, lemonSquare.y),
                () -> assertEquals(5.0, lemonSquare.r),
                () -> assertTrue(lemonSquare.isFill),
                () -> assertFalse(lemonSquare.isStroke),
                () -> assertEquals(Color.LEMONCHIFFON, lemonSquare.fill),
                () -> assertEquals(Color.LIGHTYELLOW, lemonSquare.stroke),
                () -> assertEquals(5.0, lemonSquare.strokeSize)
        );
    }
}