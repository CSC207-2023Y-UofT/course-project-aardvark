package text;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import org.openjfx.VisualElement;

public class AardText implements VisualElement {
    private String text;
    private String fontFamily;
    private double fontSize;
    // private String colour;

    private double[] coordinates;

    public AardText(String text, Font font, double x, double y){
        this.fontFamily = font.getFamily();
        this.fontSize = font.getSize();
        this.text = text;
        coordinates = new double[2];
        coordinates[0] = x;
        coordinates[1] = y;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFont(Font.font(fontFamily, fontSize));
        gc.fillText(text, coordinates[0], coordinates[1]);
    }

}
