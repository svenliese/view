package de.sl.view.swing.example;

import de.sl.view.IView;
import de.sl.view.IViewModel;
import de.sl.view.LineProperties;
import de.sl.view.Rect;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SL
 */
public class ExampleModel implements IViewModel<Color> {

    private static final float CHANGE_PERCENTAGE = 0.05f;

    private Color bgColor = Color.BLACK;

    private final List<IView<Color>> views;

    private IView<Color> activeView;

    private final Rect<Color> activeViewBorder;

    public ExampleModel(List<IView<Color>> views) {
        this.views = views;

        activeViewBorder = new Rect<>(null, new LineProperties<>(Color.RED, 2.0f));
        activeViewBorder.setVisible(false);
        views.add(activeViewBorder);
    }

    private void setBorderToView() {
        activeViewBorder.setXPercentage(activeView.getXPercentage());
        activeViewBorder.setYPercentage(activeView.getYPercentage());
        activeViewBorder.setWPercentage(activeView.getWPercentage());
        activeViewBorder.setHPercentage(activeView.getHPercentage());
    }

    public boolean greater() {
        if(activeView!=null) {
            final float newWidth = activeView.getWPercentage() + CHANGE_PERCENTAGE;
            final float newHeight = activeView.getHPercentage() + CHANGE_PERCENTAGE;
            if(newHeight+activeView.getYPercentage()<=1.0f && newWidth+activeView.getXPercentage()<=1.0f) {
                activeView.setWPercentage(newWidth);
                activeView.setHPercentage(newHeight);
                setBorderToView();
                return true;
            }
        }
        return false;
    }

    public boolean smaller() {
        if(activeView!=null) {
            final float newWidth = activeView.getWPercentage() - CHANGE_PERCENTAGE;
            final float newHeight = activeView.getHPercentage() - CHANGE_PERCENTAGE;
            if (newHeight > 0.0f && newWidth > 0.0f) {
                activeView.setWPercentage(newWidth);
                activeView.setHPercentage(newHeight);
                setBorderToView();
                return true;
            }
        }
        return false;
    }

    public boolean moveLeft() {
        if(activeView!=null) {
            final float newX = activeView.getXPercentage() - CHANGE_PERCENTAGE;
            if (newX >= 0.0f) {
                activeView.setXPercentage(newX);
                setBorderToView();
                return true;
            }
        }
        return false;
    }

    public boolean moveRight() {
        if(activeView!=null) {
            final float newX = activeView.getXPercentage() + CHANGE_PERCENTAGE;
            if (newX + activeView.getWPercentage() <= 1.0f) {
                activeView.setXPercentage(newX);
                setBorderToView();
                return true;
            }
        }
        return false;
    }

    public boolean moveUp() {
        if(activeView!=null) {
            final float newY = activeView.getYPercentage() - CHANGE_PERCENTAGE;
            if (newY >= 0.0f) {
                activeView.setYPercentage(newY);
                setBorderToView();
                return true;
            }
        }
        return false;
    }

    public boolean moveDown() {
        if(activeView!=null) {
            final float newY = activeView.getYPercentage() + CHANGE_PERCENTAGE;
            if (newY + activeView.getHPercentage() <= 1.0f) {
                activeView.setYPercentage(newY);
                setBorderToView();
                return true;
            }
        }
        return false;
    }

    @Override
    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    @Override
    public Color getBgColor() {
        return bgColor;
    }

    @Override
    public List<IView<Color>> getViews() {
        return views;
    }

    @Override
    public void touchDown(float xPercent, float yPercent) {
        for(IView<Color> view : views) {
            if(view.isInside(xPercent, yPercent)) {
                activeView = view;
                setBorderToView();
                activeViewBorder.setVisible(true);
                return;
            }
        }
    }

    @Override
    public boolean simulate(long now) {
        return true;
    }
}
