package de.sl.view.swing;

import de.sl.view.SimpleText;

import java.awt.*;

/**
 * @author SL
 */
public class SwingSimpleText extends SwingView {

    private final SimpleText<Color> simpleText;

    public SwingSimpleText(SimpleText<Color> simpleText) {
        super(simpleText);
        this.simpleText = simpleText;
    }

    @Override
    public void draw(Graphics2D g2d, double width, double height) {

        final double x = simpleText.getXPercentage() * width;
        final double y = simpleText.getYPercentage() * height;
        final double w = simpleText.getWPercentage() * width;
        final double h = simpleText.getHPercentage() * height;


        g2d.setColor(simpleText.getColor());
        drawText(g2d, x, y, w, h, simpleText.getText(), simpleText.getOrientation());
    }
}
