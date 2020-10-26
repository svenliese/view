package de.sl.view.swing;

import de.sl.view.Line;
import de.sl.view.LineProperties;

import java.awt.*;

/**
 * @author SL
 */
public class SwingLine extends SwingView {

    private final Line<Color> line;

    public SwingLine(Line<Color> line) {
        super(line);
        this.line = line;
    }

    @Override
    public void draw(Graphics2D g2d, double width, double height) {

        final double w = line.getWPercentage() * width;
        final double h = line.getHPercentage() * height;

        final double x1;
        final double y1;
        final double x2;
        final double y2;

        if(line.getMode()==Line.HORIZONTAL) {
            x1 = line.getXPercentage() * width;
            y1 = line.getYPercentage() * height + h/2.0d;
            x2 = x1 + w;
            y2 = y1;
        } else {
            x1 = line.getXPercentage() * width + w/2.0d;
            y1 = line.getYPercentage() * height;
            x2 = x1;
            y2 = y1 + h;
        }
        g2d.setStroke(new BasicStroke(line.getThickness()));
        g2d.setColor(line.getColor());
        g2d.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }
}
