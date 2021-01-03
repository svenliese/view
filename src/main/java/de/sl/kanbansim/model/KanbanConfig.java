package de.sl.kanbansim.model;

import de.sl.model.Interval;

import java.util.*;

/**
 * @author SL
 */
public class KanbanConfig {

    public static final int STOP_WHEN_NO_CARD = 1;
    public static final int STOP_WHEN_FIRST_JOBLESS = 2;

    private final Random random = new Random(7);

    private final int cardCount;

    private final int maxWorkers;

    private final int workingDayHours;

    private final long defaultMillis;

    private final long millisPerStep;

    private final int stopMode;

    private final Map<Integer, Interval> millisByType = new TreeMap<>();

    public KanbanConfig(int cardCount, int maxWorkers, int workingDayHours, long defaultMillis, long millisPerStep, int stopMode) {
        this.cardCount = cardCount;
        this.maxWorkers = maxWorkers;
        this.workingDayHours = workingDayHours;
        this.defaultMillis = defaultMillis;
        this.millisPerStep = millisPerStep;
        this.stopMode = stopMode;
    }

    public int getCardCount() {
        return cardCount;
    }

    public int getMaxWorkers() {
        return maxWorkers;
    }

    public int getWorkingDayHours() {
        return workingDayHours;
    }

    public long getDefaultMillis() {
        return defaultMillis;
    }

    public void addInterval(Integer typeId, Interval interval) {
        millisByType.put(typeId, interval);
    }

    public long getMillisForType(Integer typeId) {
        final Interval interval = millisByType.get(typeId);
        if(interval==null) {
            return defaultMillis;
        }
        return interval.getRandomMillis(random);
    }

    public Set<Integer> getConfiguredTypeIds() {
        return millisByType.keySet();
    }

    public long getMillisPerStep() {
        return millisPerStep;
    }

    public int getStopMode() {
        return stopMode;
    }
}
