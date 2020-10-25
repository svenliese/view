package de.sl.view;

/**
 * @author SL
 */
public class SimpleText<C> extends ViewBase<C> {

    public static final int CENTER = 0;
    public static final int LEFT = 1;

    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;

    private String text;
    private C color;
    private int hAlign = CENTER;
    private int size = 8;

    private int orientation = HORIZONTAL;

    public SimpleText(String text, C color) {
        this.text = text;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if(text==null) {
            throw new IllegalArgumentException(ERR_NULL_PARAM);
        }
        this.text = text;
    }

    public C getColor() {
        return color;
    }

    public void setColor(C color) {
        if(color==null) {
            throw new IllegalArgumentException(ERR_NULL_PARAM);
        }
        this.color = color;
    }

    public int getHAlign() {
        return hAlign;
    }

    public void setHAlign(int hAlign) {
        if(hAlign!=CENTER && hAlign!=LEFT) {
            throw new IllegalArgumentException(ERR_UNKNOWN_VALUE+" "+hAlign);
        }
        this.hAlign = hAlign;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        if(orientation!=HORIZONTAL && orientation!=VERTICAL) {
            throw new IllegalArgumentException(ERR_UNKNOWN_VALUE+" "+orientation);
        }
        this.orientation = orientation;
    }

    public int getTextSize() {
        return size;
    }

    public void setTextSize(int size) {
        if(size<=0) {
            throw new IllegalArgumentException(ERR_INVALID_VALUE+" "+size);
        }
        this.size = size;
    }
}
