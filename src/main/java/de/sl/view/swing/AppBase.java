package de.sl.view.swing;

import de.sl.view.ViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author SL
 */
public abstract class AppBase extends JFrame implements Runnable {

    private SwingPanel board;

    private transient Thread myThread;

    private boolean active = true;

    protected AppBase(String title, int width, int height, ViewModel<Color, BufferedImage> viewModel) {
        setSize(width, height);
        setTitle(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(5, 5));

        board = new SwingPanel(viewModel);
        add(board, BorderLayout.CENTER);

        initUI();
    }

    protected abstract void initUI();

    @Override
    public void run() {
        while(active) {
            board.refresh();

            try {
                myThread.sleep(1000 / 30);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    protected void start() {
        myThread = new Thread(this);
        myThread.setPriority(Thread.MIN_PRIORITY);
        myThread.start();
    }
}
