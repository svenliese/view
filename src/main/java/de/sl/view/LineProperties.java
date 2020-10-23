package de.sl.view;

/**
 * @author SL
 */
public class LineProperties<C> {

    private C color;
    private float thickness;
    private boolean isPercentage;

    public LineProperties(C color, float thickness, boolean isPercentage) {
        this.color = color;
        this.thickness = thickness;
        this.isPercentage = isPercentage;
    }

    public C getColor() {
        return color;
    }

    public void setColor(C color) {
        this.color = color;
    }

    public float calculateThickness(float parentSize) {
        if(isPercentage) {
            return parentSize * thickness;
        }
        return thickness;
    }

    public float getThickness() {
        return thickness;
    }

    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    public boolean isPercentage() {
        return isPercentage;
    }

    public void setPercentage(boolean percentage) {
        isPercentage = percentage;
    }
}
