package de.sl.model;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelBase implements Runnable {

    private final double speed;

    private Thread myThread;

    private boolean active;

    private final List<IModelListener> listeners = new ArrayList<>();

    private double startMillis;

    /**
     * @param speed the real time will be multiplied with the given speed
     */
    protected ModelBase(double speed) {
        this.speed = speed;
    }

    public void addListener(IModelListener listener) {
        listeners.add(listener);
    }

    public void start() {
        startMillis = System.currentTimeMillis();
        active = true;
        myThread = new Thread(this);
        myThread.setPriority(Thread.MIN_PRIORITY);
        myThread.start();
    }

    public void stop() {
        active = false;
    }

    @Override
    public void run() {
        while(active) {
            if(simulate( (long)((System.currentTimeMillis()-startMillis)*speed )) ) {
                for(IModelListener listener : listeners) {
                    listener.handleModelUpdate();
                }
            }

            try {
                myThread.sleep(100);
            } catch (InterruptedException ex) {
                // ignore
            }
        }
    }

    /**
     * @return true if model changed
     */
    abstract protected boolean simulate(long elapsedMillis);
}
