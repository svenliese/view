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
        discovery.addChild(new Column("analysis", Integer.valueOf(3), 0));
        discovery.addChild(new Column("concept", Integer.valueOf(4), 0));
        discovery.addChild(new Column("ready", Integer.valueOf(5), 0));
        model.addColumn(discovery);

        final Column dos = new Column("DOS", Integer.valueOf(6), 1);
        dos.addChild(new Column("doing", Integer.valueOf(7), 0));
        dos.addChild(new Column("ready", Integer.valueOf(8), 0));
        model.addColumn(dos);

        final Column implementation = new Column("implementation", Integer.valueOf(9), 3);
        implementation.addChild(new Column("prepare", Integer.valueOf(10), 0));
        implementation.addChild(new Column("work", Integer.valueOf(11), 0));
        implementation.addChild(new Column("deploy", Integer.valueOf(12), 0));
        implementation.addChild(new Column("ready", Integer.valueOf(13), 0));
        model.addColumn(implementation);

        model.addColumn(new Column("review", Integer.valueOf(14), 1));

        model.addColumn(new Column("done", Integer.valueOf(15), 0));

        return model;
    }

    public static KanbanModel getOpenBoard(KanbanConfig config) {
        final KanbanModel model = new KanbanModel(config);

        model.addColumn(new Column("ideas", Integer.valueOf(1), 0));

        final Column discovery = new Column("discovery", Integer.valueOf(2), 0);
        discovery.addChild(new Column("analysis", Integer.valueOf(3), 0));
        discovery.addChild(new Column("concept", Integer.valueOf(4), 0));
        discovery.addChild(new Column("ready", Integer.valueOf(5), 0));
        model.addColumn(discovery);

        final Column dos = new Column("DOS", Integer.valueOf(6), 0);
        dos.addChild(new Column("doing", Integer.valueOf(7), 0));
        dos.addChild(new Column("ready", Integer.valueOf(8), 0));
        model.addColumn(dos);

        final Column implementation = new Column("implementation", Integer.valueOf(9), 0);
        implementation.addChild(new Column("prepare", Integer.valueOf(10), 0));
        implementation.addChild(new Column("work", Integer.valueOf(11), 0));
        implementation.addChild(new Column("deploy", Integer.valueOf(12), 0));
        implementation.addChild(new Column("ready", Integer.valueOf(13), 0));
        model.addColumn(implementation);

        model.addColumn(new Column("review", Integer.valueOf(14), 1));

        model.addColumn(new Column("done", Integer.valueOf(15), 0));

        return model;
    }

    private final KanbanConfig config;

    private final List<Column> columns = new ArrayList<>();

    private Queue<Card> cardsToProcess = new LinkedList<>();
    private Card nextCard;

    private int activeCards = 0;

    public KanbanModel(KanbanConfig config) {
        this.config = config;
    }

    public void addCard(Card card) {
        cardsToProcess.add(card);
    }

    public void addColumn(Column column) {
        columns.add(column);
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void simulate(long elapsedHours, ModelBase modelBase) {

        if(nextCard==null && !cardsToProcess.isEmpty()) {
            nextCard = cardsToProcess.poll();
        }

        //
        // put new card to the backlog
        //

        final Column backlogColumn = columns.get(0);
        if(nextCard!=null && backlogColumn.isFree()) {
            backlogColumn.addTicket(nextCard, elapsedHours);
            modelBase.informListeners(backlogColumn);
            nextCard = null;
        }

        //
        // move card from backlog to the first column
        //

        final Column firstColumn = columns.get(1);
        if(activeCards<config.getActiveCardCount() && backlogColumn.getTicketCount()>0 && firstColumn.isFree()) {
            final Card cardToMove = backlogColumn.getCardToPull(elapsedHours);
            if(cardToMove!=null) {
                backlogColumn.removeTicket(cardToMove);
                firstColumn.addTicket(cardToMove, elapsedHours);
                activeCards++;
                modelBase.informListeners(firstColumn);
                modelBase.informListeners(backlogColumn);
            }
        }
    }
}
