package de.sl.model;

import de.sl.view.IView;

/**
 * @author SL
 */
public interface IModelListener<C> {

    void clickedOnView(IView<C> view);

    void clickedInBackground();
}
