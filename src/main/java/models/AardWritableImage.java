package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

import java.util.HashMap;

/**
 The AardWritableImage class represents a writable image that can be drawn on a JavaFX GraphicsContext.

 It extends the WritableImage class and implements the VisualElement interface, allowing it to be used

 as a graphical element in projects.
 */
public class AardWritableImage extends WritableImage implements VisualElement{

    /**
     Constructs an empty image of the specified dimensions.

     The image will initially be filled with transparent pixels.

     Images constructed this way will always be readable and writable,
     so the corresponding getPixelReader() and getPixelWriter() will
     always return valid objects.

     The dimensions must both be positive numbers (> 0).

     @param width the desired width of the writable image.
     @param height the desired height of the desired image.
     @throws IllegalArgumentException if either dimension is negative or zero.
     */
    public AardWritableImage(int width, int height) {
        super(width, height);
    }

    /**
     Draw the AardWritableImage on the specified GraphicsContext.
     @param gc The GraphicsContext on which the AardWritableImage should be drawn.
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(this, 0, 0);
    }

    /**
     Convert the AardWritableImage properties to a dictionary format.

     @return Null since this class does not currently provide dictionary representation.
     */
    @Override
    public HashMap<String, Object> toDict() {
        return null;
    }
}
