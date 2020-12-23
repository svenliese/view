package de.sl.kanbansim;

import de.sl.model.ModelBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SL
 */
public class KanbanCompareModel extends ModelBase {

    private final KanbanConfig config;

    private final KanbanModel model1;
    private final KanbanModel model2;

    private long elapsedDays = 0;

    public KanbanCompareModel(KanbanModel model1, KanbanModel model2, KanbanConfig config) {
        super(7200.0d);
        this.model1 = model1;
        this.model2 = model2;
        this.config = config;

        for(int i=0; i<config.getCardCount(); i++) {
            final Card card = new Card(Long.valueOf(config.getDefaultTime()));
            model1.addCard(card);
            model2.addCard(card);
        }
    }

    public KanbanModel getModel1() {
        return model1;
    }

    public KanbanModel getModel2() {
        return model2;
    }

    public String getTimeInfoText() {
        return "Tage: "+elapsedDays;
    }

    @Override
    protected void simulate(long elapsedMillis) {
        model1.simulate(elapsedMillis/(60*60*1000), this);
        model2.simulate(elapsedMillis/(60*60*1000), this);

        long newElapsedDays = elapsedMillis/(24*60*60*1000);
        if(newElapsedDays>elapsedDays) {
            elapsedDays = newElapsedDays;
            informListeners(this);
        }
    }
}
