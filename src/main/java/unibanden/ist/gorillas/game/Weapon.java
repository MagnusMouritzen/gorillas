package unibanden.ist.gorillas.game;

/**
 * Responsible: Magnus Mouritzen s214931
 * Used to fire projectiles.
 */
public abstract class Weapon {
    public PlayerMovement owner;

    /**
     * Fire a projectile.
     * @param xPos Global x position of projectile
     * @param yPos Global y position of projectile
     * @param xVel Start x velocity.
     * @param yVel Start y velocity.
     */
    public abstract void shoot(double xPos, double yPos, double xVel, double yVel);
}
