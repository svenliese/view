package de.sl.kanbansim;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SL
 */
public class Card {

    private final Map<Integer, Long> timeByType = new HashMap<>();

    public void addTime(Integer type, Long millis) {
        timeByType.put(type, millis);
    }

    public Long getTime(Integer type) {
        return timeByType.get(type);
    }
}
