package unibanden.ist.gorillas.game;

import java.awt.Color;
import unibanden.ist.gameengine.Drawing;
import unibanden.ist.gameengine.GameObject;

import java.util.Random;

/**
 * Responsible: William Lohmann s204469
 * A gamemode with turn-based combat and wind.
 */
public abstract class GameModeBasic extends GameMode{
    protected int curPlayer;
    private final double pixelsPerWind = 2;

    /**
     * Creates a basic game with the given max wind speed.
     * @param maxWindSpeed The maximum speed the wind can reach.
     */
    public GameModeBasic(double maxWindSpeed){
        super();
        Random random = new Random();
        if (maxWindSpeed > 0){
            GameMaster.xAcc = random.nextDouble(maxWindSpeed * 2) - maxWindSpeed;
        }
    }

    @Override
    public void start(int playerAmount) {
        super.start(playerAmount);
        double absWind = Math.abs(GameMaster.xAcc);
        if (absWind * pixelsPerWind > 3){
            Color[][] windArrow = new Color[5][(int)(absWind * pixelsPerWind)];
            for (int x = 0; x < windArrow[0].length; x++) {
                windArrow[2][x] = Color.BLACK;
            }
            if (GameMaster.xAcc > 0){
                windArrow[0][windArrow[0].length - 3] = Color.BLACK;
                windArrow[4][windArrow[0].length - 3] = Color.BLACK;
                windArrow[1][windArrow[0].length - 2] = Color.BLACK;
                windArrow[3][windArrow[0].length - 2] = Color.BLACK;
            } else {
                windArrow[0][2] = Color.BLACK;
                windArrow[4][2] = Color.BLACK;
                windArrow[1][1] = Color.BLACK;
                windArrow[3][1] = Color.BLACK;
            }
            new GameObject(GameMaster.getGameWidth() / 2, 5,
                    new Drawing(windArrow, -windArrow[0].length / 2, 0));
        }
        curPlayer = 0;
        players.get(curPlayer).takeTurn();
    }

    @Override
    public void registerStepDone() {
        super.registerStepDone();
        if (players.size() == 1){
            return;
        }
        curPlayer = (curPlayer + 1) % players.size();
        players.get(curPlayer).takeTurn();
    }

    @Override
    public void playerDead(PlayerMovement player) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) == player){
                if (curPlayer >= i){
                    curPlayer--;
                }
                break;
            }
        }
        super.playerDead(player);
    }
}
