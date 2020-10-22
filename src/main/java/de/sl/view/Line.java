package de.sl.view;

/**
 * @author SL
 */
public class Line<C> extends ViewBase<C> {

    public static final int CAP_ROUND = 1;
    public static final int CAP_SQUARE = 2;
    public static final int CAP_BUTT = 3;

    private int thickness;
    private int color;
    private int capMode;

    public Line(int thickness, int color) {
        this(thickness, color, CAP_ROUND);
    }

    public Line(int thickness, int color, int capMode) {
        this.thickness = thickness;
        this.color = color;
        this.capMode = capMode;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getCapMode() {
        return capMode;
    }

    public void setCapMode(int capMode) {
        this.capMode = capMode;
    }
}
