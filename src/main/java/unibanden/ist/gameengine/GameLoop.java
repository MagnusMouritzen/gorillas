package unibanden.ist.gameengine;

/**
 * Responsible: Magnus Mouritzen s214931
 * Responsible for calling Engine.update continually.
 */
public interface GameLoop {
    /**
     * Start calling Engine.update.
     */
    public void start();

    /**
     * Stop calling Engine.update.
     */
    public void stop();
}
