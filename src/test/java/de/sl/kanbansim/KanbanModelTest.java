package de.sl.kanbansim;

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
        final Integer columnType3 = Integer.valueOf(3);

        final KanbanConfig config = new KanbanConfig(1, 1, 0, Interval.MILLIS_PER_HOUR/2.0d);
        config.addInterval(columnType1, Interval.getHourInterval(1, 1));
        config.addInterval(columnType2, Interval.getHourInterval(1, 1));

        final Column column1 = new Column("column1", columnType1, 0);
        final Column column2 = new Column("column2", columnType2, 0);
        final Column column3 = new Column("column3", columnType3, 0);


        final KanbanModel model = new KanbanModel(config);
        model.addColumn(column1);
        model.addColumn(column2);
        model.addColumn(column3);
        model.addCard(new Card(1, config));

        model.simulate(0);
        Assertions.assertEquals(1, column1.getTicketCount());
        Assertions.assertEquals(0, column2.getTicketCount());
        Assertions.assertEquals(0, column3.getTicketCount());

        model.simulate(Interval.MILLIS_PER_HOUR);
        Assertions.assertEquals(1, column1.getTicketCount());
        Assertions.assertEquals(0, column2.getTicketCount());
        Assertions.assertEquals(0, column3.getTicketCount());

        model.simulate(Interval.MILLIS_PER_HOUR+1);
        Assertions.assertEquals(0, column1.getTicketCount());
        Assertions.assertEquals(1, column2.getTicketCount());
        Assertions.assertEquals(0, column3.getTicketCount());

        model.simulate(2*Interval.MILLIS_PER_HOUR+1);
        Assertions.assertEquals(0, column1.getTicketCount());
        Assertions.assertEquals(1, column2.getTicketCount());
        Assertions.assertEquals(0, column3.getTicketCount());

        model.simulate(2*Interval.MILLIS_PER_HOUR+2);
        Assertions.assertEquals(0, column1.getTicketCount());
        Assertions.assertEquals(0, column2.getTicketCount());
        Assertions.assertEquals(1, column3.getTicketCount());
    }
}
