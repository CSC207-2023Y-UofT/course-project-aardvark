package org.openjfx;

public class Settings{
    static String fontFamily;
    static int fontSize;
    // hex code
    static String colour;
    static int width;
    public Settings(){
        fontFamily = "Arial";
        fontSize = 12;
        colour = "ca8b29";
        width = 2;
    }

    public static void setWidth(int width) {
        Settings.width = width;
    }

    public static void setFontSize(int fontSize) {
        Settings.fontSize = fontSize;
    }

    public static int getFontSize() {
        return fontSize;
    }

    public static int getWidth() {
        return width;
    }

    public static String getColour() {
        return colour;
    }

    public static String getFontFamily() {
        return fontFamily;
    }
}