package de.sl.model;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelBase implements Runnable {

    private final long millisPerStep;

    private Thread myThread;

    private boolean active;

    private final List<IModelListener> listeners = new ArrayList<>();

    private long steps;

    private boolean simStopped = false;

    protected ModelBase(long millisPerStep) {
        this.millisPerStep = millisPerStep;
    }

    public void addListener(IModelListener listener) {
        listeners.add(listener);
    }

    public void start() {
        if(getSmallestMillis() < millisPerStep) {
            throw new IllegalStateException("illegal speed for model");
        }
        steps = 0;
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
    }

    public boolean isPause() {
        return simStopped;
    }

    public void resume() {
        simStopped = false;
    }

    public void join() {
        try {
            myThread.join();
        } catch (InterruptedException ex) {
            // ignore
        }
    }

    @Override
    public void run() {
        while(active) {

            if(!simStopped) {
                active = simulate(steps*millisPerStep);
                steps++;
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

    abstract public long getSmallestMillis();

    /**
     * @return false in case of simulation done
     */
    abstract protected boolean simulate(long elapsedMillis);
}
