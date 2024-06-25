package unibanden.ist.gorillas.game;

/**
 * Responsible: Magnus Mouritzen s214931
 * Huge explosionRadius.
 */
public class WeaponNuke extends WeaponSpecial{
    @Override
    protected void fire(double xPos, double yPos, double xVel, double yVel) {
        Projectile.getPrefab(xPos, yPos, xVel, yVel, 100, 2, owner, true);
    }
}
