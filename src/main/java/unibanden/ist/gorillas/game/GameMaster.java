package unibanden.ist.gorillas.game;

/**
 * Responsible: Magnus Mouritzen s214931
 * Manages the game itself.
 */
public class GameMaster {
    // The GameMode currently being played.
    private static GameMode gameMode;
    public static GameMode getGameMode() { return gameMode; }

    // Global world acceleration.
    public static double xAcc = 0;
    public static double yAcc = -9.82;

    private static int gameWidth = 480;
    public static int getGameWidth() { return gameWidth; }
    private static int gameHeight = 360;
    public static int getGameHeight() { return gameHeight; }


    /**
     * Start the game.
     * @param map The map to play on.
     * @param gameMode The GameMode to play.
     * @param playerAmount The amount of players to have in the game.
     * @param gameWidth The width of the map and screen.
     * @param gameHeight The height of the map and screen.
     */
    public static void start(Map map, GameMode gameMode, int playerAmount, int gameWidth, int gameHeight) {
        GameMaster.gameWidth = gameWidth;
        GameMaster.gameHeight = gameHeight;
        map.generateLevel(gameHeight, gameWidth);
        GameMaster.gameMode = gameMode;
        gameMode.start(playerAmount);
    }
}
