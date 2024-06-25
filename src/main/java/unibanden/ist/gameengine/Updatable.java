package unibanden.ist.gameengine;

/**
 * Responsible: Magnus Mouritzen s214931
 * Classes that implement this can sign up to the GameLoop. Primarily used by GameObject, which notifies its Components.
 */
public interface Updatable {
    /**
     * Called in each iteration of the GameLoop with the purpose of enabling continuous processes. Essentially frames.
     * @param deltaTime The time in seconds since the last update call.
     */
    public void update(double deltaTime);
}
