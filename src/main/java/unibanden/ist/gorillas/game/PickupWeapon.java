package unibanden.ist.gorillas.game;

/**
 * Responsible: Magnus Mouritzen s214931
 * Gives the activating player a new Weapon.
 */
public class PickupWeapon extends Pickup{
    private Weapon weapon;

    public PickupWeapon(Weapon weapon){
        this.weapon = weapon;
    }

    @Override
    protected void trigger(PlayerMovement source) {
        super.trigger(source);
        source.attachWeapon(weapon);
    }
}
