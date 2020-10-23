package de.sl.view;

/**
 * @author SL
 */
public class Rect<C> extends ViewBase<C> {

    protected C color;
    protected LineProperties<C> border;

    public Rect(C color, LineProperties<C> border) {
        this.color = color;
        this.border = border;
    }

    public Rect(C color) {
        this(color, null);
    }

    public C getColor() {
        return color;
    }

    public void setColor(C color) {
        this.color = color;
    }

    public LineProperties<C> getBorder() {
        return border;
    }

    public void setBorder(LineProperties<C> border) {
        this.border = border;
    }
}
