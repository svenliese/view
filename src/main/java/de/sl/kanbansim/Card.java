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

    public Card(int id, KanbanConfig config) {
        this.id = id;
        this.defaultMillis = Long.valueOf(config.getDefaultMillis());
        for(Integer typeId : config.getConfiguredTypeIds()) {
            millisByType.put(typeId, Long.valueOf(config.getMillisForType(typeId)));
        }
    }

    public Long getMillis(Integer type) {
        Long result = millisByType.get(type);
        if(result==null) {
            result = defaultMillis;
        }
        return result;
    }

    @Override
    public int compareTo(Card other) {
        return Integer.compare(id, other.id);
    }
}
