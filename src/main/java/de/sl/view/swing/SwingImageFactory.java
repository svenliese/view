package de.sl.view.swing;

import de.sl.view.IImageFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * @author SL
 */
public class SwingImageFactory implements IImageFactory<BufferedImage> {

    @Override
    public BufferedImage getImage(String resource) {
        final URL imageURL = SwingImage.class.getClassLoader().getResource(resource);
        if(imageURL==null) {
            throw new IllegalStateException("can't find "+resource);
        }

        try {
            return ImageIO.read(imageURL);
        } catch(IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
