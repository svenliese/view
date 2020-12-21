package de.sl.view;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SL
 */
public abstract class ViewModel<C, I> {

    private final List<IView<C>> views = new ArrayList<>();

    private final List<IModelListener<C>> listeners = new ArrayList<>();

    private C bgColor;

    private long lastTouchDown = -1;

    private long clickThreshold = 500;

    public long getClickThreshold() {
        return clickThreshold;
    }

    public void setClickThreshold(long clickThreshold) {
        this.clickThreshold = clickThreshold;
    }

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
        lastTouchDown = System.currentTimeMillis();
        return false;
    }

    /**
     * @return true - model changed
     */
    public boolean touchUp(float xPercent, float yPercent) {
        final long touchDuration = System.currentTimeMillis() - lastTouchDown;

        final List<IView<C>> affectedViews = new ArrayList<>();
        for(IView<C> view : views) {
            if(view.isVisible() && view.isInside(xPercent, yPercent)) {
                affectedViews.add(view);
            }
        }

        final boolean modified;
        if(touchDuration < clickThreshold) {
            modified = handleClick(affectedViews);
        } else {
            modified = handleTouchDown(affectedViews);
        }

        if(modified) {
            if (affectedViews.isEmpty()) {
                for (IModelListener<C> listener : listeners) {
                    listener.touchedInBackground();
                }
            } else {
                for (IModelListener<C> listener : listeners) {
                    listener.touchedOnViews(views);
                }
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

    protected abstract boolean handleClick(List<IView<C>> affectedViews);

    protected abstract boolean handleTouchDown(List<IView<C>> affectedViews);
}
