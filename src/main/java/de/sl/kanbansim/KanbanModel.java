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

    public static final Integer TYPE_ANALYSIS = Integer.valueOf(3);
    public static final Integer TYPE_CONCEPT = Integer.valueOf(4);
    public static final Integer TYPE_PREPARE = Integer.valueOf(10);
    public static final Integer TYPE_WORK = Integer.valueOf(11);
    public static final Integer TYPE_DEPLOY = Integer.valueOf(12);
    public static final Integer TYPE_READY_REVIEW = Integer.valueOf(13);

    public static KanbanModel getWipBoard(KanbanConfig config) {
        final KanbanModel model = new KanbanModel(config);

        model.addColumn(new Column("ideas", Integer.valueOf(1), 3));

        final Column discovery = new Column("discovery", Integer.valueOf(2), 3);
        discovery.addChild(new Column(discovery, "analysis", TYPE_ANALYSIS, 0));
        discovery.addChild(new Column(discovery, "concept", TYPE_CONCEPT, 0));
        discovery.addChild(new Column(discovery, "ready", Integer.valueOf(5), 0));
        model.addColumn(discovery);

        final Column dos = new Column("DOS", Integer.valueOf(6), 1);
        dos.addChild(new Column(dos, "doing", Integer.valueOf(7), 0));
        dos.addChild(new Column(dos, "ready", Integer.valueOf(8), 0));
        model.addColumn(dos);

        final Column implementation = new Column("implementation", Integer.valueOf(9), 3);
        implementation.addChild(new Column(implementation, "prepare", TYPE_PREPARE, 0));
        implementation.addChild(new Column(implementation, "work", TYPE_WORK, 0));
        implementation.addChild(new Column(implementation, "deploy", TYPE_DEPLOY, 0));
        implementation.addChild(new Column(implementation, "ready", TYPE_READY_REVIEW, 0));
        model.addColumn(implementation);

        model.addColumn(new Column("review", Integer.valueOf(14), 1));

        model.addColumn(new Column("done", Integer.valueOf(15), 0));

        return model;
    }

    public static KanbanModel getOpenBoard(KanbanConfig config) {
        final KanbanModel model = new KanbanModel(config);

        model.addColumn(new Column("ideas", Integer.valueOf(1), 3));

        final Column discovery = new Column("discovery", Integer.valueOf(2), 0);
        discovery.addChild(new Column(discovery, "analysis", TYPE_ANALYSIS, 0));
        discovery.addChild(new Column(discovery, "concept", TYPE_CONCEPT, 0));
        discovery.addChild(new Column(discovery, "ready", Integer.valueOf(5), 0));
        model.addColumn(discovery);

        final Column dos = new Column("DOS", Integer.valueOf(6), 0);
        dos.addChild(new Column(dos, "doing", Integer.valueOf(7), 0));
        dos.addChild(new Column(dos, "ready", Integer.valueOf(8), 0));
        model.addColumn(dos);

        final Column implementation = new Column("implementation", Integer.valueOf(9), 0);
        implementation.addChild(new Column(implementation, "prepare", TYPE_PREPARE, 0));
        implementation.addChild(new Column(implementation, "work", TYPE_WORK, 0));
        implementation.addChild(new Column(implementation, "deploy", TYPE_DEPLOY, 0));
        implementation.addChild(new Column(implementation, "ready", TYPE_READY_REVIEW, 0));
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
            if(colIdx==1 && activeCards>=config.getMaxWorkers()) {
                continue;
            }

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
