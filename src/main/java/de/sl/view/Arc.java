package de.sl.view;

/**
 * @author SL
 */
public class Arc<C> extends ViewBase<C> {

    private C color;
    private C borderColor;
    private float borderPercentage;
    private float startArc;
    private float arcSize;

    public Arc(C color, C borderColor, float borderPercentage, float startArc, float arcSize) {
        this.color = color;
        this.borderColor = borderColor;
        this.borderPercentage = borderPercentage;
        this.startArc = startArc;
        this.arcSize = arcSize;
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

    public float getStartArc() {
        if(animation instanceof CirclingAnimation) {
            return ((CirclingAnimation)animation).getStartArc();
        }
        return startArc;
    }

    public void setStartArc(float startArc) {
        this.startArc = startArc;
    }

    public float getArcSize() {
        return arcSize;
    }

    public void setArcSize(float arcSize) {
        this.arcSize = arcSize;
    }
}
