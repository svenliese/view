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

    private Color bgColor = Color.BLACK;

    private IView<Color> view;

    public ExampleModel(IView<Color> view) {
        this.view = view;
    }

    public boolean greater() {
        final float newWidth = view.getWPercentage()*1.1f;
        final float newHeight = view.getHPercentage()*1.1f;
        if(newHeight+view.getYPercentage()<1.0f && newWidth+view.getXPercentage()<1.0f) {
            view.setWPercentage(newWidth);
            view.setHPercentage(newHeight);
            return true;
        }
        return false;
    }

    public boolean smaller() {
        final float newWidth = view.getWPercentage()*0.9f;
        final float newHeight = view.getHPercentage()*0.9f;
        if(newHeight>0.01f && newWidth>0.01f) {
            view.setWPercentage(newWidth);
            view.setHPercentage(newHeight);
            return true;
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
        final List<IView<Color>> viewList = new ArrayList<>(1);
        viewList.add(view);
        return viewList;
    }

    @Override
    public void touchDown(float xPercent, float yPercent) {

    }

    @Override
    public boolean simulate(long now) {
        return true;
    }
}
