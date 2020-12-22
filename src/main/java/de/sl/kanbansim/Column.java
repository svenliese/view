package de.sl.kanbansim;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SL
 */
public class Column {

    private static int nextId = 0;

    private final Column parent;

    private final Integer id;

    private final String name;

    private final int wip;

    private final List<Column> children = new ArrayList<>();

    private int ticketCount;

    public Column(Column parent, String name, int wip) {
        this.parent = parent;
        this.id = Integer.valueOf(++nextId);
        this.name = name;
        this.wip = wip;

        this.ticketCount = 0;
    }

    public Column(String name, int wip) {
        this(null, name, wip);
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

    public void addTicket() {
        ticketCount++;
    }

    public void removeTicket() {
        ticketCount--;
    }

    public int getTicketCount() {
        if(children.isEmpty()) {
            return ticketCount;
        }
        int sum = 0;
        for(Column child : children) {
            sum += child.getTicketCount();
        }
        return sum;
    }
}
