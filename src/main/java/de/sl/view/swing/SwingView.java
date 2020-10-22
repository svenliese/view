package de.sl.view.swing;

import de.sl.view.Animation;
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
    public void setAnimation(Animation animation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getUserObject() {
        return view.getUserObject();
    }

    @Override
    public void setUserObject(Object userObject) {
        view.setUserObject(userObject);
    }

    protected String trim(String origin, Graphics2D g2d, double width) {
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

    protected void drawText(Graphics2D g2d, double x, double y, double w, double h, String text, int orientation) {

        final AffineTransform originTransform = g2d.getTransform();

        final FontMetrics fm = g2d.getFontMetrics();

        final double textX;
        final double textY;

        if(orientation == SimpleText.VERTICAL) {
            final double xm = x + w/2;
            final double ym = y + h/2;

            final double nx = xm - h/2;
            final double ny = ym - w/2;
            final double nw = h;
            final double nh = w;

            text = trim(text, g2d, nw);

            final Rectangle2D textBounds = fm.getStringBounds(text, g2d);
            textX = nx;
            textY = (ny + (nh - textBounds.getHeight()) / 2.0d) + fm.getAscent();

            AffineTransform at = new AffineTransform();
            at.setToRotation(Math.toRadians(90), xm, ym);
            g2d.setTransform(at);

        } else {
            final Rectangle2D textBounds = fm.getStringBounds(text, g2d);
            textX = (x + (w - textBounds.getWidth()) / 2.0d);
            textY = (y + (h - textBounds.getHeight()) / 2.0d) + fm.getAscent();
        }

        g2d.drawString(text, (int)textX, (int)textY);

        g2d.setTransform(originTransform);
    }

    public abstract void draw(Graphics2D g2d, double width, double height);
}
