package de.sl.model;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelBase implements Runnable {

    private final double speed;

    private Thread myThread;

    private boolean active;

    private final List<IModelListener> listeners = new ArrayList<>();

    private double startMillis;

    private boolean simStopped = false;
    private double pauseStartMillis;

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

    public void pause() {
        simStopped = true;
        pauseStartMillis = System.currentTimeMillis();
    }

    public boolean isPause() {
        return simStopped;
    }

    public void resume() {
        startMillis += (System.currentTimeMillis()-pauseStartMillis);
        simStopped = false;
    }

    @Override
    public void run() {
        while(active) {
            if(!simStopped) {
                simulate((long) ((System.currentTimeMillis() - startMillis) * speed));
            }

            try {
                myThread.sleep(100);
            } catch (InterruptedException ex) {
                // ignore
            }
        }
    }

    public void informListeners(Object modelObject) {
        for(IModelListener listener : listeners) {
            listener.handleModelUpdate(modelObject);
        }
    }

    abstract protected void simulate(long elapsedMillis);
}
