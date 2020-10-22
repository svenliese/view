package de.sl.view;

/**
 * @author SL
 */
public class Image<T, C> extends ViewBase<C> {

    public static final int XY_IS_MIDDLE = 1;
    public static final int CENTER_IN_BOX = 2;

    private T bitmap;
    private int alpha = 255;
    private int mode = XY_IS_MIDDLE;

    public Image(T bitmap) {
        this.bitmap = bitmap;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public T getBitmap() {
        return bitmap;
    }

    public void setBitmap(T bitmap) {
        this.bitmap = bitmap;
    }
}
