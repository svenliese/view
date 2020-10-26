package de.sl.model;

import de.sl.view.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SL
 */
public class ExampleModel<C, I> implements IViewModel<C, I> {

    private static final float CHANGE_PERCENTAGE = 0.05f;

    private C bgColor;

    private final List<IView<C>> views;

    private IView<C> activeView;

    private final Rect<C> activeViewBorder;

    private IModelListener<C> modelListener;

    public ExampleModel(IColorFactory<C> colorFactory, IImageFactory<I> imageFactory) {
        this.views = buildViews(colorFactory, imageFactory);

        bgColor = colorFactory.getBlack();

        activeViewBorder = new Rect<>(null, new LineProperties<>(colorFactory.getRed(), 2.0f, false));
        activeViewBorder.setVisible(false);
        views.add(activeViewBorder);
    }

    private List<IView<C>> buildViews(IColorFactory<C> colorFactory, IImageFactory<I> imageFactory) {
        final List<IView<C>> viewList = new ArrayList<>();

        Rect<C> rect = new Rect<>(
            colorFactory.getWhite(),
            new LineProperties<>(colorFactory.getLightGray(), 0.1f, true)
        );
        rect.setXPercentage(0.1f);
        rect.setYPercentage(0.1f);
        rect.setWPercentage(0.25f);
        rect.setHPercentage(0.25f);
        viewList.add(rect);

        rect = new Rect<>(colorFactory.getColor(100, 10, 200));
        rect.setXPercentage(0.2f);
        rect.setYPercentage(0.2f);
        rect.setWPercentage(0.25f);
        rect.setHPercentage(0.25f);
        viewList.add(rect);

        SimpleText<C> simpleText = new SimpleText<>("simple text H left", colorFactory.getWhite());
        simpleText.setXPercentage(0.2f);
        simpleText.setYPercentage(0.5f);
        simpleText.setWPercentage(0.25f);
        simpleText.setHPercentage(0.1f);
        simpleText.setTextSize(12);
        simpleText.setHAlign(SimpleText.LEFT);
        simpleText.setOrientation(SimpleText.HORIZONTAL);
        viewList.add(simpleText);

        simpleText = new SimpleText<>("simple text H center", colorFactory.getWhite());
        simpleText.setXPercentage(0.2f);
        simpleText.setYPercentage(0.6f);
        simpleText.setWPercentage(0.25f);
        simpleText.setHPercentage(0.1f);
        simpleText.setTextSize(14);
        simpleText.setHAlign(SimpleText.CENTER);
        simpleText.setOrientation(SimpleText.HORIZONTAL);
        viewList.add(simpleText);

        simpleText = new SimpleText<>("simple text V left", colorFactory.getWhite());
        simpleText.setXPercentage(0.05f);
        simpleText.setYPercentage(0.5f);
        simpleText.setWPercentage(0.05f);
        simpleText.setHPercentage(0.3f);
        simpleText.setTextSize(12);
        simpleText.setHAlign(SimpleText.LEFT);
        simpleText.setOrientation(SimpleText.VERTICAL);
        viewList.add(simpleText);

        simpleText = new SimpleText<>("simple text V center", colorFactory.getWhite());
        simpleText.setXPercentage(0.1f);
        simpleText.setYPercentage(0.5f);
        simpleText.setWPercentage(0.05f);
        simpleText.setHPercentage(0.3f);
        simpleText.setTextSize(12);
        simpleText.setHAlign(SimpleText.CENTER);
        simpleText.setOrientation(SimpleText.VERTICAL);
        viewList.add(simpleText);

        TextButton<C> textButton = new TextButton<>("button", colorFactory.getBlue(), colorFactory.getLightGray(), colorFactory.getBlue());
        textButton.setXPercentage(0.4f);
        textButton.setYPercentage(0.05f);
        textButton.setWPercentage(0.1f);
        textButton.setHPercentage(0.05f);
        textButton.setTextSize(16);
        viewList.add(textButton);

        de.sl.view.Image<C, I> image = new de.sl.view.Image<>(imageFactory.getImage("images/elfe.png"));
        image.setXPercentage(0.6f);
        image.setYPercentage(0.05f);
        image.setWPercentage(0.2f);
        image.setHPercentage(0.2f);
        viewList.add(image);

        image = new de.sl.view.Image<>(imageFactory.getImage("images/elfe.png"));
        image.setXPercentage(0.6f);
        image.setYPercentage(0.3f);
        image.setWPercentage(0.2f);
        image.setHPercentage(0.2f);
        image.setMode(Image.FIT_TO_BOX);
        viewList.add(image);

        return viewList;
    }

    private void setBorderToView() {
        activeViewBorder.setXPercentage(activeView.getXPercentage());
        activeViewBorder.setYPercentage(activeView.getYPercentage());
        activeViewBorder.setWPercentage(activeView.getWPercentage());
        activeViewBorder.setHPercentage(activeView.getHPercentage());
    }

    public void setModelListener(IModelListener<C> modelListener) {
        this.modelListener = modelListener;
    }

    public void greater() {
        if(activeView!=null) {
            final float newWidth = activeView.getWPercentage() + CHANGE_PERCENTAGE;
            final float newHeight = activeView.getHPercentage() + CHANGE_PERCENTAGE;
            activeView.setWPercentage(newWidth);
            activeView.setHPercentage(newHeight);
            setBorderToView();
        }
    }

    public void smaller() {
        if(activeView!=null) {
            final float newWidth = activeView.getWPercentage() - CHANGE_PERCENTAGE;
            final float newHeight = activeView.getHPercentage() - CHANGE_PERCENTAGE;
            if(newHeight>0.0f && newWidth>0.0f) {
                activeView.setWPercentage(newWidth);
                activeView.setHPercentage(newHeight);
                setBorderToView();
            }
        }
    }

    public void moveLeft() {
        if(activeView!=null) {
            final float newX = activeView.getXPercentage() - CHANGE_PERCENTAGE;
            activeView.setXPercentage(newX);
            setBorderToView();
        }
    }

    public void moveRight() {
        if(activeView!=null) {
            final float newX = activeView.getXPercentage() + CHANGE_PERCENTAGE;
            activeView.setXPercentage(newX);
            setBorderToView();
        }
    }

    public void moveUp() {
        if(activeView!=null) {
            final float newY = activeView.getYPercentage() - CHANGE_PERCENTAGE;
            activeView.setYPercentage(newY);
            setBorderToView();
        }
    }

    public void moveDown() {
        if(activeView!=null) {
            final float newY = activeView.getYPercentage() + CHANGE_PERCENTAGE;
            activeView.setYPercentage(newY);
            setBorderToView();
        }
    }

    @Override
    public void setBgColor(C bgColor) {
        this.bgColor = bgColor;
    }

    @Override
    public C getBgColor() {
        return bgColor;
    }

    @Override
    public List<IView<C>> getViews() {
        return views;
    }

    @Override
    public void touchDown(float xPercent, float yPercent) {
        for(IView<C> view : views) {
            if(view.isInside(xPercent, yPercent)) {
                activeView = view;
                setBorderToView();
                activeViewBorder.setVisible(true);

                if(modelListener!=null) {
                    modelListener.clickedOnView(view);
                }

                return;
            }
        }
        activeViewBorder.setVisible(false);
        activeView = null;

        if(modelListener!=null) {
            modelListener.clickedInBackground();
        }
    }

    @Override
    public boolean simulate(long now) {
        return true;
    }
}
