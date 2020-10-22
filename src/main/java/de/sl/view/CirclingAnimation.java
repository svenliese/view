package de.sl.view;

/**
 * @author SL
 */
public class CirclingAnimation<C> extends Animation<C> {

    private float startArc;

    public CirclingAnimation(Arc<C> entity, long animationDuration, long amountOfSteps) {
        super(entity, animationDuration, amountOfSteps);

        this.startArc = entity.getStartArc();
    }

    public float getStartArc() {
        if(currentStep>0) {
            return startArc + 360f * (float)currentStep / (float)amountOfSteps;
        }
        return startArc;
    }
}
