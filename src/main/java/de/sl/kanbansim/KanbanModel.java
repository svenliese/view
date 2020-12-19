package de.sl.kanbansim;

import de.sl.view.*;

import java.util.List;

/**
 * @author SL
 */
public class KanbanModel<C, I> extends Model<C, I> {

    private final List<Column> columns;

    public KanbanModel(IColorFactory<C> colorFactory, List<Column> columns) {
        this.columns = columns;

        float x = 0.1f;
        float columnWidth = 0.8f / columns.size();
        float xSpace = 0.02f;
        float y = 0.1f;
        float columnHeight = 0.8f;

        for(Column column : columns) {
            final Rect<C> rect = new Rect<>(
                colorFactory.getBlack(),
                new LineProperties<>(
                    colorFactory.getWhite(),
                    1.0f,
                    false
                )
            );
            rect.setXPercentage(x+xSpace);
            rect.setYPercentage(y);
            rect.setWPercentage(columnWidth-2*xSpace);
            rect.setHPercentage(columnHeight);
            addView(rect);

            final SimpleText<C> wipView = new SimpleText<>("wip "+column.getWip(), colorFactory.getWhite());
            wipView.setXPercentage(x+xSpace+0.01f);
            wipView.setYPercentage(y+0.05f);
            wipView.setWPercentage(columnWidth - 2*xSpace - 0.02f);
            wipView.setHPercentage(0.1f);
            wipView.setTextSize(30);
            addView(wipView);

            final SimpleText<C> nameView = new SimpleText<>(column.getName(), colorFactory.getWhite());
            nameView.setXPercentage(x+xSpace+0.01f);
            nameView.setYPercentage(y+0.1f);
            nameView.setWPercentage(columnWidth - 2*xSpace - 0.02f);
            nameView.setHPercentage(0.1f);
            nameView.setTextSize(20);
            addView(nameView);

            x += columnWidth;
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
