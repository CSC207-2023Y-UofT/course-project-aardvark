package models;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
class ProjectTest {

    Project project;
    @BeforeEach
    void setUpPreExistingProject() {
        Font font = new Font("Arial", 12.0);
        AardText text = new AardText("National Aardvark Day", Color.AQUAMARINE, font, 20, 40);
        ArrayList<VisualElement> visualElements = new ArrayList<>();
        visualElements.add(text);
        Calendar calendar = new GregorianCalendar(2023, Calendar.MARCH, 19);
        Date date = calendar.getTime();
        project = new Project("Aardvark Drawing", visualElements, date, 500, 600);
    }
    @Test
    void getDateTest() {
        Calendar calendar = new GregorianCalendar(2023, Calendar.MARCH, 19);
        Date date2 = calendar.getTime();
        Assertions.assertEquals(date2, project.getDate());
    }

    @Test
    void setDateTest() {
        Calendar calendar = new GregorianCalendar(2023, Calendar.AUGUST, 5);
        Date date2 = calendar.getTime();
        project.setDate(date2);

        Date date3 = calendar.getTime();
        Assertions.assertEquals(date3, project.getDate());
    }

    @Test
    void setProjectNameTest() {
        project.setProjectName("Cheese");
        Assertions.assertEquals("Cheese", project.getName());
    }

    @Test
    void getNameTest() {
        Assertions.assertEquals("Aardvark Drawing", project.getName());
    }

    @Test
    void getElementsTest() {
        // Checking if the element has been added
        ArrayList<VisualElement> projectElements = project.getElements();
        Assertions.assertEquals(1, projectElements.size());

        // Checking if the element is an AardText with correct text
        VisualElement firstElement = projectElements.get(0);
        Assertions.assertEquals("National Aardvark Day", firstElement.toDict().get("text"));
    }

    @Test
    void getElementsTestEmpty() {
        Project project2 = new Project("Untitled");
        ArrayList<VisualElement> projectElements = project2.getElements();
        Assertions.assertEquals(0, projectElements.size());
    }

    @Test
    void addVisualElementTest() {
        AardCircle circle = new AardCircle(20.0, 50.0, 5.0, true, false,
                Color.BLUE, Color.LIGHTSKYBLUE, 3.0);
        project.addVisualElement(circle);
        ArrayList<VisualElement> projectElements = project.getElements();
        Assertions.assertEquals(2, projectElements.size());

        // Checking if the most recent element is an AardCircle
        VisualElement recentElement = projectElements.get(1);
        // Using containsKey is valid, since the toDict() produces only one key-value pair
        Assertions.assertTrue(recentElement.toDict().containsKey("Name"));

        // Checking if the redo stack is empty
    }

    @Test
    void getLastAndRemoveCircleTest() {
        AardCircle circle = new AardCircle(20.0, 50.0, 5.0, true, false,
                Color.BLUE, Color.LIGHTSKYBLUE, 3.0);
        project.addVisualElement(circle);
        AardCircle removedCircle = project.getLastAndRemoveCircle();
        Assertions.assertEquals(1, project.getElements().size());
        Assertions.assertAll(
                () -> assertEquals(20.0, removedCircle.x),
                () -> assertEquals(50.0, removedCircle.y),
                () -> assertEquals(5.0, removedCircle.r),
                () -> assertTrue(removedCircle.isFill),
                () -> assertFalse(removedCircle.isStroke),
                () -> assertEquals(Color.BLUE, removedCircle.fill),
                () -> assertEquals(Color.LIGHTSKYBLUE, removedCircle.stroke),
                () -> assertEquals(3.0, removedCircle.strokeSize)
        );
    }

    @Test
    void getLastAndRemoveSquareTest() {
        AardSquare square = new AardSquare(10.0, 10.0, 2.0, false, true,
                Color.GREEN, Color.DARKOLIVEGREEN, 3.0);
        project.addVisualElement(square);
        AardSquare removedSquare = project.getLastAndRemoveSquare();
        Assertions.assertEquals(1, project.getElements().size());
        Assertions.assertAll(
                () -> assertEquals(10.0, removedSquare.x),
                () -> assertEquals(10.0, removedSquare.y),
                () -> assertEquals(2.0, removedSquare.r),
                () -> assertFalse(removedSquare.isFill),
                () -> assertTrue(removedSquare.isStroke),
                () -> assertEquals(Color.GREEN, removedSquare.fill),
                () -> assertEquals(Color.DARKOLIVEGREEN, removedSquare.stroke),
                () -> assertEquals(3.0, removedSquare.strokeSize)
        );
    }

    @Test
    void getCurrentLineTestEmpty() {
        Assertions.assertNull(project.getCurrentLine());
    }

    @Test
    void getCurrentLineTest() {
        FreeDrawLine line = new FreeDrawLine(Color.TOMATO, 4);
        project.addVisualElement(line);
        Assertions.assertEquals(line, project.getCurrentLine());
    }

    @Test
    void getRedoStackTestEmpty() {
        Assertions.assertTrue(project.getRedoStack().empty());
    }

    @Test
    void getRedoStackTest() {
        AardSquare square = new AardSquare(10.0, 10.0, 2.0, false, true,
                Color.GREEN, Color.DARKOLIVEGREEN, 3.0);
        project.addVisualElement(square);
        project.undoVisualElement();
        project.undoVisualElement();
        // Checks if both elements have been added to the redo stack
        Assertions.assertEquals(2, project.getRedoStack().size());
    }

    @Test
    void undoVisualElementTest() {
        AardSquare square = new AardSquare(10.0, 10.0, 2.0, false, true,
                Color.GREEN, Color.DARKOLIVEGREEN, 3.0);
        project.addVisualElement(square);
        Assertions.assertEquals(2, project.getElements().size());
        project.undoVisualElement();
        // Checking that the element has been removed
        Assertions.assertEquals(1, project.getElements().size());
        // Checking that it has been added to the redo stack
        Assertions.assertEquals(1, project.getRedoStack().size());
    }

    @Test
    void redoVisualElementTest() {
        AardSquare square = new AardSquare(10.0, 10.0, 2.0, false, true,
                Color.GREEN, Color.DARKOLIVEGREEN, 3.0);
        project.addVisualElement(square);
        project.undoVisualElement();
        Canvas canvas = new Canvas(500, 600);
        project.redoVisualElement(canvas.getGraphicsContext2D());
        // Checking that the element has been added to list of elements
        Assertions.assertEquals(2, project.getElements().size());
        // Checking that the element has been removed from the redo stack
        Assertions.assertEquals(0, project.getRedoStack().size());
        // Does NOT check whether the element has been drawn
    }

    @Test
    void toDictTest() {
        HashMap<String, Object> projectDict = project.toDict();
        List<HashMap<String, Object>> elementList = (List<HashMap<String, Object>>) projectDict.get("VisualElements");
        Assertions.assertEquals(1, elementList.size());

        // Getting the hashmap of the first element and checking its values
        HashMap<String, Object> firstElementDict = elementList.get(0);
        Assertions.assertTrue(firstElementDict.containsKey("Name"));
        Assertions.assertEquals("National Aardvark Day", firstElementDict.get("text"));
    }
}