package de.sl.view;

import java.util.List;

/**
 * @author SL
 */
public interface IViewModel<C, I> {

    void setBgColor(C bgColor);

    C getBgColor();

    List<IView<C>> getViews();

    // todo: move to different interface ??
    void touchDown(float xPercent, float yPercent);

    // todo: move to different interface ??
    boolean simulate(long now);
}
