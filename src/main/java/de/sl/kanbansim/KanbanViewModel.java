package de.sl.kanbansim;

import de.sl.view.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SL
 */
public class KanbanViewModel<C, I> extends ViewModel<C, I> {

    private final KanbanCompareModel model;

    private final Map<Integer, SimpleText<C>> cardInfoMap = new HashMap<>();

    public KanbanViewModel(IColorFactory<C> colorFactory, KanbanCompareModel model, ViewBounds viewBounds) {
        super(model);
        this.model = model;

        final float ySpace = 0.01f;
        final float height = viewBounds.getH() / 2 - ySpace;

        initColumns(
            colorFactory,
            model.getModel1().getColumns(),
            new ViewBounds(
                viewBounds.getX(),
                viewBounds.getY(),
                viewBounds.getW(),
                height
            )
        );

        initColumns(
            colorFactory,
            model.getModel2().getColumns(),
            new ViewBounds(
                viewBounds.getX(),
                viewBounds.getY() + height + ySpace,
                viewBounds.getW(),
                height
            )
        );
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

        final float textHeight = 0.025f;
        final float ySpace = 0.01f;

        float y = viewBounds.getY()+ySpace;
        float childHeight = viewBounds.getH() - ySpace;

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
        // WIP text
        //

        final SimpleText<C> wipView = new SimpleText<>("wip "+column.getWip(), colorFactory.getWhite());
        wipView.setXPercentage(viewBounds.getX() + 0.01f);
        wipView.setYPercentage(y);
        wipView.setWPercentage(viewBounds.getW() - 0.02f);
        wipView.setHPercentage(textHeight);
        wipView.setTextSize(18);
        addView(wipView);
        y += textHeight + ySpace;
        childHeight -= textHeight + ySpace;

        //
        // column name
        //

        final SimpleText<C> nameView = new SimpleText<>(column.getName(), colorFactory.getWhite());
        nameView.setXPercentage(viewBounds.getX() + 0.01f);
        nameView.setYPercentage(y);
        nameView.setWPercentage(viewBounds.getW() - 0.02f);
        nameView.setHPercentage(textHeight);
        nameView.setTextSize(18);
        addView(nameView);
        y += textHeight + ySpace;
        childHeight -= textHeight + ySpace;

        //
        // card info
        //

        final SimpleText<C> cardInfoView = new SimpleText<>("cards "+column.getTicketCount(), colorFactory.getWhite());
        cardInfoView.setXPercentage(viewBounds.getX() + 0.01f);
        cardInfoView.setYPercentage(y);
        cardInfoView.setWPercentage(viewBounds.getW() - 0.02f);
        cardInfoView.setHPercentage(textHeight);
        cardInfoView.setTextSize(18);
        addView(cardInfoView);
        cardInfoMap.put(column.getId(), cardInfoView);
        y += textHeight + ySpace;
        childHeight -= textHeight + ySpace;

        //
        // child columns
        //

        if(column.hasChildren()) {
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

    @Override
    protected boolean handleClick(List<IView<C>> affectedViews) {
        return false;
    }

    @Override
    protected boolean handleTouchDown(List<IView<C>> affectedViews) {
        return false;
    }
}
