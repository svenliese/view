package de.sl.view;

/**
 * @author SL
 */
public class ViewBounds {

    private final float x;
    private final float y;
    private final float w;
    private final float h;

    public ViewBounds(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }
}
