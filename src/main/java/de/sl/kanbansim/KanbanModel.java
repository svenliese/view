package de.sl.kanbansim;

import de.sl.view.*;

import java.util.List;

/**
 * @author SL
 */
public class KanbanModel<C, I> extends Model<C, I> {

    private final List<Column> columns;

    public KanbanModel(IColorFactory<C> colorFactory, List<Column> columns, ViewBounds viewBounds) {
        this.columns = columns;

        final int columnSum = getColumnSum();
        final float xSpace = 0.005f;
        final float smallestWidth = (viewBounds.getW() - (columns.size()-1)*xSpace) / columnSum;
        initColumns(colorFactory, columns, viewBounds, xSpace, smallestWidth);
    }

    private void initColumns(IColorFactory<C> colorFactory, List<Column> myColumns, ViewBounds viewBounds, float xSpace, float smallestWidth) {
        float x = viewBounds.getX();

        for(Column column : myColumns) {

            final ViewBounds columnViewBounds = new ViewBounds(
                x,
                viewBounds.getY(),
                smallestWidth * column.getColumnSum(),
                viewBounds.getH()
            );

            initColumn(colorFactory, column, columnViewBounds, xSpace, smallestWidth);

            x += columnViewBounds.getW();
            x += xSpace;
        }
    }

    private void initColumn(IColorFactory<C> colorFactory, Column column, ViewBounds viewBounds, float xSpace, float smallestWidth) {
        float x = viewBounds.getX();

        final Rect<C> rect = new Rect<>(
            colorFactory.getBlack(),
            new LineProperties<>(
                colorFactory.getWhite(),
                1.0f,
                false
            )
        );
        rect.setXPercentage(x+xSpace);
        rect.setYPercentage(viewBounds.getY());
        rect.setWPercentage(viewBounds.getW());
        rect.setHPercentage(viewBounds.getH());
        addView(rect);

        final SimpleText<C> wipView = new SimpleText<>("wip "+column.getWip(), colorFactory.getWhite());
        wipView.setXPercentage(x+0.01f);
        wipView.setYPercentage(viewBounds.getY()+0.05f);
        wipView.setWPercentage(viewBounds.getW() - 0.02f);
        wipView.setHPercentage(0.1f);
        wipView.setTextSize(20);
        addView(wipView);

        final SimpleText<C> nameView = new SimpleText<>(column.getName(), colorFactory.getWhite());
        nameView.setXPercentage(x+0.01f);
        nameView.setYPercentage(viewBounds.getY()+0.1f);
        nameView.setWPercentage(viewBounds.getW() - 0.02f);
        nameView.setHPercentage(0.1f);
        nameView.setTextSize(20);
        addView(nameView);
    }

    private int getColumnSum() {
        int sum = 0;
        for(Column column : columns) {
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
