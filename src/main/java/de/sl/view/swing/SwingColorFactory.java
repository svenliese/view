package de.sl.view.swing;

import de.sl.view.IColorFactory;

import java.awt.*;

/**
 * @author SL
 */
public class SwingColorFactory implements IColorFactory<Color> {

    @Override
    public Color[] getColorArray(int size) {
        return new Color[size];
    }

    @Override
    public Color getBlack() {
        return Color.BLACK;
    }

    @Override
    public Color getGray() {
        return Color.GRAY;
    }

    @Override
    public Color getLightGray() {
        return Color.LIGHT_GRAY;
    }

    @Override
    public Color getGreen() {
        return Color.GREEN;
    }

    @Override
    public Color getBlue() {
        return Color.BLUE;
    }

    @Override
    public Color getRed() {
        return Color.RED;
    }

    @Override
    public Color getYellow() {
        return Color.YELLOW;
    }

    @Override
    public Color getWhite() {
        return Color.WHITE;
    }
}
