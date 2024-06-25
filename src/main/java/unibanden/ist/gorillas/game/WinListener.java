package unibanden.ist.gorillas.game;

/**
 * Responsible: Magnus Mouritzen s214931
 * Implementing this lets an object ask a GameMode to notify it when someone wins.
 */
public interface WinListener {
    /**
     * A player has won the game.
     * @param id The id of the player who won.
     */
    public void playerWon(int id);
}
