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

    public KanbanSim(KanbanViewModel viewModel) {
        super("kanban simulation 1.0", 1200, 800, viewModel);
    }

    @Override
    protected void initUI() {
        // we have no own UI
    }

    public static void main(String[] args) {

        final KanbanModel model = KanbanModel.getIgusBoard();
        final KanbanViewModel<Color, BufferedImage> viewModel = new KanbanViewModel<>(new SwingColorFactory(), model, new ViewBounds(0.05f, 0.05f, 0.9f, 0.9f));

        EventQueue.invokeLater(() -> {
            KanbanSim app = new KanbanSim(viewModel);
            app.setVisible(true);
            app.start();
        });
    }
}
