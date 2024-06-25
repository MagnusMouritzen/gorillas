package unibanden.ist.gorillas.game;

/**
 * Responsible: Magnus Mouritzen s214931
 * Template for a special weapon that will override a player's standard weapon for one turn.
 */
public abstract class WeaponSpecial extends Weapon{
    @Override
    public void shoot(double xPos, double yPos, double xVel, double yVel){
        // Removes itself after firing once.
        fire(xPos, yPos, xVel, yVel);
        owner.detachWeapon();
    }

    protected abstract void fire(double xPos, double yPos, double xVel, double yVel);
}
