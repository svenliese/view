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

    private ExampleModel model;

    private SwingPanel board;

    private transient Thread myThread;

    private boolean simulate = true;

    private JButton bGreater;
    private JButton bSmaller;

    private Examples() {

        model = buildModel();

        initUI();
    }

    private ExampleModel buildModel() {
        final Rect<Color> rect = new Rect<>(Color.WHITE);

        rect.setXPercentage(0.25f);
        rect.setYPercentage(0.25f);
        rect.setWPercentage(0.5f);
        rect.setHPercentage(0.5f);

        return new ExampleModel(rect);
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

        setSize(800, 600);
        setTitle("examples 1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==bGreater) {
            bGreater.setEnabled(model.greater());
            bSmaller.setEnabled(true);
        } else if(e.getSource()==bSmaller) {
            bSmaller.setEnabled(model.smaller());
            bGreater.setEnabled(true);
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
