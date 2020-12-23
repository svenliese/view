package de.sl.kanbansim;

import de.sl.model.ModelBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SL
 */
public class KanbanModel {

    public static KanbanModel getWipBoard() {
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

        model.addColumn(new Column("done", 0));

        return model;
    }

    public static KanbanModel getOpenBoard() {
        final KanbanModel model = new KanbanModel();

        model.addColumn(new Column("ideas", 0));

        final Column discovery = new Column("discovery", 0);
        discovery.addChild(new Column("analysis", 0));
        discovery.addChild(new Column("concept", 0));
        discovery.addChild(new Column("ready", 0));
        model.addColumn(discovery);

        final Column dos = new Column("DOS", 0);
        dos.addChild(new Column("doing", 0));
        dos.addChild(new Column("ready", 0));
        model.addColumn(dos);

        final Column implementation = new Column("implementation", 0);
        implementation.addChild(new Column("prepare", 0));
        implementation.addChild(new Column("work", 0));
        implementation.addChild(new Column("deploy", 0));
        implementation.addChild(new Column("ready", 0));
        model.addColumn(implementation);

        model.addColumn(new Column("review", 0));

        model.addColumn(new Column("done", 0));

        return model;
    }

    private final List<Column> columns = new ArrayList<>();

    private long lastNewCard = 0;

    public void addColumn(Column column) {
        columns.add(column);
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void simulate(long elapsedHours, ModelBase modelBase) {
        if(elapsedHours>lastNewCard) {
            lastNewCard = elapsedHours;
            columns.get(0).addTicket();
            modelBase.informListeners(columns.get(0));
        }
    }
}
