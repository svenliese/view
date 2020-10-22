package de.sl.view;

/**
 * @author SL
 */
public abstract class Animation<C> {
    protected final float xF;
    protected final float yF;
    protected final float wF;
    protected final float hF;
    private final long animationDuration;
    protected final long amountOfSteps;

    private long startTime = -1;
    protected long currentStep = 0;

    /**
     * @param animationDuration time in millis of whole animation
     * @param amountOfSteps number of steps the whole animation consist of
     */
    Animation(IView<C> entity, long animationDuration, long amountOfSteps) {
        this.xF = entity.getXPercentage();
        this.yF = entity.getYPercentage();
        this.wF = entity.getWPercentage();
        this.hF = entity.getHPercentage();
        this.animationDuration = animationDuration;
        this.amountOfSteps = amountOfSteps;
    }

    /**
     * @param now current time millis
     */
    boolean simulate(long now) {
        if(startTime==-1) {
            startTime = now;
        }
        long mod = (now-startTime);
        if(mod>animationDuration) {
            mod = mod%animationDuration;
        }
        long step = amountOfSteps * mod / animationDuration;
        boolean modified = step!=currentStep;
        currentStep = step;
        return modified;
    }

    public float getXPercentage() {
        return xF;
    }

    public float getYPercentage() {
        return yF;
    }

    public float getWPercentage() {
        return wF;
    }

    public float getHPercentage() {
        return hF;
    }
}
