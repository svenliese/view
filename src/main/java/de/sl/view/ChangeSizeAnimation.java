package de.sl.view;

/**
 * @author SL
 */
public class ChangeSizeAnimation<C> extends Animation<C> {

    private final float maxPercentage;

    public ChangeSizeAnimation(IView<C> entity, long animationDuration, long amountOfSteps, float maxPercentage) {
        super(entity, animationDuration, amountOfSteps);
        this.maxPercentage = maxPercentage;
    }

    private float calculateMove(float value) {
        return value * (maxPercentage/(float)amountOfSteps * (float)currentStep);
    }

    @Override
    public float getXPercentage() {
        if(currentStep>0) {
            return xF - calculateMove(wF);
        }
        return xF;
    }

    @Override
    public float getYPercentage() {
        if(currentStep>0) {
            return yF - calculateMove(hF);
        }
        return yF;
    }

    @Override
    public float getWPercentage() {
        if(currentStep>0) {
            return wF + 2 * calculateMove(wF);
        }
        return wF;
    }

    @Override
    public float getHPercentage() {
        if(currentStep>0) {
            return hF + 2 * calculateMove(hF);
        }
        return hF;
    }
}
