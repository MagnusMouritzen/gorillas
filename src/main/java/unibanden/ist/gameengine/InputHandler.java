package unibanden.ist.gameengine;

/**
 * Responsible: Magnus Mouritzen s214931
 * Handles reading input into the game.
 */
public interface InputHandler {
    /**
     * Check if the given input currently is being given.
     * Inputs can be divided into groups by the use of the two parameters.
     * @param i Index of the group of input.
     * @param j Index of the input in the given group.
     * @return true if the input currently is being fired.
     */
    public boolean getInput(int i, int j);
}
