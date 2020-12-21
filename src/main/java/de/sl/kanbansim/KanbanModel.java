package de.sl.kanbansim;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SL
 */
public class KanbanModel {

    public static KanbanModel getIgusBoard() {
        final KanbanModel model = new KanbanModel();

        model.addColumn(new Column("ideas", 3));

        final Column discovery = new Column("discovery", 3);
        discovery.addChild(new Column("analysis", 0));
        discovery.addChild(new Column("concept", 0));
        discovery.addChild(new Column("ready", 0));
        model.addColumn(discovery);

        final Column dos = new Column("DOS", 1);
        dos.addChild(new Column("doing", 0));
        dos.addChild(new Column("ready", 0));
        model.addColumn(dos);

        final Column implementation = new Column("implementation", 3);
        implementation.addChild(new Column("prepare", 0));
        implementation.addChild(new Column("work", 0));
        implementation.addChild(new Column("deploy", 0));
        implementation.addChild(new Column("ready", 0));
        model.addColumn(implementation);

        model.addColumn(new Column("review", 1));

        return model;
    }

    private final List<Column> columns = new ArrayList<>();

    public void addColumn(Column column) {
        columns.add(column);
    }

    public List<Column> getColumns() {
        return columns;
    }
}
