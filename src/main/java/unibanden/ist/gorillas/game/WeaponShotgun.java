package unibanden.ist.gorillas.game;

import java.util.Random;

/**
 * Responsible: Magnus Mouritzen s214931
 * Fires multiple shots in different directions.
 */
public class WeaponShotgun extends WeaponSpecial{
    @Override
    protected void fire(double xPos, double yPos, double xVel, double yVel) {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            // Not very sophisticated scattering of shots, but it will do.
            Projectile.getPrefab(xPos, yPos, xVel + random.nextDouble(50) - 25, yVel + random.nextDouble(90) - 45, 15, 1, owner, i == 0);
        }
    }
}
