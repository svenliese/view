package de.sl.kanbansim.model;

import de.sl.model.Interval;

import java.util.*;

/**
 * @author SL
 */
public class KanbanConfig {

    public static final int STOP_WHEN_NO_CARD = 1;
    public static final int STOP_WHEN_FIRST_JOBLESS = 2;

    public static final int COUNT_BLOCKING_FROM_BEGINNING = 3;
    public static final int COUNT_BLOCKING_FIRST_DONE = 4;

    private final Random random = new Random(7);

    private final int cardCount;

    private final int maxWorkers;

    private final int workingDayHours;

    private final long defaultMillis;

    private final long millisPerStep;

    private final int stopMode;

    private final int blockMode;

    private final double leadTimePercentage = 0.8d;

    private final Map<Integer, Interval> millisByType = new TreeMap<>();

    public KanbanConfig(int cardCount, int maxWorkers, int workingDayHours, long defaultMillis, long millisPerStep, int stopMode, int blockMode) {
        this.cardCount = cardCount;
        this.maxWorkers = maxWorkers;
        this.workingDayHours = workingDayHours;
        this.defaultMillis = defaultMillis;
        this.millisPerStep = millisPerStep;
        this.stopMode = stopMode;
        this.blockMode = blockMode;
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

    public int getBlockMode() {
        return blockMode;
    }

    public double getLeadTimePercentage() {
        return leadTimePercentage;
    }
}
