package de.sl.view.swing;

import de.sl.view.IView;
import de.sl.view.SimpleText;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * @author SL
 */
public abstract class SwingView implements IView<Color> {

    private final IView<Color> view;

    protected SwingView(IView<Color> view) {
        this.view = view;
    }

    @Override
    public float getXPercentage() {
        return view.getXPercentage();
    }

    @Override
    public void setXPercentage(float xPercentage) {
        view.setXPercentage(xPercentage);
    }

    @Override
    public float getYPercentage() {
        return view.getYPercentage();
    }

    @Override
    public void setYPercentage(float yPercentage) {
        view.setYPercentage(yPercentage);
    }

    @Override
    public float getWPercentage() {
        return view.getWPercentage();
    }

    @Override
    public void setWPercentage(float wPercentage) {
        view.setWPercentage(wPercentage);
    }

    @Override
    public float getHPercentage() {
        return view.getHPercentage();
    }

    @Override
    public void setHPercentage(float hPercentage) {
        view.setHPercentage(hPercentage);
    }

    @Override
    public boolean isInside(float xPercentage, float yPercentage) {
        return
            xPercentage>=view.getXPercentage() && xPercentage<=view.getXPercentage()+view.getWPercentage() &&
            yPercentage>=view.getYPercentage() && yPercentage<=view.getYPercentage()+view.getHPercentage();
    }

    @Override
    public boolean isVisible() {
        return view.isVisible();
    }

    @Override
    public void setVisible(boolean visible) {
        view.setVisible(visible);
    }

    @Override
    public boolean simulate(long now) {
        return false;
    }

    @Override
    public Object getUserObject() {
        return view.getUserObject();
    }

    @Override
    public void setUserObject(Object userObject) {
        view.setUserObject(userObject);
    }

    protected static String trim(String origin, Graphics2D g2d, double width) {
        final FontMetrics fm = g2d.getFontMetrics();

        String newText = origin;

        Rectangle2D textBounds = fm.getStringBounds(newText+"XXX", g2d);
        while(textBounds.getWidth()>=width) {
            newText = newText.substring(0, newText.length()-1);
            textBounds = fm.getStringBounds(newText+"XXX", g2d);
        }

        if(!newText.equals(origin)) {
            newText += "..";
        }
        return newText;
    }

    protected static double getTextStart(double originStart, double textWidth, double bounds, int alignment) {
        if(alignment==SimpleText.LEFT) {
            return originStart;
        }
        return (originStart + (bounds - textWidth) / 2.0d);
    }

    protected static void drawText(Graphics2D g2d, SimpleText<Color> data, double width, double height) {

        final AffineTransform originTransform = g2d.getTransform();

        g2d.setFont(new Font(g2d.getFont().getFontName(), Font.PLAIN, data.getTextSize()));
        final FontMetrics fm = g2d.getFontMetrics();

        final double x = data.getXPercentage() * width;
        final double y = data.getYPercentage() * height;
        final double w = data.getWPercentage() * width;
        final double h = data.getHPercentage() * height;

        final double textX;
        final double textY;

        String text = data.getText();

        if(data.getOrientation() == SimpleText.VERTICAL) {
            final double xm = x + w/2;
            final double ym = y + h/2;

            final double nx = xm - h/2;
            final double ny = ym - w/2;
            final double nw = h;
            final double nh = w;

            text = trim(text, g2d, nw);

            final Rectangle2D textBounds = fm.getStringBounds(text, g2d);
            textX = getTextStart(nx, textBounds.getWidth(), nw, data.getHAlign());
            textY = (ny + (nh - textBounds.getHeight()) / 2.0d) + fm.getAscent();

            AffineTransform at = new AffineTransform();
            at.setToRotation(Math.toRadians(90), xm, ym);
            g2d.setTransform(at);

        } else {
            final Rectangle2D textBounds = fm.getStringBounds(text, g2d);
            textX = getTextStart(x, textBounds.getWidth(), w, data.getHAlign());
            textY = (y + (h - textBounds.getHeight()) / 2.0d) + fm.getAscent();
        }

        g2d.setColor(data.getTextColor());
        g2d.drawString(text, (int)textX, (int)textY);

        g2d.setTransform(originTransform);
    }

    public abstract void draw(Graphics2D g2d, double width, double height);
}
