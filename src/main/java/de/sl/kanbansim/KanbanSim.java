package de.sl.kanbansim;

import de.sl.view.ViewBounds;
import de.sl.view.swing.AppBase;
import de.sl.view.swing.SwingColorFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author SL
 */
public class KanbanSim extends AppBase {

    public KanbanSim(KanbanViewModel<Color, BufferedImage> viewModel) {
        super("kanban simulation 1.0", 1200, 800, viewModel);
    }

    @Override
    protected void initUI() {
        // we have no own UI
    }

    public static void main(String[] args) {

        final KanbanModel model1 = KanbanModel.getWipBoard();
        final KanbanModel model2 = KanbanModel.getOpenBoard();
        final KanbanCompareModel compareModel = new KanbanCompareModel(model1, model2);

        final KanbanViewModel<Color, BufferedImage> viewModel = new KanbanViewModel<>(new SwingColorFactory(), compareModel, new ViewBounds(0.025f, 0.025f, 0.95f, 0.95f));

        compareModel.start();

        EventQueue.invokeLater(() -> {
            KanbanSim app = new KanbanSim(viewModel);
            app.setVisible(true);
            app.start();
        });
    }
}
