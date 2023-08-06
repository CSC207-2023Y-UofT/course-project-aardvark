package models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.nio.Buffer;
import java.util.HashMap;

public class AardWritableImage extends WritableImage implements VisualElement{

    /**
     * Constructs an empty image of the specified dimensions.
     * The image will initially be filled with transparent pixels.
     * Images constructed this way will always be readable and writable
     * so the corresponding getPixelReader() and getPixelWriter() will
     * always return valid objects.
     * The dimensions must both be positive numbers <code>(&gt;&nbsp;0)</code>.
     *
     * @param width  the desired width of the writable image
     * @param height the desired height of the desired image
     * @throws IllegalArgumentException if either dimension is negative or zero.
     */
    public AardWritableImage(int width, int height) {
        super(width, height);
    }

    /**
     * Constructs a {@code WritableImage} using the specified {@code PixelBuffer}.
     * The {@code Buffer} provided by the {@code PixelBuffer} will be used
     * directly as the pixel data for this image.
     * The {@code PixelBuffer} can be shared by multiple {@code WritableImage}s.
     * Images constructed this way are readable using {@code Image.getPixelReader()},
     * but are not writable using {@code WritableImage.getPixelWriter()}.
     *
     * @param pixelBuffer the {@code PixelBuffer} used to construct this image
     * @throws NullPointerException if {@code pixelBuffer} is {@code null}
     * @since 13
     */
    public AardWritableImage(PixelBuffer<? extends Buffer> pixelBuffer) {
        super(pixelBuffer);
    }

    /**
     * Constructs an image of the specified dimensions, initialized from
     * the indicated {@link PixelReader}.
     * The image will initially be filled with data returned from the
     * {@code PixelReader}.
     * If the {@code PixelReader} accesses a surface that does not contain
     * the necessary number of pixel rows and columns then an
     * {@link ArrayIndexOutOfBoundsException} will be thrown.
     * Images constructed this way will always be readable and writable
     * so the corresponding getPixelReader() and getPixelWriter() will
     * always return valid objects.
     * The dimensions must both be positive numbers <code>(&gt;&nbsp;0)</code>.
     *
     * @param reader the {@code PixelReader} to construct from
     * @param width  the desired width of the writable image and the
     *               width of the region to be read from the {@code reader}
     * @param height the desired height of the desired image and the
     *               width of the region to be read from the {@code reader}
     * @throws ArrayIndexOutOfBoundsException if the {@code reader} does
     *                                        not access a surface of at least the requested dimensions
     * @throws IllegalArgumentException       if either dimension is negative or zero.
     */
    public AardWritableImage(PixelReader reader, int width, int height) {
        super(reader, width, height);
    }

    /**
     * Constructs an image of the specified dimensions, initialized from
     * the indicated region of the {@link PixelReader}.
     * The image will initially be filled with data returned from the
     * {@code PixelReader} for the specified region.
     * If the {@code PixelReader} accesses a surface that does not contain
     * the necessary number of pixel rows and columns then an
     * {@link ArrayIndexOutOfBoundsException} will be thrown.
     * Images constructed this way will always be readable and writable
     * so the corresponding getPixelReader() and getPixelWriter() will
     * always return valid objects.
     * The dimensions must both be positive numbers <code>(&gt;&nbsp;0)</code>.
     *
     * @param reader the {@code PixelReader} to construct from
     * @param x      the X coordinate of the upper left corner of the region to
     *               read from the {@code reader}
     * @param y      the Y coordinate of the upper left corner of the region to
     *               read from the {@code reader}
     * @param width  the desired width of the writable image and the
     *               width of the region to be read from the {@code reader}
     * @param height the desired height of the desired image and the
     *               width of the region to be read from the {@code reader}
     * @throws ArrayIndexOutOfBoundsException if the {@code reader} does
     *                                        not access a surface containing at least the indicated region
     * @throws IllegalArgumentException       if either dimension is negative or zero.
     */
    public AardWritableImage(PixelReader reader, int x, int y, int width, int height) {
        super(reader, x, y, width, height);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(this, 0, 0);
    }

    @Override
    public HashMap<String, Object> toDict() {
        return null;
    }
}
