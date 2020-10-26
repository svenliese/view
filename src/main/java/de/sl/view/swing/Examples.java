package de.sl.view.swing;

import de.sl.model.ExampleModel;
import de.sl.model.IModelListener;
import de.sl.view.IView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * @author SL
 */
public class Examples extends JFrame implements Runnable, ActionListener, IModelListener<Color> {

    private static final String ARROW_LEFT = "\u2190";
    private static final String ARROW_RIGHT = "\u2192";
    private static final String ARROW_UP = "\u2191";
    private static final String ARROW_DOWN = "\u2193";

    private transient ExampleModel<Color, BufferedImage> model;

    private SwingPanel board;

    private transient Thread myThread;

    private boolean simulate = true;

    private JButton bGreater;
    private JButton bSmaller;
    private JButton bLeft;
    private JButton bRight;
    private JButton bUp;
    private JButton bDown;

    private Examples() {

        model = new ExampleModel<>(new SwingColorFactory(), new SwingImageFactory());
        model.setModelListener(this);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(5, 5));

        board = new SwingPanel(model);
        add(board, BorderLayout.CENTER);

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

        setSize(800, 600);
        setTitle("examples 1.0");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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
    public void clickedOnView(IView<Color> view) {
        changeInputState(true);
    }

    @Override
    public void clickedInBackground() {
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

    @Override
    public void run() {
        while(simulate) {
            board.simulate();

            try {
                myThread.sleep(1000 / 30);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    void start() {
        myThread = new Thread(this);
        myThread.setPriority(Thread.MIN_PRIORITY);
        myThread.start();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Examples app = new Examples();
            app.setVisible(true);
            app.start();
        });
    }
}
