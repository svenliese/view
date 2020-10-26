package de.sl.view.swing;

import de.sl.view.Image;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author SL
 */
public class SwingImage extends SwingView {

    private final de.sl.view.Image<Color, BufferedImage> image;

    public SwingImage(de.sl.view.Image<Color, BufferedImage> image) {
        super(image);
        this.image = image;
    }

    @Override
    public void draw(Graphics2D g2d, double width, double height) {

        final BufferedImage bufferedImage = image.getBitmap();
        final double imageWidth = bufferedImage.getWidth();
        final double imageHeight = bufferedImage.getHeight();

        double x = image.getXPercentage() * width;
        double y = image.getYPercentage() * height;
        double w = image.getWPercentage() * width;
        double h = image.getHPercentage() * height;

        if(image.getMode() == Image.USE_ORIGIN) {
            x += w/2.0d - imageWidth/2.0d;
            y += h/2.0d - imageHeight/2.0d;
            g2d.drawImage(bufferedImage, (int) x, (int) y, null);
        } else {
            final double factor = Math.min(w/imageWidth, h/imageHeight);
            x += w/2.0d - imageWidth*factor/2.0d;
            y += h/2.0d - imageHeight*factor/2.0d;
            w = imageWidth * factor;
            h = imageHeight * factor;
            g2d.drawImage(bufferedImage, (int) x, (int) y, (int) w, (int) h, null);
        }
    }
}
