package unibanden.ist.gorillas.game;

import unibanden.ist.gameengine.Engine;
import unibanden.ist.gameengine.Updatable;

import java.util.Random;

/**
 * Responsible: William Lohmann s204469
 * The hyper intensive Rumble GameMode. Players move freely all the time, no turn-based combat. But LOADS of pick-ups.
 */
public class GameModeRumble extends GameMode implements Updatable {
    private Random random = new Random();
    private final double maxBuffer = 20;
    private double randomBuffer;

    private PickupType[] allowedPickups;

    /**
     * Creates a game of the type Rumble.
     * @param allowedPickups A list of all the pick-ups that should be able to spawn.
     */
    public GameModeRumble(PickupType[] allowedPickups){
        super();
        this.allowedPickups = allowedPickups;
    }

    @Override
    public void start(int playerAmount) {
        Engine.addUpdateListener(this);
        super.start(playerAmount);
        // Makes sure all the players can move
        for (PlayerMovement player : players) {
            player.takeTurn();
        }

        randomBuffer = random.nextDouble(maxBuffer);
    }

    @Override
    protected PlayerMovement generatePlayer(int id, double xPos, double yPos) {
        return PlayerMovement.getPrefab(xPos, yPos, id, new PlayerMovementRumble(id, 5)).getComponent(PlayerMovement.class);
    }

    public void update(double deltaTime) {
        randomBuffer -= deltaTime;

        if (randomBuffer <= 0){
            PickupType.instantiatePickup(allowedPickups[random.nextInt(allowedPickups.length)], random.nextDouble(400), random.nextDouble(300));
            randomBuffer = random.nextDouble(maxBuffer);
        }
    }
}
