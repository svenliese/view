package de.sl.view.swing;

import de.sl.view.LineProperties;
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
        g2d.fillRoundRect((int)x, (int)y, (int)w, (int)h, 10, 10);

        drawText(g2d, textButton, width, height);

        final LineProperties<Color> border = textButton.getBorder();
        if(border!=null) {
            g2d.setStroke(new BasicStroke(border.calculateThickness((float)Math.min(w, h))));
            g2d.setColor(border.getColor());
            g2d.drawRoundRect((int)x, (int)y, (int)w, (int)h, 10, 10);
        }
    }
}
