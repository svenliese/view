package de.sl.view.swing;


import de.sl.view.SimpleText;
import de.sl.view.TextButton;

import java.awt.*;

/**
 * @author SL
 */
public class SwingTextButton extends SwingView {

    private final TextButton<Color> textButton;

    public SwingTextButton(TextButton<Color> textButton) {
        super(textButton);
        this.textButton = textButton;
    }

    @Override
    public void draw(Graphics2D g2d, double width, double height) {

        final double x = textButton.getXPercentage() * width;
        final double y = textButton.getYPercentage() * height;
        final double w = textButton.getWPercentage() * width;
        final double h = textButton.getHPercentage() * height;

        g2d.setColor(textButton.getBgColor());
        g2d.fillRect((int)x, (int)y, (int)w, (int)h);

        g2d.setColor(textButton.getTextColor());
        drawText(g2d, x, y, w, h, textButton.getText(), SimpleText.HORIZONTAL);

        g2d.setColor(textButton.getBorderColor());
        g2d.drawRoundRect((int)x, (int)y, (int)w, (int)h, 10, 10);
    }
}
