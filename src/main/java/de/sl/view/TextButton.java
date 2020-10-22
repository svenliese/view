package de.sl.view;

/**
 * @author SL
 */
public class TextButton<C> extends ViewBase<C> {

    private String text;
    private C textColor;
    private C bgColor;
    private C borderColor;

    public TextButton(String text, C textColor, C bgColor, C borderColor) {
        this.text = text;
        this.textColor = textColor;
        this.bgColor = bgColor;
        this.borderColor = borderColor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public C getTextColor() {
        return textColor;
    }

    public void setTextColor(C textColor) {
        this.textColor = textColor;
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
