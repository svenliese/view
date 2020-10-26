package de.sl.view.swing;

import de.sl.view.IView;
import de.sl.view.IViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SL
 */
public class SwingPanel extends JPanel implements MouseListener {

    private final transient IViewModel<Color> model;

    private transient java.util.List<SwingView> swingObjects;

    public SwingPanel(IViewModel<Color> model) {
        this.model = model;

        initialize();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawAll(g);
    }

    private void initialize() {

        initializeViews();

        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        model.touchDown(
            (float) event.getX() / (float) getWidth(),
            (float) event.getY() / (float) getHeight()
        );
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // not needed
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // not needed
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // not needed
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // not needed
    }

    private void initializeViews() {

        final SwingViewFactory objectFactory = new SwingViewFactory();

        List<IView<Color>> entities = model.getViews();
        swingObjects = new ArrayList<>(entities.size());
        for (IView<Color> e : entities) {
            final SwingView view = objectFactory.create(e);
            if (view != null) {
                swingObjects.add(view);
            } else {
                throw new IllegalStateException("now view for " + e.getClass());
            }
        }
    }

    public void simulate() {
        if (model.simulate(System.currentTimeMillis())) {
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawAll(g);
    }

    private void drawAll(Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        rh.put(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY
        );

        g2d.setRenderingHints(rh);

        Dimension size = getSize();
        double w = size.getWidth();
        double h = size.getHeight();

        g2d.setColor(model.getBgColor());
        g2d.fillRect(0, 0, (int) w, (int) h);

        for (SwingView o : swingObjects) {
            if (o.isVisible()) {
                o.draw(g2d, w, h);
            }
        }
    }
}