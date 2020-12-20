package de.sl.view.swing;

import de.sl.view.ExampleModel;
import de.sl.view.IModelListener;
import de.sl.view.IView;
import de.sl.view.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * @author SL
 */
public class Examples extends AppBase implements ActionListener, IModelListener<Color> {

    private static final String ARROW_LEFT = "\u2190";
    private static final String ARROW_RIGHT = "\u2192";
    private static final String ARROW_UP = "\u2191";
    private static final String ARROW_DOWN = "\u2193";

    private transient ExampleModel<Color, BufferedImage> model;

    private JButton bGreater;
    private JButton bSmaller;
    private JButton bLeft;
    private JButton bRight;
    private JButton bUp;
    private JButton bDown;

    private Examples() {
        super("examples 1.0", 800, 600);
    }

    @Override
    protected Model<Color, BufferedImage> initModel() {
        model = new ExampleModel<>(new SwingColorFactory(), new SwingImageFactory());
        model.addModelListener(this);
        return model;
    }

    @Override
    protected void initUI() {

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        add(buttonPanel, BorderLayout.NORTH);

        bGreater = new JButton("+");
        bGreater.addActionListener(this);
        buttonPanel.add(bGreater);

        bSmaller = new JButton("-");
        bSmaller.addActionListener(this);
        buttonPanel.add(bSmaller);

        bLeft = new JButton(ARROW_LEFT);
        bLeft.addActionListener(this);
        buttonPanel.add(bLeft);

        bRight = new JButton(ARROW_RIGHT);
        bRight.addActionListener(this);
        buttonPanel.add(bRight);

        bUp = new JButton(ARROW_UP);
        bUp.addActionListener(this);
        buttonPanel.add(bUp);

        bDown = new JButton(ARROW_DOWN);
        bDown.addActionListener(this);
        buttonPanel.add(bDown);

        changeInputState(false);
    }

    private void changeInputState(boolean enabled) {
        bGreater.setEnabled(enabled);
        bSmaller.setEnabled(enabled);
        bLeft.setEnabled(enabled);
        bRight.setEnabled(enabled);
        bUp.setEnabled(enabled);
        bDown.setEnabled(enabled);
    }

    @Override
    public void touchedOnViews(java.util.List<IView<Color>> iViews) {
        changeInputState(true);
    }

    @Override
    public void touchedInBackground() {
        changeInputState(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==bGreater) {
            model.greater();
        } else if(e.getSource()==bSmaller) {
            model.smaller();
        } else if(e.getSource()==bLeft) {
            model.moveLeft();
        } else if(e.getSource()==bRight) {
            model.moveRight();
        } else if(e.getSource()==bUp) {
            model.moveUp();
        } else if(e.getSource()==bDown) {
            model.moveDown();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Examples app = new Examples();
            app.setVisible(true);
            app.start();
        });
    }
}
