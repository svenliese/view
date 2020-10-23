package de.sl.view.swing.example;

import de.sl.view.Rect;
import de.sl.view.swing.SwingPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author SL
 */
public class Examples extends JFrame implements Runnable, ActionListener {

    private static final String ARROW_LEFT = "\u2190";
    private static final String ARROW_RIGHT = "\u2192";
    private static final String ARROW_UP = "\u2191";
    private static final String ARROW_DOWN = "\u2193";

    private ExampleModel model;

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

        model = buildModel();

        initUI();
    }

    private ExampleModel buildModel() {
        final ExampleModel model = new ExampleModel();

        Rect<Color> rect = new Rect<>(Color.WHITE);
        rect.setXPercentage(0.1f);
        rect.setYPercentage(0.1f);
        rect.setWPercentage(0.25f);
        rect.setHPercentage(0.25f);
        model.addView(rect);

        rect = new Rect<>(Color.YELLOW);
        rect.setXPercentage(0.2f);
        rect.setYPercentage(0.2f);
        rect.setWPercentage(0.25f);
        rect.setHPercentage(0.25f);
        model.addView(rect);

        return model;
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
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
