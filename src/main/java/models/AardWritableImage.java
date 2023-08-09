package models;

import java.awt.geom.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.util.Pair;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 The AardWritableImage class represents a writable image that can be drawn on a JavaFX GraphicsContext.

 It extends the WritableImage class and implements the VisualElement interface, allowing it to be used

 as a graphical element in projects.
 */
public class AardWritableImage extends WritableImage implements VisualElement{
    private BufferedImage bufferedImage;
    // private ArrayList<Point2D.Double> path = new ArrayList<>();
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
    public AardWritableImage(int width, int height, BufferedImage b) {
        super(width, height);
        this.bufferedImage = b;
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
        HashMap<String, Object> dict = new HashMap<>();
        dict.put("AardWritableImage", this.toPoint2D());
        return dict;
    }

    public static Color convertRGBToColor(int rgb) {
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        double redNormalized = red / 255.0;
        double greenNormalized = green / 255.0;
        double blueNormalized = blue / 255.0;

        return new Color((float) redNormalized, (float) greenNormalized, (float) blueNormalized, 1.0F);
    }

    public static int convertColorToRGB(Color color) {
        int red = (int) (color.getRed() * 255);
        int green = (int) (color.getGreen() * 255);
        int blue = (int) (color.getBlue() * 255);

        int rgb = (red << 16) | (green << 8) | blue;

        return rgb;
    }

    public ArrayList<ArrayList<Object>> toPoint2D() {
        ArrayList<ArrayList<Object>> ret = new ArrayList<>();
        BufferedImage image = bufferedImage;
        Color colorAdd;
        for (int x = 0; x < image.getWidth(); x++) {
            ArrayList<Object> temp = new ArrayList<>();
            for (int y = 0; y < image.getHeight(); y++) {
                int color = image.getRGB(x, y);
                colorAdd = convertRGBToColor(color);
                // Create a Point2D.Double object with grayscale value as the y-coordinate
                ArrayList<Integer> xy = new ArrayList<>();
                xy.add(x);
                xy.add(y);
                temp.add(xy);
                temp.add(colorAdd.toString());
            } ret.add(temp);
        } return ret;
    }

    public static AardWritableImage fromDict(HashMap<String, ArrayList<ArrayList<Object>>> curr) {
        ArrayList<ArrayList<Object>> points = curr.get("AardWritableImage");
        BufferedImage image = new BufferedImage(points.size(), points.get(0).size(), BufferedImage.TYPE_INT_RGB);
        for (ArrayList<Object> arr : points) {
            ArrayList<Integer> xy = (ArrayList<Integer>) arr.get(0);
            int x = xy.get(0);
            int y = xy.get(1);
            String colorStr = (String) arr.get(1);
            Color color = Color.valueOf(colorStr);
            int rgb = convertColorToRGB(color);
            image.setRGB(x, y, rgb);
        } return new AardWritableImage(points.size(), points.get(0).size(), image);
    }

    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }
}