package de.sl.model;

import java.util.Random;

/**
 * @author SL
 */
public class Interval {

    private static final long MILLIS_PER_HOUR = 1000*60*60;
    private static final long MILLIS_PER_DAY = 1000*60*60*24;

    public static Interval getHourInterval(int minHours, int maxHours) {
        return new Interval(minHours*MILLIS_PER_HOUR, maxHours*MILLIS_PER_HOUR);
    }

    public static Interval getDayInterval(int minDays, int maxDays) {
        return new Interval(minDays*MILLIS_PER_DAY, maxDays*MILLIS_PER_DAY);
    }

    private final long minMillis;
    private final long maxMillis;

    private Interval(long minMillis, long maxMillis) {
        this.minMillis = minMillis;
        this.maxMillis = maxMillis;
    }

    public long getRandom(Random random) {
        return (long)(random.nextDouble() * (maxMillis - minMillis) + minMillis + 1);
    }
}
