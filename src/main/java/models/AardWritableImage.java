package models;

import java.awt.geom.Point2D;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.util.Pair;


import javax.imageio.ImageIO;
import javax.lang.model.type.UnionType;
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
        HashMap<String, Object> dict = new HashMap<>();
        dict.put("Name", "AardWritableImage");
        dict.put("Points", this.toPoint2D());
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
        BufferedImage image = new BufferedImage((int) this.getWidth(), (int) this.getHeight(), BufferedImage.TYPE_INT_RGB);
        SwingFXUtils.fromFXImage(this, image);
        Color colorAdd;
        for (int x = 0; x < image.getWidth(); x++) {
            ArrayList<Object> temp = new ArrayList<>();
            for (int y = 0; y < image.getHeight(); y++) {
                int color = image.getRGB(x, y);
                ArrayList<Object> info = new ArrayList<>();
                colorAdd = convertRGBToColor(color);
                // Create a Point2D.Double object with grayscale value as the y-coordinate
                info.add(x);
                info.add(y);
                info.add(colorAdd.toString());
                temp.add(info);
            } ret.add(temp);
        } return ret;
    }

    public static AardWritableImage fromDict(HashMap<String, Object> curr) {
        ArrayList<ArrayList<Object>> points = (ArrayList<ArrayList<Object>>) curr.get("Points");
        BufferedImage image = new BufferedImage(points.size(), points.get(0).size(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < points.size(); i++) {
            ArrayList<Object> temp = points.get(i);
            for (int j = 0; j < points.get(i).size(); j++) {
                ArrayList<Object> info = (ArrayList<Object>) temp.get(j);
                long x = (long) info.get(0);
                long y = (long) info.get(1);
                String colorStr = (String) info.get(2);
                Color color = Color.valueOf(colorStr);
                int rgb = convertColorToRGB(color);
                image.setRGB((int) x, (int) y, rgb);
            }
        }
        AardWritableImage fxImage = new AardWritableImage(image.getWidth(), image.getHeight());
        SwingFXUtils.toFXImage(image, fxImage);
        return fxImage;
    }

    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }
}