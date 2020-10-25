package de.sl.view;

/**
 * @author SL
 */
public class TextButton<C> extends SimpleText<C> {

    private C bgColor;
    private C borderColor;

    public TextButton(String text, C textColor, C bgColor, C borderColor) {
        super(text, textColor);
        this.bgColor = bgColor;
        this.borderColor = borderColor;
    }

    public C getBgColor() {
        return bgColor;
    }

    public void setBgColor(C bgColor) {
        this.bgColor = bgColor;
    }

    public C getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(C borderColor) {
        this.borderColor = borderColor;
    }
}
