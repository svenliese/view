package de.sl.kanbansim.view;

import de.sl.kanbansim.model.Column;
import de.sl.kanbansim.model.KanbanModel;
import de.sl.kanbansim.model.Card;
import de.sl.model.Interval;
import de.sl.view.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SL
 */
public class CardInfo<C> {

    private final KanbanModel model;

    private final Column column;

    private final IColorFactory<C> colorFactory;

    private final SimpleText<C> cardCountText;

    private final SimpleText<C> timeInfo;

    private final List<IView<C>> allViews = new ArrayList<>();

    private final List<Rect<C>> cardRectList = new ArrayList<>();

    public CardInfo(KanbanModel model, Column column, ViewBounds viewBounds, IColorFactory<C> colorFactory, float cardSize) {
        this.model = model;
        this.column = column;
        this.colorFactory = colorFactory;

        float y = viewBounds.getY();

        cardCountText = new SimpleText<>("", colorFactory.getWhite());
        if(column.getTypeId().equals(KanbanModel.TYPE_DONE)) {
            cardCountText.setTextColor(colorFactory.getYellow());
        }
        cardCountText.setXPercentage(viewBounds.getX() + 0.01f);
        cardCountText.setYPercentage(y);
        cardCountText.setWPercentage(viewBounds.getW() - 0.02f);
        cardCountText.setHPercentage(KanbanCompareModelView.textHeight);
        cardCountText.setTextSize(KanbanCompareModelView.textSize);
        allViews.add(cardCountText);
        y += KanbanCompareModelView.textHeight + KanbanCompareModelView.ySpace;

        if(column.getTypeId().equals(KanbanModel.TYPE_DONE)) {
            timeInfo = new SimpleText<>(getTimeInfoText(), colorFactory.getWhite());
            timeInfo.setXPercentage(viewBounds.getX() + 0.01f);
            timeInfo.setYPercentage(y);
            timeInfo.setWPercentage(viewBounds.getW() - 0.02f);
            timeInfo.setHPercentage(KanbanCompareModelView.textHeight);
            timeInfo.setTextSize(KanbanCompareModelView.textSize);
            allViews.add(timeInfo);
        } else {
            timeInfo = null;

            y += KanbanCompareModelView.ySpace;

            for (int i = 0; i < model.getMaxWorkers(); i++) {
                final Rect<C> cardRect = new Rect<>(colorFactory.getYellow());
                cardRect.setXPercentage(viewBounds.getX() + (viewBounds.getW() - cardSize) / 2);
                cardRect.setYPercentage(y);
                cardRect.setWPercentage(cardSize);
                cardRect.setHPercentage(cardSize);
                cardRect.setVisible(false);
                allViews.add(cardRect);
                cardRectList.add(cardRect);

                y += cardSize + KanbanCompareModelView.ySpace;
            }
        }

        update();
    }

    public List<IView<C>> getAllViews() {
        return allViews;
    }

    private String getTimeInfoText() {
        return "days "+model.getElapsedTime() / Interval.MILLIS_PER_HOUR / model.getWorkingDayHours();
    }

    public void update() {
        cardCountText.setText("cards "+column.getTicketCount());

        if(column.getTypeId().equals(KanbanModel.TYPE_DONE)) {
            timeInfo.setText(getTimeInfoText());
        } else {
            for (Rect<C> rect : cardRectList) {
                rect.setVisible(false);
            }
            int i = 0;
            for (Card card : column.getCards()) {
                final Rect<C> cardRect = cardRectList.get(i++);
                cardRect.setVisible(true);
                if(card.isBlocked()) {
                    cardRect.setColor(colorFactory.getRed());
                } else {
                    cardRect.setColor(colorFactory.getYellow());
                }
            }
        }
    }
}
