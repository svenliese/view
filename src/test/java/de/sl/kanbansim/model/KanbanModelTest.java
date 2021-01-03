package de.sl.kanbansim.model;

import de.sl.model.Interval;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author SL
 */
class KanbanModelTest {

    @Test
    void testModel() {
        final Integer columnType1 = Integer.valueOf(1);
        final Integer columnType2 = Integer.valueOf(2);

        final KanbanConfig config = new KanbanConfig(1, 1, 8, 0, 1000);
        config.addInterval(columnType1, Interval.getHourInterval(1, 1));
        config.addInterval(columnType2, Interval.getHourInterval(1, 1));

        final Column column1 = new Column("column1", columnType1, 0);
        final Column column2 = new Column("column2", columnType2, 0);
        final Column column3 = new Column("column3", KanbanModel.TYPE_DONE, 0);

        final KanbanModel model = new KanbanModel(config);
        model.addColumn(column1);
        model.addColumn(column2);
        model.addColumn(column3);

        final Card card = new Card(1, config);
        model.addCard(card);

        // no time elapsed, the card should remain in the first column
        model.simulate(0);
        Assertions.assertEquals(1, column1.getTicketCount());
        Assertions.assertEquals(0, column2.getTicketCount());
        Assertions.assertEquals(0, column3.getTicketCount());

        // one hour elapsed, the card should remain in the first column
        model.simulate(Interval.MILLIS_PER_HOUR);
        Assertions.assertEquals(0, column1.getTicketCount());
        Assertions.assertEquals(Interval.MILLIS_PER_HOUR, column2.getArrivalTime(card));
        Assertions.assertEquals(1, column2.getTicketCount());
        Assertions.assertEquals(0, column3.getTicketCount());

        // two hours elapsed, the card should remain in the second column
        model.simulate(2*Interval.MILLIS_PER_HOUR);
        Assertions.assertEquals(0, column1.getTicketCount());
        Assertions.assertEquals(0, column2.getTicketCount());
        Assertions.assertEquals(2*Interval.MILLIS_PER_HOUR, column3.getArrivalTime(card));
        Assertions.assertEquals(1, column3.getTicketCount());
    }

    @Test
    void testBlockedTimeStepByStep() {

        final KanbanConfig config = new KanbanConfig(2, 2, 8, 0, Interval.MILLIS_PER_HOUR);
        config.addInterval(KanbanModel.TYPE_PREPARE, Interval.getHourInterval(1, 1));
        config.addInterval(KanbanModel.TYPE_WORK, Interval.getHourInterval(1, 1));

        final Column column1 = new Column("column1", KanbanModel.TYPE_IDEAS, 0);
        final Column column2 = new Column("column2", KanbanModel.TYPE_PREPARE, 1);
        final Column column3 = new Column("column3", KanbanModel.TYPE_WORK, 1);
        final Column column4 = new Column("column4", KanbanModel.TYPE_DONE, 0);

        final KanbanModel model = new KanbanModel(config);
        model.addColumn(column1);
        model.addColumn(column2);
        model.addColumn(column3);
        model.addColumn(column4);

        final Card card1 = new Card(1, config);
        model.addCard(card1);

        final Card card2 = new Card(2, config);
        model.addCard(card2);

        //
        // no time elapsed
        //
        model.simulate(0);
        Assertions.assertEquals(0, model.getBlockedTimeSum());
        // column1
        Assertions.assertEquals(1, column1.getTicketCount());
        Assertions.assertEquals(0, column1.getArrivalTime(card2));
        // column2
        Assertions.assertEquals(1, column2.getTicketCount());
        Assertions.assertEquals(0, column2.getArrivalTime(card1));

        // 1 hour elapsed
        model.simulate(config.getMillisPerStep());
        Assertions.assertEquals(Interval.MILLIS_PER_HOUR, model.getBlockedTimeSum());
        // column1
        Assertions.assertEquals(0, column1.getTicketCount());
        // column2
        Assertions.assertEquals(1, column2.getTicketCount());
        Assertions.assertEquals(Interval.MILLIS_PER_HOUR, column2.getArrivalTime(card2));
        // column3
        Assertions.assertEquals(1, column3.getTicketCount());
        Assertions.assertEquals(Interval.MILLIS_PER_HOUR, column3.getArrivalTime(card1));

        // 2 hour elapsed
        model.simulate(2*config.getMillisPerStep());
        Assertions.assertEquals(Interval.MILLIS_PER_HOUR, model.getBlockedTimeSum());
        // column1
        Assertions.assertEquals(0, column1.getTicketCount());
        // column2
        Assertions.assertEquals(0, column2.getTicketCount());
        // column3
        Assertions.assertEquals(1, column3.getTicketCount());
        Assertions.assertEquals(2*Interval.MILLIS_PER_HOUR, column3.getArrivalTime(card2));
        // column4
        Assertions.assertEquals(1, column4.getTicketCount());
        Assertions.assertEquals(2*Interval.MILLIS_PER_HOUR, column4.getArrivalTime(card1));

        Assertions.assertFalse(model.simulate(3*config.getMillisPerStep()));
    }

