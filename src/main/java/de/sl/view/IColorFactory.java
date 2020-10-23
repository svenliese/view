package de.sl.view;

/**
 * @author SL
 */
public interface IColorFactory<C> {

    C[] getColorArray(int size);

    C getBlack();

    C getGray();

    C getLightGray();

    C getGreen();

    C getBlue();

    C getRed();

    C getYellow();

    C getWhite();

    C getColor(int red, int green, int blue);
}
