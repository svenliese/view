package de.sl.kanbansim;

/**
 * @author SL
 */
public class Column {

    private String name;

    private final int wip;

    public Column(String name, int wip) {
        this.name = name;
        this.wip = wip;
    }

    public String getName() {
        return name;
    }

    public int getWip() {
        return wip;
    }
}
