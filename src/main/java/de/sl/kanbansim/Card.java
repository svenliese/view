package de.sl.kanbansim;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SL
 */
public class Card {

    private final Map<Integer, Long> timeByType = new HashMap<>();

    private final Long defaultTime;

    public Card(Long defaultTime) {
        this.defaultTime = defaultTime;
    }

    public void addTime(Integer type, Long millis) {
        timeByType.put(type, millis);
    }

    public Long getTime(Integer type) {
        Long result = timeByType.get(type);
        if(result==null) {
            result = defaultTime;
        }
        return result;
    }
}
