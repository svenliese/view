package de.sl.kanbansim.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SL
 */
class CardLeadTimeComparatorTest {

    @Test
    void testCompare() {
        final KanbanConfig config = new KanbanConfig(
            0,
            0,
            0,
            0,
            0,
            KanbanConfig.STOP_WHEN_NO_CARD,
            KanbanConfig.COUNT_BLOCKING_FROM_BEGINNING
        );
        final List<Card> cards = new ArrayList<>();

        final Card card1 = new Card(1, config);
        cards.add(card1);

        final Card card2 = new Card(2, config);
        cards.add(card2);

        card1.setStartMillis(10);
        card1.setDoneMillis(20);
        card2.setStartMillis(10);
        card2.setDoneMillis(30);
        cards.sort(new CardLeadTimeComparator());
        Assertions.assertEquals(1, cards.get(0).getId());
        Assertions.assertEquals(2, cards.get(1).getId());

        card1.setDoneMillis(300);
        cards.sort(new CardLeadTimeComparator());
        Assertions.assertEquals(2, cards.get(0).getId());
        Assertions.assertEquals(1, cards.get(1).getId());
    }
}
