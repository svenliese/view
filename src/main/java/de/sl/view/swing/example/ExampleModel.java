package de.sl.view.swing.example;

import de.sl.view.IView;
import de.sl.view.IViewModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SL
 */
public class ExampleModel implements IViewModel<Color> {

    private static final float CHANGE_PERCENTAGE = 0.05f;

    private Color bgColor = Color.BLACK;

    private List<IView<Color>> views = new ArrayList<>();

    private IView<Color> activeView;

    public void addView(IView<Color> view) {
        views.add(view);
    }

    public boolean greater() {
        if(activeView!=null) {
            final float newWidth = activeView.getWPercentage() + CHANGE_PERCENTAGE;
            final float newHeight = activeView.getHPercentage() + CHANGE_PERCENTAGE;
            if(newHeight+activeView.getYPercentage()<=1.0f && newWidth+activeView.getXPercentage()<=1.0f) {
                activeView.setWPercentage(newWidth);
                activeView.setHPercentage(newHeight);
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
                return;
            }
        }
    }

    @Override
    public boolean simulate(long now) {
        return true;
    }
}
