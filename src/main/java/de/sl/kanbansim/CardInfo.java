package de.sl.kanbansim;

import de.sl.view.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SL
 */
public class CardInfo<C> {

    private final float textHeight = 0.025f;
    private final float ySpace = 0.015f;

    private final Column column;

    private final SimpleText<C> cardCountText;

    private final List<IView<C>> allViews = new ArrayList<>();

    private final List<Rect<C>> cardRectList = new ArrayList<>();

    public CardInfo(Column column, ViewBounds viewBounds, IColorFactory<C> colorFactory, float cardSize, int cardCount) {
        this.column = column;

        float y = viewBounds.getY();

        cardCountText = new SimpleText<>("", colorFactory.getWhite());
        if(column.getTypeId().equals(KanbanModel.TYPE_DONE)) {
            cardCountText.setTextColor(colorFactory.getYellow());
        }
        cardCountText.setXPercentage(viewBounds.getX() + 0.01f);
        cardCountText.setYPercentage(y);
        cardCountText.setWPercentage(viewBounds.getW() - 0.02f);
        cardCountText.setHPercentage(textHeight);
        cardCountText.setTextSize(18);
        allViews.add(cardCountText);

        y += textHeight + ySpace;

        if(!column.getTypeId().equals(KanbanModel.TYPE_DONE)) {
            for (int i = 0; i < cardCount; i++) {
                final Rect<C> cardRect = new Rect<>(colorFactory.getYellow());
                cardRect.setXPercentage(viewBounds.getX() + (viewBounds.getW() - cardSize) / 2);
                cardRect.setYPercentage(y);
                cardRect.setWPercentage(cardSize);
                cardRect.setHPercentage(cardSize);
                cardRect.setVisible(false);
                allViews.add(cardRect);
                cardRectList.add(cardRect);

                y += cardSize + ySpace;
            }
        }

        update();
    }

    public List<IView<C>> getAllViews() {
        return allViews;
    }

    public void update() {
        cardCountText.setText("cards "+column.getTicketCount());

        if(!column.getTypeId().equals(KanbanModel.TYPE_DONE)) {
            for (Rect<C> rect : cardRectList) {
                rect.setVisible(false);
            }
            for (int i = 0; i < column.getTicketCount(); i++) {
                cardRectList.get(i).setVisible(true);
            }
        }
    }
}
