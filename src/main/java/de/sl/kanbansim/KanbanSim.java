package de.sl.kanbansim;

import de.sl.view.Model;
import de.sl.view.swing.AppBase;
import de.sl.view.swing.SwingColorFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SL
 */
public class KanbanSim extends AppBase {

    KanbanSim() {
        super("kanban simulation 1.0");
    }

    @Override
    protected Model<Color, BufferedImage> initModel() {
        final List<Column> columns = new ArrayList<>();
        columns.add(new Column("ideas", 3));
        columns.add(new Column("analysis", 0));
        columns.add(new Column("concept", 2));
        return new KanbanModel<>(new SwingColorFactory(), columns);
    }

    @Override
    protected void initUI() {

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            KanbanSim app = new KanbanSim();
            app.setVisible(true);
            app.start();
        });

    }
}
