package de.sl.view.swing;

import de.sl.view.LineProperties;
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

        final double x = rect.getXPercentage() * width;
        final double y = rect.getYPercentage() * height;
        final double w = rect.getWPercentage() * width;
        final double h = rect.getHPercentage() * height;

        final Color bgColor = rect.getColor();
        if(bgColor!=null) {
            g2d.setColor(bgColor);
            g2d.fillRect((int) x, (int) y, (int) w, (int) h);
        }

        final LineProperties<Color> border = rect.getBorder();
        if(border!=null) {
            g2d.setStroke(new BasicStroke(border.calculateThickness((float)Math.min(w, h))));
            g2d.setColor(border.getColor());
            g2d.drawRect((int)x, (int)y, (int)w, (int)h);
        }
    }
}
