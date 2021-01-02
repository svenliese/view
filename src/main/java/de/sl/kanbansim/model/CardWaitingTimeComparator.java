package de.sl.kanbansim.model;

import java.util.Comparator;

/**
 * @author SL
 */
public class CardWaitingTimeComparator implements Comparator<Card> {

    @Override
    public int compare(Card card1, Card card2) {

        return Long.compare(card2.getWaitingTime(), card1.getWaitingTime());
    }
}
