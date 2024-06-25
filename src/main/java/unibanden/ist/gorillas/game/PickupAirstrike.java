package unibanden.ist.gorillas.game;

import java.util.Random;

/**
 * Responsible: Magnus Mouritzen s214931
 * Makes a number of projectiles fall from the sky when triggered.
 */
public class PickupAirstrike extends Pickup{
    @Override
    protected void trigger(PlayerMovement source) {
        super.trigger(source);
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            Projectile.getPrefab(random.nextDouble(GameMaster.getGameWidth()), GameMaster.getGameHeight() + 30, 0, 0, random.nextInt(36) + 4, 1, source, false);
        }
    }
}
