package text;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class AardVisualElement {
    public static ArrayList<AardCircle> circles = new ArrayList<>();
    public static ArrayList<AardSquare> squares = new ArrayList<>();

    public static void addCircle(AardCircle circle)
    {
        circles.add(circle);
    }

    public static void addSquare(AardSquare square)
    {
        squares.add(square);
    }

    public static void draw(GraphicsContext gc)
    {
        for (AardCircle circle : circles)
            circle.draw(gc);
        for (AardSquare square : squares)
            square.draw(gc);
    }

    public static void clear()
    {
        circles = new ArrayList<>();
    }
}
