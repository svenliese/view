package de.sl.kanbansim;

import de.sl.model.Interval;
import de.sl.view.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SL
 */
public class KanbanModelView<C, I> extends ViewModel<C, I> {

    private final KanbanModel model;

    private final Map<Integer, CardInfo<C>> cardInfoMap = new HashMap<>();

    private final SimpleText<C> waitingInfo;

    final float cardSize;

    public KanbanModelView(IColorFactory<C> colorFactory, KanbanModel model, ViewBounds viewBounds) {
        super(model);
        this.model = model;

        cardSize = viewBounds.getH()/3/model.getMaxWorkers();

        waitingInfo = new SimpleText<>("wait 0", colorFactory.getRed());

        initColumns(colorFactory, model.getColumns(), viewBounds);
    }

    private void initColumns(IColorFactory<C> colorFactory, List<Column> myColumns, ViewBounds viewBounds) {
        final int allColumnCount = getColumnSum(myColumns);

        float x = viewBounds.getX();
        for(Column column : myColumns) {
            final int columnCount = column.getColumnSum();
            final float width = viewBounds.getW() / allColumnCount * columnCount;

            final ViewBounds columnViewBounds = new ViewBounds(
                x,
                viewBounds.getY(),
                width,
                viewBounds.getH()
            );

            initColumn(colorFactory, column, columnViewBounds);

            x += width;
        }
    }

    private void initColumn(IColorFactory<C> colorFactory, Column column, ViewBounds viewBounds) {

        float y = viewBounds.getY() + KanbanCompareModelView.ySpace;
        float childHeight = viewBounds.getH() - KanbanCompareModelView.ySpace;

        //
        // column border
        //

        final Rect<C> rect = new Rect<>(
            colorFactory.getBlack(),
            new LineProperties<>(
                colorFactory.getWhite(),
                1.0f,
                false
            )
        );
        rect.setXPercentage(viewBounds.getX());
        rect.setYPercentage(viewBounds.getY());
        rect.setWPercentage(viewBounds.getW());
        rect.setHPercentage(viewBounds.getH());
        addView(rect);

        //
        // column name
        //

        final SimpleText<C> nameView = new SimpleText<>(column.getName(), colorFactory.getWhite());
        nameView.setXPercentage(viewBounds.getX() + 0.01f);
        nameView.setYPercentage(y);
        nameView.setWPercentage(viewBounds.getW() - 0.02f);
        nameView.setHPercentage(KanbanCompareModelView.textHeight);
        nameView.setTextSize(KanbanCompareModelView.textSize+2);
        addView(nameView);
        y += KanbanCompareModelView.textHeight + KanbanCompareModelView.ySpace;
        childHeight -= KanbanCompareModelView.textHeight + KanbanCompareModelView.ySpace;

        //
        // WIP text / waiting time
        //

        if(column.getTypeId().equals(KanbanModel.TYPE_DONE)) {
            waitingInfo.setXPercentage(viewBounds.getX() + 0.01f);
            waitingInfo.setYPercentage(y);
            waitingInfo.setWPercentage(viewBounds.getW() - 0.02f);
            waitingInfo.setHPercentage(KanbanCompareModelView.textHeight);
            waitingInfo.setTextSize(KanbanCompareModelView.textSize);
            addView(waitingInfo);
        } else {
            final SimpleText<C> wipView = new SimpleText<>("wip " + column.getWip(), colorFactory.getWhite());
            wipView.setXPercentage(viewBounds.getX() + 0.01f);
            wipView.setYPercentage(y);
            wipView.setWPercentage(viewBounds.getW() - 0.02f);
            wipView.setHPercentage(KanbanCompareModelView.textHeight);
            wipView.setTextSize(KanbanCompareModelView.textSize);
            addView(wipView);
        }
        y += KanbanCompareModelView.textHeight + KanbanCompareModelView.ySpace;
        childHeight -= KanbanCompareModelView.textHeight + KanbanCompareModelView.ySpace;

        if(column.getColumnSum()==1) {

            //
            // card info
            //

            final CardInfo<C> cardInfo = new CardInfo<>(
                model,
                column,
                new ViewBounds(viewBounds.getX(), y, viewBounds.getW(), childHeight),
                colorFactory,
                cardSize
            );
            addViews(cardInfo.getAllViews());
            cardInfoMap.put(column.getId(), cardInfo);

        } else {

            //
            // child columns
            //

            final ViewBounds childBounds = new ViewBounds(
                viewBounds.getX(),
                y,
                viewBounds.getW(),
                childHeight
            );
            initColumns(colorFactory, column.getChildren(), childBounds);
        }
    }

    private int getColumnSum(List<Column> myColumns) {
        int sum = 0;
        for(Column column : myColumns) {
            sum += column.getColumnSum();
        }
        return sum;
    }

    public void updateViews(Object modelObject) {
        if(modelObject instanceof Column) {
            final Column column = (Column) modelObject;
            final CardInfo<C> cardInfoView = cardInfoMap.get(column.getId());
            if(cardInfoView!=null) {
                cardInfoView.update();
            }
            if(column.getTypeId().equals(KanbanModel.TYPE_DONE)) {
                waitingInfo.setText("wait "+(model.getWaitingTime() / Interval.MILLIS_PER_HOUR / model.getMaxWorkers()));
            }
        } else {
            throw new IllegalStateException("unexpected model object "+modelObject.getClass());
        }
    }

    @Override
    public void handleModelUpdate(Object modelObject) {
        super.handleModelUpdate(modelObject);
        updateViews(modelObject);
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
