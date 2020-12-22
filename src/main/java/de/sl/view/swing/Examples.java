package de.sl.view.swing;

import de.sl.view.ExampleViewModel;
import de.sl.view.IViewModelListener;
import de.sl.view.IView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * @author SL
 */
public class Examples extends AppBase implements ActionListener, IViewModelListener<Color> {

    private static final String ARROW_LEFT = "\u2190";
    private static final String ARROW_RIGHT = "\u2192";
    private static final String ARROW_UP = "\u2191";
    private static final String ARROW_DOWN = "\u2193";

    private transient ExampleViewModel<Color, BufferedImage> viewModel;

    private JButton bGreater;
    private JButton bSmaller;
    private JButton bLeft;
    private JButton bRight;
    private JButton bUp;
    private JButton bDown;

    private Examples(ExampleViewModel<Color, BufferedImage> viewModel) {
        super("examples 1.0", 800, 600, viewModel);

        this.viewModel = viewModel;
        this.viewModel.addViewModelListener(this);
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
            viewModel.greater();
        } else if(e.getSource()==bSmaller) {
            viewModel.smaller();
        } else if(e.getSource()==bLeft) {
            viewModel.moveLeft();
        } else if(e.getSource()==bRight) {
            viewModel.moveRight();
        } else if(e.getSource()==bUp) {
            viewModel.moveUp();
        } else if(e.getSource()==bDown) {
            viewModel.moveDown();
        }
    }

    public static void main(String[] args) {
        final ExampleViewModel<Color, BufferedImage> viewModel = new ExampleViewModel<>(new SwingColorFactory(), new SwingImageFactory());

        EventQueue.invokeLater(() -> {
            Examples app = new Examples(viewModel);
            app.setVisible(true);
            app.start();
        });
    }
}
