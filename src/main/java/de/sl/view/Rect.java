package de.sl.view;

/**
 * @author SL
 */
public class Rect<C> extends ViewBase<C> {

    protected C color;
    protected C borderColor;
    protected float cornerPercentage;
    protected float borderPercentage;

    public Rect(C color, C borderColor, float cornerPercentage, float borderPercentage) {
        this.color = color;
        this.borderColor = borderColor;
        this.cornerPercentage = cornerPercentage;
        this.borderPercentage = borderPercentage;
    }

    public Rect(C color) {
        this(color, null, 0, 0);
    }

    public C getColor() {
        return color;
    }

    public void setColor(C color) {
        this.color = color;
    }

    public C getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(C borderColor) {
        this.borderColor = borderColor;
    }

    public float getCornerPercentage() {
        return cornerPercentage;
    }

    public void setCornerPercentage(float cornerPercentage) {
        this.cornerPercentage = cornerPercentage;
    }

    public float getBorderPercentage() {
        return borderPercentage;
    }

    public void setBorderPercentage(float borderPercentage) {
        this.borderPercentage = borderPercentage;
    }
}
