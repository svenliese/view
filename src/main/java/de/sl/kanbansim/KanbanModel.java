package de.sl.kanbansim;

import de.sl.model.ModelBase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author SL
 */
public class KanbanModel extends ModelBase {

    public static final Integer TYPE_IDEAS = Integer.valueOf(1);
    public static final Integer TYPE_ANALYSIS = Integer.valueOf(3);
    public static final Integer TYPE_CONCEPT = Integer.valueOf(4);
    public static final Integer TYPE_PREPARE = Integer.valueOf(10);
    public static final Integer TYPE_WORK = Integer.valueOf(11);
    public static final Integer TYPE_DEPLOY = Integer.valueOf(12);
    public static final Integer TYPE_READY_REVIEW = Integer.valueOf(13);
    public static final Integer TYPE_DONE = Integer.valueOf(15);

    public static KanbanModel getWipBoard(KanbanConfig config) {
        final KanbanModel model = new KanbanModel(config);

        model.addColumn(new Column("ideas", TYPE_IDEAS, 3));

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

        model.addColumn(new Column("done", TYPE_DONE, 0));

        return model;
    }

    public static KanbanModel getOpenBoard(KanbanConfig config) {
        final KanbanModel model = new KanbanModel(config);

        model.addColumn(new Column("ideas", TYPE_IDEAS, 3));

        final Column discovery = new Column("discovery", Integer.valueOf(2), 2);
        discovery.addChild(new Column(discovery, "analysis", TYPE_ANALYSIS, 0));
        discovery.addChild(new Column(discovery, "concept", TYPE_CONCEPT, 0));
        discovery.addChild(new Column(discovery, "ready", Integer.valueOf(5), 0));
        model.addColumn(discovery);

        final Column dos = new Column("DOS", Integer.valueOf(6), 0);
        dos.addChild(new Column(dos, "doing", Integer.valueOf(7), 0));
        dos.addChild(new Column(dos, "ready", Integer.valueOf(8), 0));
        model.addColumn(dos);

        final Column implementation = new Column("implementation", Integer.valueOf(9), 4);
        implementation.addChild(new Column(implementation, "prepare", TYPE_PREPARE, 0));
        implementation.addChild(new Column(implementation, "work", TYPE_WORK, 0));
        implementation.addChild(new Column(implementation, "deploy", TYPE_DEPLOY, 0));
        implementation.addChild(new Column(implementation, "ready", TYPE_READY_REVIEW, 0));
        model.addColumn(implementation);

        model.addColumn(new Column("review", Integer.valueOf(14), 1));

        model.addColumn(new Column("done", TYPE_DONE, 0));

        return model;
    }

    private final KanbanConfig config;

    private final List<Column> columns = new ArrayList<>();

    private final List<Column> childColumns = new ArrayList<>();

    private final Queue<Card> cardsToProcess = new LinkedList<>();

    private long elapsedDays = 0;

    private int activeCards = 0;

    private long waitingTime = 0;

    private int blockedInIdeas = 0;

    public KanbanModel(KanbanConfig config) {
        super(config.getSpeed());
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

    /**
     * @return true in case of changes
     */
    private boolean move(Column sourceColumn, Column targetColumn, long elapsedMillis) {
        boolean modified = false;
        final Card cardToPull = sourceColumn.getCardToPull(elapsedMillis);
        if (cardToPull != null) {

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
                final long blockedTime = cardToPull.unblock(elapsedMillis);
                if(blockedTime>0) {
                    waitingTime += blockedTime;

                    if(sourceColumn.getTypeId().equals(KanbanModel.TYPE_IDEAS)) {
                        blockedInIdeas--;
                    }
                }

                sourceColumn.removeTicket(cardToPull);
                targetColumn.addTicket(cardToPull, elapsedMillis);
                if (targetColumn.getTypeId().equals(KanbanModel.TYPE_DONE)) {
                    activeCards--;
                } else if (sourceColumn.getTypeId().equals(KanbanModel.TYPE_IDEAS)) {
                    activeCards++;
                }
                modified = true;
            } else if(!cardToPull.isBlocked() && (!sourceColumn.getTypeId().equals(KanbanModel.TYPE_IDEAS) || activeCards+blockedInIdeas<config.getMaxWorkers())) {
                cardToPull.setBlocked(elapsedMillis);
                modified = true;

                if(sourceColumn.getTypeId().equals(KanbanModel.TYPE_IDEAS)) {
                    blockedInIdeas++;
                }
            }
        }
        return modified;
    }

    public void simulate(long elapsedMillis, ModelBase modelBase) {


        long newElapsedDays = elapsedMillis/(60*60*1000)/config.getWorkingDayHours();
        if(newElapsedDays>elapsedDays) {
            elapsedDays = newElapsedDays;
            informListeners(columns.get(columns.size()-1));
        }

        //
        // fill backlog
        //

        final Column backlogColumn = childColumns.get(0);
        while(backlogColumn.canPull() && !cardsToProcess.isEmpty()) {
            backlogColumn.addTicket(cardsToProcess.poll(), elapsedMillis);
            modelBase.informListeners(backlogColumn);
        }

        //
        // move cards over the board from right to left
        //

        for(int colIdx=childColumns.size()-1; colIdx>0; colIdx--) {

            final Column targetColumn = childColumns.get(colIdx);
            final Column sourceColumn = childColumns.get(colIdx-1);

            while(move(sourceColumn, targetColumn, elapsedMillis)) {
                modelBase.informListeners(targetColumn);
                modelBase.informListeners(sourceColumn);
                modelBase.informListeners(columns.get(columns.size()-1));
            }
        }
    }

    public int getMaxWorkers() {
        return config.getMaxWorkers();
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public long getElapsedDays() {
        return elapsedDays;
    }

    @Override
    protected void simulate(long elapsedMillis) {
        simulate(elapsedMillis, this);
    }
}
