package de.sl.view;

/**
 * @author SL
 */
public class LineProperties<C> {

    private C color;
    private float thickness;

    public LineProperties(C color, float thickness) {
        this.color = color;
        this.thickness = thickness;
    }

    public C getColor() {
        return color;
    }

    public void setColor(C color) {
        this.color = color;
    }

    public float getThickness() {
        return thickness;
    }

    public void setThickness(float thickness) {
        this.thickness = thickness;
    }
}
