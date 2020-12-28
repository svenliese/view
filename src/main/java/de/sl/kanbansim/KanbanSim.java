package de.sl.kanbansim;

import de.sl.model.Interval;
import de.sl.view.ViewBounds;
import de.sl.view.swing.AppBase;
import de.sl.view.swing.SwingColorFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author SL
 */
public class KanbanSim extends AppBase {

    public KanbanSim(KanbanCompareModelView<Color, BufferedImage> viewModel) {
        super("kanban simulation", 1200, 800, viewModel);
    }

    @Override
    protected void initUI() {
        // we have no own UI
    }

    public static void main(String[] args) {

        final KanbanConfig config = new KanbanConfig(100, 6, Interval.MILLIS_PER_HOUR, 28800.0d);
        config.addInterval(KanbanModel.TYPE_ANALYSIS, Interval.getDayInterval(1, 3));
        config.addInterval(KanbanModel.TYPE_CONCEPT, Interval.getDayInterval(1, 3));
        config.addInterval(KanbanModel.TYPE_PREPARE, Interval.getDayInterval(1, 2));
        config.addInterval(KanbanModel.TYPE_WORK, Interval.getDayInterval(1, 4));
        config.addInterval(KanbanModel.TYPE_DEPLOY, Interval.getDayInterval(1, 2));
        config.addInterval(KanbanModel.TYPE_READY_REVIEW, Interval.getHourInterval(6, 24));

        final KanbanModel model1 = KanbanModel.getWipBoard(config);
        final KanbanModel model2 = KanbanModel.getOpenBoard(config);
        final KanbanCompareModel compareModel = new KanbanCompareModel(model1, model2, config);

        final KanbanCompareModelView<Color, BufferedImage> viewModel = new KanbanCompareModelView<>(
            new SwingColorFactory(),
            compareModel,
            new ViewBounds(0.025f, 0.025f, 0.95f, 0.95f)
        );

        compareModel.start();

        EventQueue.invokeLater(() -> {
            KanbanSim app = new KanbanSim(viewModel);
            app.setVisible(true);
            app.start();
        });
    }
}
