package de.sl.kanbansim;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SL
 */
public class Column {

    private final Column parent;

    private final String name;

    private final int wip;

    private final List<Column> children = new ArrayList<>();

    public Column(Column parent, String name, int wip) {
        this.parent = parent;
        this.name = name;
        this.wip = wip;
    }

    public Column(String name, int wip) {
        this(null, name, wip);
    }

    public void addChild(Column child) {
        children.add(child);
    }

    public String getName() {
        return name;
    }

    public int getWip() {
        return wip;
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
}
