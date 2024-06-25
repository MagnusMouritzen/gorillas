package unibanden.ist.gorillas.game;

/**
 * Responsible: Magnus Mouritzen s214931
 * The standard weapon that players start with.
 */
public class WeaponStandard extends Weapon{
    @Override
    public void shoot(double xPos, double yPos, double xVel, double yVel) {
        Projectile.getPrefab(xPos, yPos, xVel, yVel, 20, 1, owner, true);
    }
}
