package unibanden.ist.gorillas.game;

/**
 * Responsible: William Lohmann s204469
 * The Classic GameMode.
 */
public class GameModeClassic extends GameModeBasic {
    public GameModeClassic(double maxWindSpeed) {
        super(maxWindSpeed);
    }

    @Override
    protected PlayerMovement generatePlayer(int id, double xPos, double yPos) {
        return PlayerMovement.getPrefab(xPos, yPos, id, new PlayerMovementClassic(id, 1)).getComponent(PlayerMovement.class);
    }
}
