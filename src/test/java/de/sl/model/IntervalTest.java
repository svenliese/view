package de.sl.model;

import de.sl.stat.Distribution;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * @author SL
 */
class IntervalTest {

    @Test
    void testRandomMillis() {
        final Interval interval = Interval.getMillisInterval(10, 11);
        final Distribution distribution = new Distribution();
        distribution.addElement("10");
        distribution.addElement("11");
        final Random rnd = new Random(11);
        for(int i=0; i<10; i++) {
            distribution.incCounter(Long.toString(interval.getRandomMillis(rnd)));
        }
        Assertions.assertTrue(distribution.checkMinValue(4));
    }
}
