package models;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AardTextTest {
    @Test
    void toDictTest() {
        Font font = new Font("Arial", 12.0);
        AardText text = new AardText("Arthur", Color.AQUAMARINE, font, 20, 40);

        HashMap<String, Object> textHashMap = text.toDict();
        Assertions.assertTrue(textHashMap.containsKey("Name"));
        Assertions.assertEquals("AardText", textHashMap.get("Name"));
        Assertions.assertTrue(textHashMap.containsKey("AardText"));
        Object[] obtainedArray = (Object[]) textHashMap.get("AardText");
        double [] obtainedCoordinatePair = (double[]) obtainedArray[4];
        // No assertion for fontFamily, since this causes an error with Github autograding
        Assertions.assertAll(
                () -> assertEquals("Arthur", obtainedArray[0]),
                () -> assertEquals(12.0, obtainedArray[2]),
                () -> assertEquals(Color.AQUAMARINE, obtainedArray[3]),
                () -> assertEquals(20, obtainedCoordinatePair[0]),
                () -> assertEquals(40, obtainedCoordinatePair[1])
        );
    }

    @Test
    void fromDictTest() {
        HashMap<String, Object> textHashMap = new HashMap<>();
        double [] coordinateArray = {20, 40};
        Object [] textArray = {"Arthur", "Arial", 12.0, Color.AQUAMARINE, coordinateArray};
        textHashMap.put("AardText", textArray);
        AardText arthurText = AardText.fromDict(textHashMap);
        // No assertion for fontFamily, since this causes an error with Github autograding
        Assertions.assertAll(
                () -> assertEquals("Arthur", arthurText.text),
                () -> assertEquals(12.0, arthurText.font.getSize()),
                () -> assertEquals(Color.AQUAMARINE, arthurText.color),
                () -> assertEquals(20, arthurText.coordinates[0]),
                () -> assertEquals(40, arthurText.coordinates[1])
        );
    }
}