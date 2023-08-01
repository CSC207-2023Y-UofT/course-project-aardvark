package text;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class WriteTextUseCase {
    private GraphicsContext gc;
    private MouseEvent event;

    public WriteTextUseCase(GraphicsContext gc, MouseEvent event) {
        this.gc = gc;
        this.event = event;
    }

    public AardText writeText(String input, Color color){
        double xValue = event.getX();
        double yValue = event.getY();
        AardText newText = new AardText(input, color.toString(),
                gc.getFont(), xValue, yValue);
        newText.draw(gc);
        return newText;
    }

}
