package de.sl.kanbansim;

import de.sl.kanbansim.model.KanbanCompareModel;
import de.sl.kanbansim.model.KanbanConfig;
import de.sl.kanbansim.model.KanbanModel;
import de.sl.kanbansim.view.KanbanCompareModelView;
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

        final KanbanConfig config = new KanbanConfig(
            10,
            6,
            8,
            Interval.MILLIS_PER_HOUR,
            28800.0d
        );
        config.addInterval(KanbanModel.TYPE_ANALYSIS, Interval.getHourInterval(config.getWorkingDayHours(), 3*config.getWorkingDayHours()));
        config.addInterval(KanbanModel.TYPE_CONCEPT, Interval.getHourInterval(config.getWorkingDayHours(), 3*config.getWorkingDayHours()));
        config.addInterval(KanbanModel.TYPE_PREPARE, Interval.getHourInterval(config.getWorkingDayHours(), 2*config.getWorkingDayHours()));
        config.addInterval(KanbanModel.TYPE_WORK, Interval.getHourInterval(config.getWorkingDayHours(), 4*config.getWorkingDayHours()));
        config.addInterval(KanbanModel.TYPE_DEPLOY, Interval.getHourInterval(config.getWorkingDayHours(), 2*config.getWorkingDayHours()));
        config.addInterval(KanbanModel.TYPE_READY_REVIEW, Interval.getHourInterval(6, 24));

        final KanbanModel model1 = KanbanModel.getWipBoard(config);
        final KanbanModel model2 = KanbanModel.getOpenBoard(config);
        final KanbanCompareModel compareModel = new KanbanCompareModel(model1, model2, config);

        final KanbanCompareModelView<Color, BufferedImage> viewModel = new KanbanCompareModelView<>(
            new SwingColorFactory(),
            compareModel,
            new ViewBounds(0.01f, 0.01f, 0.98f, 0.98f)
        );

        compareModel.start();

        EventQueue.invokeLater(() -> {
            KanbanSim app = new KanbanSim(viewModel);
            app.setVisible(true);
            app.start();
        });
    }
}
