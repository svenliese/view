package de.sl.kanbansim;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SL
 */
public class Card implements Comparable<Card> {

    private final int id;

    private final Map<Integer, Long> millisByType = new HashMap<>();

    private final Long defaultMillis;

    private boolean blocked = false;
    private long startBlockMillis;

    public Card(int id, KanbanConfig config) {
        this.id = id;
        this.defaultMillis = Long.valueOf(config.getDefaultMillis());
        for(Integer typeId : config.getConfiguredTypeIds()) {
            millisByType.put(typeId, Long.valueOf(config.getMillisForType(typeId)));
        }
    }

    public Card(int id, Map<Integer, Long> millisByType, Long defaultMillis) {
        this.id = id;
        this.millisByType.putAll(millisByType);
        this.defaultMillis = defaultMillis;
    }

    public Card getCopy() {
        return new Card(id, millisByType, defaultMillis);
    }

    public Long getMillis(Integer type) {
        Long result = millisByType.get(type);
        if(result==null) {
            result = defaultMillis;
        }
        return result;
    }

    public void setBlocked(long elapsedMillis) {
        if(!blocked) {
            blocked = true;
            startBlockMillis = elapsedMillis;
        }
    }

    public boolean isBlocked() {
        return blocked;
    }

    public long unblock(long elapsedMillis) {
        long blockedMillis = 0;
        if(blocked) {
            blocked = false;
            blockedMillis = elapsedMillis-startBlockMillis;
        }
        return blockedMillis;
    }

    public long getStartBlockMillis() {
        return startBlockMillis;
    }

    public long getSmallestMillis() {
        long min = defaultMillis.longValue();
        for(Long millis : millisByType.values()) {
            if(millis.longValue()<min) {
                min = millis.longValue();
            }
        }
        return min;
    }

    @Override
    public int compareTo(Card other) {
        return Integer.compare(id, other.id);
    }
}
