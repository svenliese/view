package de.sl.view;

import java.util.List;

/**
 * @author SL
 */
public interface IViewModelListener<C> {

    void touchedOnViews(List<IView<C>> views);

    void touchedInBackground();
}
