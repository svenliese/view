package de.sl.kanbansim;

import de.sl.model.ModelBase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author SL
 */
public class KanbanModel {

    public static KanbanModel getWipBoard(KanbanConfig config) {
        final KanbanModel model = new KanbanModel(config);

        model.addColumn(new Column("ideas", Integer.valueOf(1), 3));

        final Column discovery = new Column("discovery", Integer.valueOf(2), 3);
        discovery.addChild(new Column(discovery, "analysis", Integer.valueOf(3), 0));
        discovery.addChild(new Column(discovery, "concept", Integer.valueOf(4), 0));
        discovery.addChild(new Column(discovery, "ready", Integer.valueOf(5), 0));
        model.addColumn(discovery);

        final Column dos = new Column("DOS", Integer.valueOf(6), 1);
        dos.addChild(new Column(dos, "doing", Integer.valueOf(7), 0));
        dos.addChild(new Column(dos, "ready", Integer.valueOf(8), 0));
        model.addColumn(dos);

        final Column implementation = new Column("implementation", Integer.valueOf(9), 3);
        implementation.addChild(new Column(implementation, "prepare", Integer.valueOf(10), 0));
        implementation.addChild(new Column(implementation, "work", Integer.valueOf(11), 0));
        implementation.addChild(new Column(implementation, "deploy", Integer.valueOf(12), 0));
        implementation.addChild(new Column(implementation, "ready", Integer.valueOf(13), 0));
        model.addColumn(implementation);

        model.addColumn(new Column("review", Integer.valueOf(14), 1));

        model.addColumn(new Column("done", Integer.valueOf(15), 0));

        return model;
    }

    public static KanbanModel getOpenBoard(KanbanConfig config) {
        final KanbanModel model = new KanbanModel(config);

        model.addColumn(new Column("ideas", Integer.valueOf(1), 3));

        final Column discovery = new Column("discovery", Integer.valueOf(2), 0);
        discovery.addChild(new Column(discovery, "analysis", Integer.valueOf(3), 0));
        discovery.addChild(new Column(discovery, "concept", Integer.valueOf(4), 0));
        discovery.addChild(new Column(discovery, "ready", Integer.valueOf(5), 0));
        model.addColumn(discovery);

        final Column dos = new Column("DOS", Integer.valueOf(6), 0);
        dos.addChild(new Column(dos, "doing", Integer.valueOf(7), 0));
        dos.addChild(new Column(dos, "ready", Integer.valueOf(8), 0));
        model.addColumn(dos);

        final Column implementation = new Column("implementation", Integer.valueOf(9), 0);
        implementation.addChild(new Column(implementation, "prepare", Integer.valueOf(10), 0));
        implementation.addChild(new Column(implementation, "work", Integer.valueOf(11), 0));
        implementation.addChild(new Column(implementation, "deploy", Integer.valueOf(12), 0));
        implementation.addChild(new Column(implementation, "ready", Integer.valueOf(13), 0));
        model.addColumn(implementation);

        model.addColumn(new Column("review", Integer.valueOf(14), 1));

        model.addColumn(new Column("done", Integer.valueOf(15), 0));

        return model;
    }

    private final KanbanConfig config;

    private final List<Column> columns = new ArrayList<>();

    private final List<Column> childColumns = new ArrayList<>();

    private Queue<Card> cardsToProcess = new LinkedList<>();

    private int activeCards = 0;

    public KanbanModel(KanbanConfig config) {
        this.config = config;
    }

    public void addCard(Card card) {
        cardsToProcess.add(card);
    }

    public void addColumn(Column column) {
        columns.add(column);
        childColumns.addAll(column.getAllChildren());
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void simulate(long elapsedHours, ModelBase modelBase) {

        //
        // fill backlog
        //

        final Column backlogColumn = childColumns.get(0);
        while(backlogColumn.canPull() && !cardsToProcess.isEmpty()) {
            backlogColumn.addTicket(cardsToProcess.poll(), elapsedHours);
            modelBase.informListeners(backlogColumn);
        }

        //
        // move cards over the board
        //

        for(int colIdx=childColumns.size()-1; colIdx>0; colIdx--) {
            final Column targetColumn = childColumns.get(colIdx);
            final Column sourceColumn = childColumns.get(colIdx-1);

            boolean canPull = targetColumn.canPull();
            if(canPull) {
                final Column targetParent = targetColumn.getParent();
                final Column sourceParent = sourceColumn.getParent();
                if(targetParent!=null && targetParent!=sourceParent) {
                    if(targetParent.getWip()>0 && targetParent.getAllTicketCount()>=targetParent.getWip()) {
                        canPull = false;
                    }
                }
            }

            if(canPull) {
                final Card cardToPull = sourceColumn.getCardToPull(elapsedHours);
                if (cardToPull != null) {
                    sourceColumn.removeTicket(cardToPull);
                    targetColumn.addTicket(cardToPull, elapsedHours);
                    if(colIdx==childColumns.size()-1) {
                        activeCards--;
                    } else if(colIdx==1) {
                        activeCards++;
                    }
                    modelBase.informListeners(targetColumn);
                    modelBase.informListeners(sourceColumn);
                }
            }
        }
    }
}
