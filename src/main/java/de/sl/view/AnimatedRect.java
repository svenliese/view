package de.sl.view;

/**
 * @author SL
 */
public class AnimatedRect<C> extends Rect<C> {

    private float animationSpeed;
    private long firstCall = -1;
    private float animationPercentage = 0;

    public AnimatedRect(C color, float animationSpeed) {
        super(color);
        this.animationSpeed = animationSpeed;
    }

    public boolean finishedAnimation() {
        return animationPercentage>=super.hPercentage;
    }

    public void finish() {
        firstCall = 0;
    }

    public void resetAnimation() {
        firstCall = -1;
        animationPercentage = 0;
    }

    @Override
    public boolean simulate(long now) {
        super.simulate(now);

        if(firstCall==-1) {
            firstCall = now;
        } else {
            float duration = (now-firstCall)/1000f;
            animationPercentage = duration*animationSpeed*super.hPercentage;
        }

        return true;
    }

    @Override
    public float getHPercentage() {
        if(finishedAnimation()) {
            return super.getHPercentage();
        }
        return animationPercentage;
    }
}
