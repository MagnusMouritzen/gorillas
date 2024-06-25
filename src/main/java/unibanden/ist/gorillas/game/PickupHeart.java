package unibanden.ist.gorillas.game;

/**
 * Responsible: Magnus Mouritzen s214931
 * Heals the player by 1.
 */
public class PickupHeart extends Pickup{
    @Override
    protected void trigger(PlayerMovement source) {
        super.trigger(source);
        source.getGameObject().getComponent(PlayerMovement.class).heal(1);
    }
}
