package unibanden.ist.gorillas.game;

import java.util.Random;

/**
 * Responsible: William Lohmann s204469
 * The Modern GameMode. Like the Classic, but with pick-ups and jumping.
 */
public class GameModeModern extends GameModeBasic {
    private int nextSpawn;
    private Random random;

    private PickupType[] allowedPickups;

    /**
     * Creates a game of the type Modern.
     * @param maxWindSpeed The maximum speed the wind can reach.
     * @param allowedPickups A list of all the pick-ups that should be able to spawn.
     */
    public GameModeModern(double maxWindSpeed, PickupType[] allowedPickups){
        super(maxWindSpeed);
        this.allowedPickups = allowedPickups;
    }

    @Override
    public void start(int playerAmount) {
        super.start(playerAmount);
        random = new Random();
    }

    @Override
    protected PlayerMovement generatePlayer(int id, double xPos, double yPos) {
        return PlayerMovement.getPrefab(xPos, yPos, id, new PlayerMovementModern(id, 3)).getComponent(PlayerMovement.class);
    }

    @Override
    public void registerStepDone() {
        if (curPlayer == 0){
            nextSpawn = random.nextInt(players.size());
        }
        if (curPlayer == nextSpawn){
            PickupType.instantiatePickup(allowedPickups[random.nextInt(allowedPickups.length)], random.nextDouble(400), random.nextDouble(300));
        }
        super.registerStepDone();
    }
}
