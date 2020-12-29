package de.sl.kanbansim;

import de.sl.model.ModelBase;

/**
 * @author SL
 */
public class KanbanCompareModel extends ModelBase {

    private final KanbanModel model1;
    private final KanbanModel model2;
    private final KanbanConfig config;

    public KanbanCompareModel(KanbanModel model1, KanbanModel model2, KanbanConfig config) {
        super(config.getSpeed());
        this.model1 = model1;
        this.model2 = model2;
        this.config = config;

        for(int i=0; i<config.getCardCount(); i++) {
            final Card card = new Card(i, config);
            model1.addCard(card);
            model2.addCard(card.getCopy());
        }
    }

    public KanbanModel getModel1() {
        return model1;
    }

    public KanbanModel getModel2() {
        return model2;
    }

    @Override
    protected void simulate(long elapsedMillis) {
        model1.simulate(elapsedMillis, this);
        model2.simulate(elapsedMillis, this);
    }
}
