package de.sl.kanbansim;

import de.sl.view.Model;
import de.sl.view.ViewBounds;
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
        super("kanban simulation 1.0", 1200, 800);
    }

    @Override
    protected Model<Color, BufferedImage> initModel() {
        final List<Column> columns = new ArrayList<>();

        columns.add(new Column("ideas", 3));

        final Column discovery = new Column("discovery", 3);
        discovery.addChild(new Column("analysis", 0));
        discovery.addChild(new Column("concept", 0));
        discovery.addChild(new Column("ready", 0));
        columns.add(discovery);

        final Column dos = new Column("DOS", 1);
        dos.addChild(new Column("doing", 0));
        dos.addChild(new Column("ready", 0));
        columns.add(dos);

        final Column implementation = new Column("implementation", 3);
        implementation.addChild(new Column("understanding", 0));
        implementation.addChild(new Column("further work", 0));
        implementation.addChild(new Column("deployment", 0));
        implementation.addChild(new Column("ready", 0));
        columns.add(implementation);

        columns.add(new Column("review", 1));

        return new KanbanModel<>(new SwingColorFactory(), columns, new ViewBounds(0.05f, 0.05f, 0.9f, 0.9f));
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
