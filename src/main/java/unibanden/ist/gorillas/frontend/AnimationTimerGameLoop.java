package unibanden.ist.gorillas.frontend;

import javafx.animation.AnimationTimer;
import unibanden.ist.gameengine.Engine;
import unibanden.ist.gameengine.GameLoop;

/**
 * Responsible: Magnus Mouritzen s214931
 * Handles calling Engine.update with an AnimationTimer which attempts to run 60 times a second and updates the UI after each iteration.
 */
public class AnimationTimerGameLoop implements GameLoop {
    private final AnimationTimer at;

    public AnimationTimerGameLoop(){
        at = new AnimationTimer() {
            long prevTime = 0;

            @Override
            public void handle(long now) {
                // now is the current frame's timestamp in nanoseconds.
                long deltaTime = 0;
                if (prevTime != 0){
                    deltaTime = now - prevTime;
                }
                prevTime = now;
                Engine.invokeUpdate(((double)deltaTime) / 1000000000.0);
            }
        };
    }

    @Override
    public void start() {
        at.start();
    }

    @Override
    public void stop() {
        at.stop();
    }
}
