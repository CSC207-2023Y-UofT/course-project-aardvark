package text;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * The WriteTextUseCase class represents the use case for writing text onto the canvas.
 */
public class WriteTextUseCase {

    /** The GraphicsContext that will be drawn on with changes reflected to a Canvas. */
    private GraphicsContext gc;

    /** The event that resulted in this use case being called. */
    private MouseEvent event;

    /**
     * Creates a new WriteTextUseCase object.
     */
    public WriteTextUseCase(GraphicsContext gc, MouseEvent event) {
        this.gc = gc;
        this.event = event;
    }

    /**
     * Writes the text onto the canvas by creating an AardText object and calling its draw method.
     * @param input The text to be drawn on the screen.
     * @param color The color of the specified text.
     * @return The AardText object created and drawn.
     */
    public AardText writeText(String input, Color color){
        double xValue = event.getX();
        double yValue = event.getY();
        AardText newText = new AardText(input, color.toString(),
                gc.getFont(), xValue, yValue);
        newText.draw(gc);
        return newText;
    }

}
