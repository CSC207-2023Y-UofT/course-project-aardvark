package text;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

/**
 * The ChangeSettingsUseCase class represents the use case for changing any type of setting.
 * Currently only for text-related settings.
 */
public class ChangeSettingsUseCase {

    /** The GraphicsContext whose properties can be modified, with changes reflected to a Canvas. */
    private GraphicsContext gc;

    /**
     * Creates a new ChangeSettingsUseCase object.
     */
    public ChangeSettingsUseCase(GraphicsContext gc) {
        this.gc = gc;
    }

    /**
     * Changes the font family linked to the GraphicsContext.
     * @param fontComboBox The ComboBox from which we can extract the selected font family to change to.
     //* @param sizeLabelProperty The IntegerProperty from which we can extract the current font size.
     */
    public void changeFontFamily(ComboBox<String> fontComboBox, TextField sizeField){
        String fontFamily = fontComboBox.getValue();
        int fontSize = Integer.parseInt(sizeField.getText());
        Font newFont = new Font(fontFamily, fontSize);
        gc.setFont(newFont);
    }

    /**
     * Changes the font size linked to the GraphicsContext through a text field.
     //* @param sizeLabelProperty The IntegerProperty from which we can extract the current font size.
     * @param sizeField The TextField from which we can extract the new font size.
     */
    public void changeFontSize(TextField sizeField){
        Font currentFont = gc.getFont();
        String currentFamily = currentFont.getFamily();
        int fontSize = Integer.parseInt(sizeField.getText());
        Font newFont = new Font(currentFamily, fontSize);
        gc.setFont(newFont);
    }

    /**
     * Increases/decrease the font size linked to the GraphicsContext by 1.
     * Precondition: operation is "+" or "-".
     //* @param sizeLabelProperty The IntegerProperty from which we can extract the current font size.
     * @param sizeField The TextField that will be changed to reflect the new font size.
     * @param operation A String that indicates whether to increase or decrease the font size:
     *                  + for increase and - for decrease.
     */
    public void changeFontSizeByOne(TextField sizeField, String operation){
        Font currentFont = gc.getFont();
        String currentFamily = currentFont.getFamily();
        int currentSize = (int) currentFont.getSize();
        int newSize;
        if (operation.equals("+")){
            newSize = currentSize + 1;
        }
        else {
            newSize = currentSize - 1;
        }
        Font newFont = new Font(currentFamily, newSize);
        gc.setFont(newFont);
        sizeField.setText(Integer.toString(newSize));
    }

    /**
     * Changes the font colour linked to the GraphicsContext through a ColorPicker.
     * @param colorPicker The ColorPicker from which we can extract the color that we will change to.
     */
    public void changeFontColor(ColorPicker colorPicker){
        gc.setFill(colorPicker.getValue());
    }

}
