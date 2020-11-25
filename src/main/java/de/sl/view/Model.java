package de.sl.view;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SL
 */
public abstract class Model<C, I> {

    private final List<IView<C>> views = new ArrayList<>();

    private final List<IModelListener<C>> listeners = new ArrayList<>();

    private C bgColor;

    public void setBgColor(C bgColor) {
        this.bgColor = bgColor;
    }

    public C getBgColor() {
        return bgColor;
    }

    public void addView(IView<C> view) {
        views.add(view);
    }

    public List<IView<C>> getViews() {
        return views;
    }

    public void addModelListener(IModelListener<C> listener) {
        listeners.add(listener);
    }

    /**
     * @return true - model changed
     */
    public final boolean touchDown(float xPercent, float yPercent) {
        final List<IView<C>> affectedViews = new ArrayList<>();
        for(IView<C> view : views) {
            if(view.isVisible() && view.isInside(xPercent, yPercent)) {
                affectedViews.add(view);
            }
        }

        final boolean modified = handleTouchDown(affectedViews);

        if(affectedViews.isEmpty()) {
            for(IModelListener<C> listener : listeners) {
                listener.touchedInBackground();
            }
        } else {
            for(IModelListener<C> listener : listeners) {
                listener.touchedOnViews(views);
            }
        }

        return modified;
    }

    /**
     * @return true - model changed
     */
    public final boolean simulate(long now) {
        return true;
    }

    protected abstract boolean handleTouchDown(List<IView<C>> affectedViews);
}