    @Test
    void testBlockedTime() {

        for(int speed = 1; speed <= 3; speed++) {
            final KanbanConfig config = new KanbanConfig(2, 2, 8, Interval.MILLIS_PER_HOUR, Interval.MILLIS_PER_HOUR / speed);
            config.addInterval(KanbanModel.TYPE_IDEAS, Interval.getHourInterval(1, 1));
            config.addInterval(KanbanModel.TYPE_PREPARE, Interval.getHourInterval(1, 1));
            config.addInterval(KanbanModel.TYPE_WORK, Interval.getHourInterval(1, 1));

            final Column column1 = new Column("column1", KanbanModel.TYPE_IDEAS, 0);
            final Column column2 = new Column("column2", KanbanModel.TYPE_PREPARE, 1);
            final Column column3 = new Column("column3", KanbanModel.TYPE_WORK, 1);
            final Column column4 = new Column("column3", KanbanModel.TYPE_DONE, 0);

            final KanbanModel model = new KanbanModel(config);
            model.addColumn(column1);
            model.addColumn(column2);
            model.addColumn(column3);
            model.addColumn(column4);
            model.addCard(new Card(1, config));
            model.addCard(new Card(2, config));

            model.start();
            model.join();

            Assertions.assertEquals(1, column4.getTicketCount());
            Assertions.assertEquals(1, model.getBlockedTimeSum() / Interval.MILLIS_PER_HOUR, "error by speed "+speed);
        }
    }

    @Test
    void testNoBlockedTime() {

        for(long speed = 2; speed <= 5; speed++) {
            final KanbanConfig config = new KanbanConfig(2, 2, 8, Interval.MILLIS_PER_HOUR, Interval.MILLIS_PER_HOUR / speed);

            final Column column1 = new Column("column1", KanbanModel.TYPE_IDEAS, 0);
            final Column column2 = new Column("column2", KanbanModel.TYPE_PREPARE, 1);
            final Column column3 = new Column("column3", KanbanModel.TYPE_WORK, 1);
            final Column column4 = new Column("column3", KanbanModel.TYPE_DONE, 0);

            final KanbanModel model = new KanbanModel(config);
            model.addColumn(column1);
            model.addColumn(column2);
            model.addColumn(column3);
            model.addColumn(column4);

            final Card card1 = new Card(1, config);
            card1.setMillis(KanbanModel.TYPE_IDEAS, Interval.MILLIS_PER_HOUR / 2);
            card1.setMillis(KanbanModel.TYPE_PREPARE, Interval.MILLIS_PER_HOUR);
            card1.setMillis(KanbanModel.TYPE_WORK, Interval.MILLIS_PER_HOUR / 2);
            model.addCard(card1);

            final Card card2 = new Card(2, config);
            card2.setMillis(KanbanModel.TYPE_IDEAS, Interval.MILLIS_PER_HOUR);
            card2.setMillis(KanbanModel.TYPE_PREPARE, Interval.MILLIS_PER_HOUR / 2);
            card2.setMillis(KanbanModel.TYPE_WORK, Interval.MILLIS_PER_HOUR);
            model.addCard(card2);

            model.start();
            model.join();

            Assertions.assertEquals(1, column4.getTicketCount());
            Assertions.assertEquals(Interval.MILLIS_PER_HOUR * 2, model.getElapsedTime());
        }
    }
}
