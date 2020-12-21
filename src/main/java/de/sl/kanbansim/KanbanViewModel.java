package de.sl.kanbansim;

import de.sl.view.*;

import java.util.List;

/**
 * @author SL
 */
public class KanbanViewModel<C, I> extends ViewModel<C, I> {

    private final float xSpace = 0.005f;

    private final KanbanModel model;

    public KanbanViewModel(IColorFactory<C> colorFactory, KanbanModel model, ViewBounds viewBounds) {
        this.model = model;

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

        final float textHeight = 0.025f;
        final float ySpace = 0.025f;

        float y = viewBounds.getY()+ySpace;

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
        rect.setXPercentage(viewBounds.getX() + xSpace);
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

        //
        // child columns
        //

        if(column.hasChildren()) {
            final ViewBounds childBounds = new ViewBounds(
                viewBounds.getX() + xSpace,
                y,
                viewBounds.getW() - 2*xSpace,
                viewBounds.getH() - y
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
