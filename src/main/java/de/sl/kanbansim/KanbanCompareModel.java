package de.sl.kanbansim;

/**
 * @author SL
 */
public class KanbanCompareModel {

    private final KanbanModel model1;
    private final KanbanModel model2;

    public KanbanCompareModel(KanbanModel model1, KanbanModel model2) {
        this.model1 = model1;
        this.model2 = model2;
    }

    public KanbanModel getModel1() {
        return model1;
    }

    public KanbanModel getModel2() {
        return model2;
    }
}
