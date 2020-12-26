package de.sl.kanbansim;

import de.sl.model.Interval;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * @author SL
 */
public class KanbanConfig {

    private final Random random = new Random(7);

    private final int cardCount;

    private final int maxWorkers;

    private final long defaultTime;

    private final double speed = 14400.0d;

    private final Map<Integer, Interval> timesByType = new TreeMap<>();

    public KanbanConfig(int cardCount, int maxWorkers, int defaultHours) {
        this.cardCount = cardCount;
        this.maxWorkers = maxWorkers;
        this.defaultTime = defaultHours;
    }

    public int getCardCount() {
        return cardCount;
    }

    public int getMaxWorkers() {
        return maxWorkers;
    }

    public long getDefaultHours() {
        return defaultTime;
    }

    public void addInterval(Integer typeId, Interval interval) {
        timesByType.put(typeId, interval);
    }

    public long getHoursForType(Integer typeId) {
        final Interval interval = timesByType.get(typeId);
        if(interval==null) {
            return defaultTime;
        }
        return interval.getRandom(random) / Interval.MILLIS_PER_HOUR;
    }

    public double getSpeed() {
        return speed;
    }
}
