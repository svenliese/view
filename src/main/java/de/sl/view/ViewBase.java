package de.sl.view;

/**
 * @author SL
 */
public abstract class ViewBase<C> implements IView<C> {

    public static final String ERR_NULL_PARAM = "parameter should not be null";
    public static final String ERR_UNKNOWN_VALUE = "unknown value";
    public static final String ERR_INVALID_VALUE = "invalid value";

    protected float xPercentage;
    protected float yPercentage;
    protected float wPercentage;
    protected float hPercentage;

    protected boolean visible = true;

    protected Object userObject;

    @Override
    public Object getUserObject() {
        return userObject;
    }

    @Override
    public void setUserObject(Object userObject) {
        this.userObject = userObject;
    }

    @Override
    public float getXPercentage() {
        return xPercentage;
    }

    @Override
    public void setXPercentage(float xPercentage) {
        this.xPercentage = xPercentage;
    }

    @Override
    public float getYPercentage() {
        return yPercentage;
    }

    @Override
    public void setYPercentage(float yPercentage) {
        this.yPercentage = yPercentage;
    }

    @Override
    public float getWPercentage() {
        return wPercentage;
    }

    @Override
    public void setWPercentage(float wPercentage) {
        this.wPercentage = wPercentage;
    }

    @Override
    public float getHPercentage() {
        return hPercentage;
    }

    @Override
    public void setHPercentage(float hPercentage) {
        this.hPercentage = hPercentage;
    }

    @Override
    public boolean isInside(float xPercentage, float yPercentage) {
        return
            xPercentage>=this.xPercentage && xPercentage<this.xPercentage+this.wPercentage &&
            yPercentage>=this.yPercentage && yPercentage<this.yPercentage+this.hPercentage;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean simulate(long now) {
        return false;
    }
}
