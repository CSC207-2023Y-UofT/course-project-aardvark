package models;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AardTextTest {
    @Test
    void toDictTest() {
        Font font = new Font("Arial", 12.0);
        AardText text = new AardText("Arthur", Color.AQUAMARINE, font, 20, 40);

        HashMap<String, Object> m = text.toDict();
        Assertions.assertTrue(m.containsKey("Name"));

        Assertions.assertAll(
                () -> assertEquals("AardText", m.get("Name")),
                () -> assertEquals("Arthur", m.get("text")),
                () -> assertEquals(Color.AQUAMARINE, Color.valueOf((String)m.get("color"))),
                () -> assertEquals(font.getFamily(), m.get("fontFamily")),
                () -> assertEquals(font.getSize(), m.get("fontSize")),
                () -> assertEquals(20.0, m.get("x")),
                () -> assertEquals(40.0, m.get("y")));
    }

    @Test
    void fromDictTest() {
        HashMap<String, Object> m = new HashMap<>();
        m.put("Name", "AardText");
        m.put("text", "Arthur");
        m.put("x", 20.0);
        m.put("y", 40.0);
        m.put("color", Color.AQUAMARINE.toString());
        m.put("fontSize", 12.0);
        AardText arthurText = AardText.fromDict(m);
        // No assertion for fontFamily, since this causes an error with GitHub autograding
        Assertions.assertAll(
                () -> assertEquals("Arthur", arthurText.text),
                () -> assertEquals(12.0, arthurText.font.getSize()),
                () -> assertEquals(Color.AQUAMARINE, arthurText.color),
                () -> assertEquals(20.0, arthurText.coordinates[0]),
                () -> assertEquals(40.0, arthurText.coordinates[1])
        );
    }
}