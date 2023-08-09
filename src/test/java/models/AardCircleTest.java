package models;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AardCircleTest {

    @Test
    void toDictTest() {
        AardCircle circle = new AardCircle(50.0, 50.0, 5.0, true, false,
                Color.GREEN, Color.DARKOLIVEGREEN, 3.0);

        HashMap<String, Object> m = circle.toDict();
        Assertions.assertTrue(m.containsKey("Name"));

        Assertions.assertAll(
                () -> assertEquals("AardCircle", m.get("Name")),
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
    void fromDictText() {
        HashMap<String, Object> m = new HashMap<>();
        m.put("Name", "AardCircle");
        m.put("x", 50.0);
        m.put("y", 50.0);
        m.put("r", 5.0);
        m.put("isFill", false);
        m.put("isStroke", true);
        m.put("fill", Color.RED.toString());
        m.put("stroke", Color.SPRINGGREEN.toString());
        m.put("strokeSize", 4.0);
        AardCircle watermelon = AardCircle.fromDict(m);
        Assertions.assertAll(
                () -> assertEquals(50.0, watermelon.x),
                () -> assertEquals(50.0, watermelon.y),
                () -> assertEquals(5.0, watermelon.r),
                () -> assertFalse(watermelon.isFill),
                () -> assertTrue(watermelon.isStroke),
                () -> assertEquals(Color.RED, watermelon.fill),
                () -> assertEquals(Color.SPRINGGREEN, watermelon.stroke),
                () -> assertEquals(4.0, watermelon.strokeSize)
        );

    }
}