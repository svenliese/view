package de.sl.view;

/**
 * @author SL
 */
public class Image<C, I> extends ViewBase<C> {

    public static final int USE_ORIGIN = 1;
    public static final int FIT_TO_BOX = 2;

    private I bitmap;
    private int alpha = 255;
    private int mode = USE_ORIGIN;

    public Image(I bitmap) {
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

    public I getBitmap() {
        return bitmap;
    }

    public void setBitmap(I bitmap) {
        this.bitmap = bitmap;
    }
}
