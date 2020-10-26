package de.sl.view;

/**
 * @author SL
 */
public class Line<C> extends ViewBase<C> {

    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;

    private final LineProperties<C> properties;

    private int mode = HORIZONTAL;

    public Line(C color, float thickness) {
        this.properties = new LineProperties<>(color, thickness, false);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public float getThickness() {
        return properties.getThickness();
    }

    public void setThickness(float thickness) {
        properties.setThickness(thickness);
    }

    public C getColor() {
        return properties.getColor();
    }

    public void setColor(C color) {
        properties.setColor(color);
    }

    public LineProperties<C> getProperties() {
        return properties;
    }
}
