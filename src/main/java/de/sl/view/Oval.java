package de.sl.view;

/**
 * @author SL
 */
public class Oval<C> extends ViewBase<C> {

    private C color;
    private C borderColor;
    private float borderPercentage;

    public Oval(C color, C borderColor, float borderPercentage) {
        this.color = color;
        this.borderColor = borderColor;
        this.borderPercentage = borderPercentage;
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

    public float getBorderPercentage() {
        return borderPercentage;
    }

    public void setBorderPercentage(float borderPercentage) {
        this.borderPercentage = borderPercentage;
    }
}
