package org.openjfx;
public class Text implements VisualElement{
    private String fontFamily;
    private int fontSize;
    private String colour;

    public Text(){
        this.fontFamily = Settings.getFontFamily();
        this.fontSize = Settings.getFontSize();
        this.colour = Settings.getColour();
    }

    public void draw(){

    }
}
