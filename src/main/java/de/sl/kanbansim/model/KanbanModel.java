package de.sl.kanbansim.model;

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

    private long blockedTimeSum = 0;

    private int blockedInIdeas = 0;

    private long lastElapsedMillis = -1;

    public KanbanModel(KanbanConfig config) {
        super(config.getMillisPerStep());
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

        final List<Card> cardsToPull = sourceColumn.getCardsToPull(elapsedMillis);
        for(Card cardToPull : cardsToPull) {

            if(sourceColumn.getTypeId().equals(KanbanModel.TYPE_IDEAS) && activeCards>=config.getMaxWorkers()) {
                break;
            }

            boolean canPull = targetColumn.canPull();

            if(canPull) {
                final Column targetParent = targetColumn.getParent();
                final Column sourceParent = sourceColumn.getParent();
                if(
                    targetParent!=null &&
                    targetParent!=sourceParent &&
                    targetParent.getWip()>0 &&
                    targetParent.getAllTicketCount()>=targetParent.getWip()
                ) {
                    canPull = false;
                }
            }

            if(canPull) {
                final long blockedTime = cardToPull.unblock(elapsedMillis);
                if(blockedTime>0) {
                    blockedTimeSum += blockedTime;

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

    public boolean simulate(long elapsedMillis, ModelBase modelBase) {

        if(cardsToProcess.isEmpty() && columns.get(0).getTicketCount()==0 && activeCards<config.getMaxWorkers()) {
            return false;
        }

        for(Column column : childColumns) {
            column.setModified(false);
        }

        long newElapsedDays = elapsedMillis/(60*60*1000)/config.getWorkingDayHours();
        if(newElapsedDays>elapsedDays) {
            elapsedDays = newElapsedDays;
            // the 'done' column contains additional info, so we have to update the view
            // todo: remove this from the model because it is view related
            columns.get(columns.size()-1).setModified(true);
        }

        //
        // fill backlog
        //

        final Column backlogColumn = childColumns.get(0);
        while(backlogColumn.canPull() && !cardsToProcess.isEmpty()) {
            backlogColumn.addTicket(cardsToProcess.poll(), elapsedMillis);
            backlogColumn.setModified(true);
        }

        //
        // move cards over the board from right to left
        //

        final int beforeFinished = childColumns.get(childColumns.size()-1).getTicketCount();
        for(int colIdx=childColumns.size()-1; colIdx>0; colIdx--) {

            final Column targetColumn = childColumns.get(colIdx);
            final Column sourceColumn = childColumns.get(colIdx - 1);

            if (move(sourceColumn, targetColumn, elapsedMillis)) {
                targetColumn.setModified(true);
                sourceColumn.setModified(true);
                // the 'done' column contains additional info, so we have to update the view
                // todo: remove this from the model because it is view related
                columns.get(columns.size() - 1).setModified(true);
            }
        }
        final int newFinished = childColumns.get(childColumns.size()-1).getTicketCount() - beforeFinished;

        if(activeCards>config.getMaxWorkers()) {
            throw new IllegalStateException(activeCards + " active cards");
        }

        if(lastElapsedMillis>0) {
            final int notCountedInactiveWorkers = config.getMaxWorkers() - activeCards - blockedInIdeas - newFinished;
            if(notCountedInactiveWorkers<0){
                System.out.println("notCountedInactiveWorkers="+notCountedInactiveWorkers);
            }
            if(notCountedInactiveWorkers>0) {
                blockedTimeSum += (elapsedMillis-lastElapsedMillis)*notCountedInactiveWorkers;
                // the 'done' column contains additional info, so we have to update the view
                // todo: remove this from the model because it is view related
                columns.get(columns.size() - 1).setModified(true);
            }
        }
        lastElapsedMillis = elapsedMillis;

        for(Column column : childColumns) {
            if(column.isModified()) {
                modelBase.informListeners(column);
            }
        }

        return true;
    }

    public int getMaxWorkers() {
        return config.getMaxWorkers();
    }

    public long getBlockedTimeSum() {
        return blockedTimeSum;
    }

    public long getElapsedDays() {
        return elapsedDays;
    }

    @Override
    protected boolean simulate(long elapsedMillis) {
        return simulate(elapsedMillis, this);
    }
}