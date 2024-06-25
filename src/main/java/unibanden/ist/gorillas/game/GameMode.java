package unibanden.ist.gorillas.game;

import java.util.ArrayList;

/**
 * Responsible: William Lohmann s204469
 * A gamemode for the game who can start the gameloop, control the flow of the game, and spawn the players.
 */
public abstract class GameMode {
    protected ArrayList<PlayerMovement> players;
    private WinListener winListener;

    /**
     * Starts the game.
     * @param playerAmount The amount of players to spawn.
     */
    public void start(int playerAmount) {
        players = new ArrayList<PlayerMovement>();
        for (int i = 0; i < playerAmount; i++) {
            players.add(generatePlayer(i + 1, ((double)i + 0.5) * ((double)GameMaster.getGameWidth()) / playerAmount, GameMaster.getGameHeight()));
        }
    }

    /**
     * Creates a new player.
     * @param id The player id. All players have a unique id.
     * @param xPos x position to spawn the player at.
     * @param yPos y position to spawn the player at.
     * @return The newly generated player.
     */
    protected abstract PlayerMovement generatePlayer(int id, double xPos, double yPos);

    /**
     * Some step of the game has finished, such as a projectile landing.
     */
    public void registerStepDone(){}

    /**
     * Sets a player as dead and removes them from the game. Checks if someone won.
     * @param player The player who should die.
     */
    public void playerDead(PlayerMovement player) {
        players.remove(player);
        if (players.size() == 1){
            if (winListener != null){
                winListener.playerWon(players.get(0).getID());
            }
        }
    }

    /**
     * Sign up to be notified when a player wins.
     * @param winListener The object to be notified.
     */
    public void setWinListener(WinListener winListener){
        this.winListener = winListener;
    }
}
