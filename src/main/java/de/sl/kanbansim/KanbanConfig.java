package de.sl.kanbansim;

import java.util.Random;

/**
 * @author SL
 */
public class KanbanConfig {

    private final Random random = new Random(7);

    private final int cardCount = 100;

    private final int activeCardCount = 6;

    public int getCardCount() {
        return cardCount;
    }

    public int getActiveCardCount() {
        return activeCardCount;
    }
}
