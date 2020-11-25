package de.sl.view;

/**
 * @author SL
 */
public interface IView<C> {

    float getXPercentage();

    void setXPercentage(float xPercentage);

    float getYPercentage();

    void setYPercentage(float yPercentage);

    float getWPercentage();

    void setWPercentage(float wPercentage);

    float getHPercentage();

    void setHPercentage(float hPercentage);

    boolean isInside(float xPercentage, float yPercentage);

    boolean isVisible();

    void setVisible(boolean visible);

    boolean simulate(long now);

    Object getUserObject();

    void setUserObject(Object userObject);
}
