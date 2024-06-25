package unibanden.ist.gorillas.game;

import unibanden.ist.gameengine.Component;

/**
 * Responsible: Magnus Mouritzen s214931
 * Makes the GameObject disappear a given amount of seconds after being created.
 * Used for short-lived effects.
 */
public class SelfDestruct extends Component {
    private double timeLeft;

    /**
     * Set up SelfDestruct.
     * @param timeToLive Amount of seconds after activation to self destroy.
     */
    public SelfDestruct(double timeToLive){
        timeLeft = timeToLive;
    }

    @Override
    public void update(double deltaTime) {
        timeLeft -= deltaTime;
        if (timeLeft <= 0.0){
            getGameObject().queryDestroy();
        }
    }
}
