package de.sl.kanbansim.model;

import java.util.Comparator;

/**
 * @author SL
 */
public class CardLeadTimeComparator implements Comparator<Card> {

    @Override
    public int compare(Card card1, Card card2) {

        return Long.compare(card1.getLeadTimeMillis(), card2.getLeadTimeMillis());
    }
}
