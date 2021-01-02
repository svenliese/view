package de.sl.kanbansim;

import de.sl.view.*;

import java.util.List;

/**
 * @author SL
 */
public class KanbanCompareModelView<C, I> extends ViewModel<C, I> {

    static final float textHeight = 0.01f;
    static final float ySpace = 0.015f;
    static final int textSize = 16;

    private final KanbanCompareModel model;

    private final KanbanModelView<C, I> modelView1;
    private final KanbanModelView<C, I> modelView2;

    private final TextButton<C> button;

    public KanbanCompareModelView(IColorFactory<C> colorFactory, KanbanCompareModel model, ViewBounds viewBounds) {
        super(model);
        this.model = model;

        final float buttonHeight = textHeight + 2*ySpace;
        final float boardHeight = (viewBounds.getH() - 3*ySpace - buttonHeight) / 2;

        float y = viewBounds.getY();

        button = new TextButton<>("pause", colorFactory.getWhite(), colorFactory.getBlack(), new LineProperties<>(colorFactory.getWhite(), 1.0f, false));
        button.setXPercentage(viewBounds.getX());
        button.setYPercentage(y);
        button.setWPercentage(0.05f);
        button.setHPercentage(buttonHeight);
        button.setTextSize(textSize);
        button.setHAlign(SimpleText.CENTER);
        addView(button);

        y += buttonHeight;
        y += ySpace;

        modelView1 = new KanbanModelView<>(
            colorFactory,
            model.getModel1(),
                new ViewBounds(
                    viewBounds.getX(),
                    y,
                    viewBounds.getW(),
                    boardHeight
                )
        );
        addViews(modelView1.getViews());

        y += boardHeight;
        y += ySpace;

        modelView2 = new KanbanModelView<>(
            colorFactory,
            model.getModel2(),
            new ViewBounds(
                viewBounds.getX(),
                y,
                viewBounds.getW(),
                boardHeight
            )
        );
        addViews(modelView2.getViews());
    }

    @Override
    public void handleModelUpdate(Object modelObject) {
        super.handleModelUpdate(modelObject);
        if(modelObject instanceof Column) {
            modelView1.updateViews(modelObject);
            modelView2.updateViews(modelObject);
        } else {
            throw new IllegalStateException("unexpected model object "+modelObject.getClass());
        }
    }

    @Override
    protected boolean handleClick(List<IView<C>> affectedViews) {
        for(IView<C> view : affectedViews) {
            if(view==button) {
                if(model.isPause()) {
                    model.resume();
                    button.setText("pause");
                } else {
                    model.pause();
                    button.setText("go");
                }
                super.handleModelUpdate(view);
            }
        }
        return false;
    }

    @Override
    protected boolean handleTouchDown(List<IView<C>> affectedViews) {
        return false;
    }
}
