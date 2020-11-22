package de.sl.view;

import java.util.List;

/**
 * @author SL
 */
public interface IViewModel<C, I> {

    void setBgColor(C bgColor);

    C getBgColor();

    List<IView<C>> getViews();

    /**
     * @return true - model changed
     */
    boolean touchDown(float xPercent, float yPercent);

    /**
     * @return true - model changed
     */
    boolean simulate(long now);
}
