package de.sl.view;

import java.util.List;

/**
 * @author SL
 */
public interface IModelListener<C> {

    void touchedOnViews(List<IView<C>> views);

    void touchedInBackground();
}
