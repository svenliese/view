package de.sl.view.swing;

import de.sl.view.Rect;

import java.awt.*;

/**
 * @author SL
 */
public class SwingRect extends SwingView {

    private final Rect<Color> rect;

    public SwingRect(Rect<Color> rect) {
        super(rect);
        this.rect = rect;
    }

    @Override
    public void draw(Graphics2D g2d, double width, double height) {
        g2d.setColor(rect.getColor());

        final double x = rect.getXPercentage() * width;
        final double y = rect.getYPercentage() * height;
        final double w = rect.getWPercentage() * width;
        final double h = rect.getHPercentage() * height;

        g2d.fillRect((int)x, (int)y, (int)w, (int)h);
    }
}
