package de.sl.kanbansim;

import de.sl.view.*;

import java.util.List;

/**
 * @author SL
 */
public class KanbanCompareModelView<C, I> extends ViewModel<C, I> {

    private final KanbanCompareModel model;

    private final KanbanModelView<C, I> modelView1;
    private final KanbanModelView<C, I> modelView2;

    private final SimpleText<C> timeInfo;

    private final float textHeight = 0.025f;
    private final float ySpace = 0.015f;

    public KanbanCompareModelView(IColorFactory<C> colorFactory, KanbanCompareModel model, ViewBounds viewBounds) {
        super(model);
        this.model = model;

        final float height = viewBounds.getH() / 2 - 2*ySpace - textHeight;

        float y = viewBounds.getY();

        modelView1 = new KanbanModelView<>(
            colorFactory,
            model.getModel1(),
                new ViewBounds(
                    viewBounds.getX(),
                    y,
                    viewBounds.getW(),
                    height
                )
        );
        addViews(modelView1.getViews());
        y += height;
        y += ySpace;

        timeInfo = new SimpleText<>(model.getTimeInfoText(), colorFactory.getWhite());
        timeInfo.setXPercentage(viewBounds.getX() + 0.01f);
        timeInfo.setYPercentage(y);
        timeInfo.setWPercentage(viewBounds.getW() - 0.02f);
        timeInfo.setHPercentage(textHeight);
        timeInfo.setTextSize(18);
        timeInfo.setHAlign(SimpleText.LEFT);
        addView(timeInfo);
        y += ySpace;
        y += textHeight;

        modelView2 = new KanbanModelView<>(
            colorFactory,
            model.getModel2(),
            new ViewBounds(
                viewBounds.getX(),
                y,
                viewBounds.getW(),
                height
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
        } else if(modelObject == model) {
            timeInfo.setText(model.getTimeInfoText());
        } else {
            throw new IllegalStateException("unexpected model object "+modelObject.getClass());
        }
    }

    @Override
    protected boolean handleClick(List<IView<C>> affectedViews) {
        return false;
    }

    @Override
    protected boolean handleTouchDown(List<IView<C>> affectedViews) {
        return false;
    }
}
