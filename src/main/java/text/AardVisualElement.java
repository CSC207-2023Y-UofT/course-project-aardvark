package text;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class AardVisualElement {
    private static ArrayList<AardCircle> circles = new ArrayList<>();

    public static void addCircle(AardCircle circle)
    {
        circles.add(circle);
    }

    public static void draw(GraphicsContext gc, boolean isFill, boolean isStroke)
    {
        for (AardCircle circle : circles)
            circle.draw(gc, isFill, isStroke);
    }
}
