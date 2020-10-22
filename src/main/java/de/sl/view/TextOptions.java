package de.sl.view;

/**
 * @author SL
 */
public class TextOptions<C> extends ViewBase<C> {

    private C textColor;
    private C bgColor;
    private C borderColor;
    private float borderPercentage;
    private float cornerPercentage;
    private String name;
    private String[] options;
    private int selectedOptionIdx;

    public TextOptions(
        C textColor,
        C bgColor,
        C borderColor,
        float borderPercentage,
        float cornerPercentage,
        String name,
        String[] options,
        int selectedOptionIdx
    ) {
        this.textColor = textColor;
        this.bgColor = bgColor;
        this.borderColor = borderColor;
        this.borderPercentage = borderPercentage;
        this.cornerPercentage = cornerPercentage;
        this.name = name;
        this.options = options;
        this.selectedOptionIdx = selectedOptionIdx;
    }

    public C getTextColor() {
        return textColor;
    }

    public void setTextColor(C textColor) {
        this.textColor = textColor;
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

    public C getBgColor() {
        return bgColor;
    }

    public void setBgColor(C bgColor) {
        this.bgColor = bgColor;
    }

    public float getCornerPercentage() {
        return cornerPercentage;
    }

    public void setCornerPercentage(float cornerPercentage) {
        this.cornerPercentage = cornerPercentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int getSelectedOptionIdx() {
        return selectedOptionIdx;
    }

    public void setSelectedOptionIdx(int selectedOptionIdx) {
        this.selectedOptionIdx = selectedOptionIdx;
    }

    public int getOptionIdx(float xPercentage, float yPercentage) {
        if(getYPercentage()+getHPercentage()/2 < yPercentage && getYPercentage()+getHPercentage() > yPercentage) {
            float dim = getWPercentage()/((float)options.length);
            for(int i=0; i<options.length; i++) {
                if(getXPercentage()+(float)i*dim<xPercentage && getXPercentage()+(float)i*dim+dim>xPercentage) {
                    return i;
                }
            }
        }
        return -1;
    }
}
