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
        drawText(g2d, simpleText, width, height);
    }
}
