package de.sl.view.swing;

import de.sl.view.IView;
import de.sl.view.Rect;
import de.sl.view.SimpleText;
import de.sl.view.TextButton;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author SL
 */
public class SwingViewFactory {

    public SwingView create(IView<Color> e) {
        SwingView view = null;
        if(e instanceof Rect) {
            view = new SwingRect((Rect<Color>)e);
        } else if(e instanceof TextButton) {
            view = new SwingTextButton((TextButton<Color>)e);
        } else if(e instanceof SimpleText) {
            view = new SwingSimpleText((SimpleText<Color>) e);
        } else if(e instanceof de.sl.view.Image) {
            view = new SwingImage((de.sl.view.Image<Color,BufferedImage>)e);
        }
        return view;
    }
}
