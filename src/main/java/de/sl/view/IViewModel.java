package de.sl.view;

import java.util.List;

/**
 * @author SL
 */
public interface IViewModel<C> {

    void setBgColor(C bgColor);

    C getBgColor();

    List<IViewType> getViewTypes();
    
    List<IView<C>> getViews();

    // todo: move to different interface ??
    void touchDown(float xPercent, float yPercent);

    // todo: move to different interface ??
    boolean simulate(long now);
}
