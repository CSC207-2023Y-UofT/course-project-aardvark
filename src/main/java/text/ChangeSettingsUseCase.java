package text;

import javafx.beans.property.IntegerProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class ChangeSettingsUseCase {
    private GraphicsContext gc;
    private String fontFamily;
    private int fontSize;
    private Color color;

    public ChangeSettingsUseCase(GraphicsContext gc) {
        this.gc = gc;
    }

    public ChangeSettingsUseCase(GraphicsContext gc, String fontFamily, int fontSize) {
        this.gc = gc;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
    }

    public ChangeSettingsUseCase(GraphicsContext gc, Color color) {
        this.gc = gc;
        this.color = color;
    }

    public void changeFontFamily(){
        Font newFont = new Font(fontFamily, fontSize);
        gc.setFont(newFont);
    }

    public void changeFontSize(IntegerProperty sizeLabelProperty){
        Font newFont = new Font(fontFamily, fontSize);
        sizeLabelProperty.set(fontSize);
        gc.setFont(newFont);
    }

    public void incrementFontSize(IntegerProperty sizeLabelProperty, TextField sizeField, String operation){
        Font currentFont = gc.getFont();
        setFontFamily(currentFont.getFamily());
        if (operation.equals("+")){
            setFontSize((int) currentFont.getSize() + 1);
        }
        else {
            setFontSize((int) currentFont.getSize() - 1);
        }
        changeFontSize(sizeLabelProperty);
        sizeField.setText(Integer.toString(fontSize));
    }

    public void changeFontColor(ColorPicker colorPicker){
        gc.setFill(colorPicker.getValue());
    }

    private void setFontFamily(String family){
        this.fontFamily = family;
    }

    private void setFontSize(int size){
        this.fontSize = size;
    }
}
