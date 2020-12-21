package de.sl.view;

import java.util.List;

/**
 * @author SL
 */
public class ExampleViewModel<C, I> extends ViewModel<C, I> {

    private static final float CHANGE_PERCENTAGE = 0.05f;

    private IView<C> activeView;

    private final Rect<C> activeViewBorder;

    public ExampleViewModel(IColorFactory<C> colorFactory, IImageFactory<I> imageFactory) {
        buildViews(colorFactory, imageFactory);

        setBgColor(colorFactory.getBlack());

        activeViewBorder = new Rect<>(null, new LineProperties<>(colorFactory.getRed(), 2.0f, false));
        activeViewBorder.setVisible(false);
        addView(activeViewBorder);
    }

    private void buildViews(IColorFactory<C> colorFactory, IImageFactory<I> imageFactory) {

        Rect<C> rect = new Rect<>(
            colorFactory.getWhite(),
            new LineProperties<>(colorFactory.getLightGray(), 0.1f, true)
        );
        rect.setXPercentage(0.1f);
        rect.setYPercentage(0.1f);
        rect.setWPercentage(0.25f);
        rect.setHPercentage(0.25f);
        addView(rect);

        rect = new Rect<>(colorFactory.getColor(100, 10, 200));
        rect.setXPercentage(0.2f);
        rect.setYPercentage(0.2f);
        rect.setWPercentage(0.25f);
        rect.setHPercentage(0.25f);
        rect.setAlpha(0.75f);
        addView(rect);

        SimpleText<C> simpleText = new SimpleText<>("simple text H left", colorFactory.getWhite());
        simpleText.setXPercentage(0.2f);
        simpleText.setYPercentage(0.5f);
        simpleText.setWPercentage(0.25f);
        simpleText.setHPercentage(0.1f);
        simpleText.setTextSize(12);
        simpleText.setHAlign(SimpleText.LEFT);
        simpleText.setOrientation(SimpleText.HORIZONTAL);
        addView(simpleText);

        simpleText = new SimpleText<>("simple text H center", colorFactory.getWhite());
        simpleText.setXPercentage(0.2f);
        simpleText.setYPercentage(0.6f);
        simpleText.setWPercentage(0.25f);
        simpleText.setHPercentage(0.1f);
        simpleText.setTextSize(14);
        simpleText.setHAlign(SimpleText.CENTER);
        simpleText.setOrientation(SimpleText.HORIZONTAL);
        addView(simpleText);

        simpleText = new SimpleText<>("simple text V left", colorFactory.getWhite());
        simpleText.setXPercentage(0.05f);
        simpleText.setYPercentage(0.5f);
        simpleText.setWPercentage(0.05f);
        simpleText.setHPercentage(0.3f);
        simpleText.setTextSize(12);
        simpleText.setHAlign(SimpleText.LEFT);
        simpleText.setOrientation(SimpleText.VERTICAL);
        addView(simpleText);

        simpleText = new SimpleText<>("simple text V center", colorFactory.getWhite());
        simpleText.setXPercentage(0.1f);
        simpleText.setYPercentage(0.5f);
        simpleText.setWPercentage(0.05f);
        simpleText.setHPercentage(0.3f);
        simpleText.setTextSize(12);
        simpleText.setHAlign(SimpleText.CENTER);
        simpleText.setOrientation(SimpleText.VERTICAL);
        addView(simpleText);

        TextButton<C> textButton = new TextButton<>(
            "button",
            colorFactory.getBlue(),
            colorFactory.getLightGray(),
            new LineProperties<>(colorFactory.getBlue(), 2.0f, false)
        );
        textButton.setXPercentage(0.4f);
        textButton.setYPercentage(0.05f);
        textButton.setWPercentage(0.1f);
        textButton.setHPercentage(0.05f);
        textButton.setTextSize(16);
        addView(textButton);

        de.sl.view.Image<C, I> image = new de.sl.view.Image<>(imageFactory.getImage("images/elfe.png"));
        image.setXPercentage(0.6f);
        image.setYPercentage(0.05f);
        image.setWPercentage(0.2f);
        image.setHPercentage(0.2f);
        addView(image);

        image = new de.sl.view.Image<>(imageFactory.getImage("images/elfe.png"));
        image.setXPercentage(0.6f);
        image.setYPercentage(0.3f);
        image.setWPercentage(0.2f);
        image.setHPercentage(0.2f);
        image.setMode(Image.FIT_TO_BOX);
        addView(image);

        de.sl.view.Line<C> line = new de.sl.view.Line<>(colorFactory.getWhite(), 2.0f);
        line.setXPercentage(0.1f);
        line.setYPercentage(0.1f);
        line.setWPercentage(0.8f);
        line.setHPercentage(0.05f);
        line.setMode(Line.HORIZONTAL);
        addView(line);

        line = new de.sl.view.Line<>(colorFactory.getWhite(), 2.0f);
        line.setXPercentage(0.1f);
        line.setYPercentage(0.1f);
        line.setWPercentage(0.05f);
        line.setHPercentage(0.8f);
        line.setMode(Line.VERTICAL);
        addView(line);
    }

    private void setBorderToView() {
        activeViewBorder.setXPercentage(activeView.getXPercentage());
        activeViewBorder.setYPercentage(activeView.getYPercentage());
        activeViewBorder.setWPercentage(activeView.getWPercentage());
        activeViewBorder.setHPercentage(activeView.getHPercentage());
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
    protected boolean handleClick(List<IView<C>> affectedViews) {
        boolean modified = false;

        if(affectedViews.isEmpty()) {
            if(activeView!=null) {
                activeViewBorder.setVisible(false);
                activeView = null;
                modified = true;
            }
        } else {
            final IView<C> newActiveView = affectedViews.get(0);
            if(newActiveView!=activeView) {
                activeView = newActiveView;
                setBorderToView();
                activeViewBorder.setVisible(true);
                modified = true;
            }
        }

        return modified;
    }

    @Override
    protected boolean handleTouchDown(List<IView<C>> affectedViews) {
        if(affectedViews.size()>1) {
            return false;
        }
        return handleClick(affectedViews);
    }
}
