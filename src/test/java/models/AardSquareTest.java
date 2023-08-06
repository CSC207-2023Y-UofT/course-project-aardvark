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

        HashMap<String, Object> squareHashMap = square.toDict();
        Assertions.assertTrue(squareHashMap.containsKey("AardSquare"));
        Object[] obtainedArray = (Object[]) squareHashMap.get("AardSquare");

        Assertions.assertAll(
                () -> assertEquals(50.0, obtainedArray[0]),
                () -> assertEquals(50.0, obtainedArray[1]),
                () -> assertEquals(5.0, obtainedArray[2]),
                () -> assertTrue((Boolean) obtainedArray[3]),
                () -> assertFalse((Boolean) obtainedArray[4]),
                () -> assertEquals(Color.GREEN, obtainedArray[5]),
                () -> assertEquals(Color.DARKOLIVEGREEN, obtainedArray[6]),
                () -> assertEquals(3.0, obtainedArray[7])
        );
    }
}