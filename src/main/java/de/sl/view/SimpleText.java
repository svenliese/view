package de.sl.view;

/**
 * @author SL
 */
public class SimpleText<C> extends ViewBase<C> {

    public static final int X_IS_MIDDLE = 0;

    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;

    private String text;
    private C color;
    private int hAlign = X_IS_MIDDLE;

    private int orientation = HORIZONTAL;

    public SimpleText(String text, C color) {
        this.text = text;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public C getColor() {
        return color;
    }

    public void setColor(C color) {
        this.color = color;
    }

    public int getHAlign() {
        return hAlign;
    }

    public void setHAlign(int hAlign) {
        this.hAlign = hAlign;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}
