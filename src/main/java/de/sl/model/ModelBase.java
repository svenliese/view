package de.sl.model;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelBase implements Runnable {

    private final double speed;

    private Thread myThread;

    private boolean active;

    private final List<IModelListener> listeners = new ArrayList<>();

    private long minMillis;

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
        minMillis = getSmallestMillis();
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

    public void join() {
        try {
            myThread.join();
        } catch (InterruptedException ex) {
            // ignore
        }
    }

    @Override
    public void run() {
        long lastELapsed = -1;

        while(active) {

            if(!simStopped) {
                long elapsed = (long)((System.currentTimeMillis() - startMillis) * speed);
                if(lastELapsed>0 && elapsed - lastELapsed > minMillis) {
                    throw new IllegalStateException("simulation to slow for selected speed");
                }
                lastELapsed = elapsed;
                active = simulate(elapsed);
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
