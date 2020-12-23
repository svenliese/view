package de.sl.kanbansim;

import java.util.*;

/**
 * @author SL
 */
public class Column {

    private static int nextId = 0;

    private final Column parent;

    private final Integer id;

    private final Integer typeId;

    private final String name;

    private final int wip;

    private final List<Column> children = new ArrayList<>();

    private Map<Card, Long> cards = new HashMap<>();

    public Column(Column parent, String name, Integer typeId, int wip) {
        this.parent = parent;
        this.id = Integer.valueOf(++nextId);
        this.name = name;
        this.typeId = typeId;
        this.wip = wip;
    }

    public Column(String name, Integer typeId, int wip) {
        this(null, name, typeId, wip);
    }

    public void addChild(Column child) {
        children.add(child);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWip() {
        return wip;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public List<Column> getChildren() {
        return children;
    }

    public int getColumnSum() {
        if(children.isEmpty()) {
            return 1;
        }
        int sum = 0;
        for(Column column : children) {
            sum += column.getColumnSum();
        }
        return sum;
    }

    public boolean isFree() {
        return wip==0 || cards.size()<wip;
    }

    public void addTicket(Card card, long elapsedHours) {
        cards.put(card, Long.valueOf(elapsedHours));
    }

    public void removeTicket(Card card) {
        cards.remove(card);
    }

    public int getTicketCount() {
        return cards.size();
    }

    public Card getCardToPull(long elapsedHours) {
        for(Map.Entry<Card, Long> e : cards.entrySet()) {
            final Card card = e.getKey();
            final Long time = card.getTime(typeId);
            if(time==null || time.longValue() < elapsedHours - e.getValue().longValue()) {
                return card;
            }
        }
        return null;
    }
}
