package de.sl.view;

/**
 * @author SL
 */
public class TextButton<C> extends SimpleText<C> {

    private C bgColor;
    private final LineProperties<C> border;

    public TextButton(String text, C textColor, C bgColor, LineProperties<C> border) {
        super(text, textColor);
        this.bgColor = bgColor;
        this.border = border;
    }

    public TextButton(String text, C textColor, C bgColor) {
        this(text, textColor, bgColor, null);
    }

    public C getBgColor() {
        return bgColor;
    }

    public void setBgColor(C color) {
        if(color==null) {
            throw new IllegalArgumentException(ERR_NULL_PARAM);
        }
        this.bgColor = color;
    }

    public C getBorderColor() {
        return border.getColor();
    }

    public void setBorderColor(C color) {
        if(color==null) {
            throw new IllegalArgumentException(ERR_NULL_PARAM);
        }
        this.border.setColor(color);
    }

    public LineProperties<C> getBorder() {
        return border;
    }
}
