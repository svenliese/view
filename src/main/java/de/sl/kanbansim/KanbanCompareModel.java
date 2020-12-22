package de.sl.kanbansim;

import de.sl.model.ModelBase;

/**
 * @author SL
 */
public class KanbanCompareModel extends ModelBase {

    private final KanbanModel model1;
    private final KanbanModel model2;

    public KanbanCompareModel(KanbanModel model1, KanbanModel model2) {
        super(3600.0d);
        this.model1 = model1;
        this.model2 = model2;
    }

    public KanbanModel getModel1() {
        return model1;
    }

    public KanbanModel getModel2() {
        return model2;
    }

    @Override
    protected boolean simulate(long elapsedMillis) {
        boolean modified = model1.simulate(elapsedMillis/(60*60*1000));
        return modified | model2.simulate(elapsedMillis/(60*60*1000));
    }
}
