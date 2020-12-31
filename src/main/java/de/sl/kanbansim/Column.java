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

    private boolean modified;

    /** map of card's and their time when entered this column */
    private final Map<Card, Long> cards = new TreeMap<>();

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

    public List<Column> getAllChildren() {
        final List<Column> result = new ArrayList<>();
        if(children.isEmpty()) {
            result.add(this);
        } else {
            for (Column column : children) {
                result.addAll(column.getAllChildren());
            }
        }
        return result;
    }

    public Column getParent() {
        return parent;
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

    public boolean canPull() {
        return wip==0 || cards.size() < wip;
    }

    public void addTicket(Card card, long elapsedMillis) {
        cards.put(card, Long.valueOf(elapsedMillis));
    }

    public void removeTicket(Card card) {
        cards.remove(card);
    }

    public int getTicketCount() {
        return cards.size();
    }

    public int getAllTicketCount() {
        int sum = 0;
        if(children.isEmpty()) {
            sum += cards.size();
        } else {
            for (Column column : children) {
                sum += column.getAllTicketCount();
            }
        }
        return sum;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public List<Card> getCardsToPull(long elapsedMillis) {

        final List<Card> cardsToPull = new ArrayList<>(cards.size());

        for(Map.Entry<Card, Long> e : cards.entrySet()) {
            final Card card = e.getKey();

            final long timeInColumn = elapsedMillis - e.getValue().longValue();
            final long plannedTime = card.getMillis(typeId).longValue();

            if(plannedTime < timeInColumn) {
                card.setWaitingTime(timeInColumn - plannedTime);
                cardsToPull.add(card);
            }
        }

        cardsToPull.sort(new CardWaitingTimeComparator());
        return cardsToPull;
    }

    public Set<Card> getCards() {
        return cards.keySet();
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return name;
    }
}
