package unibanden.ist.gorillas.game;

import java.awt.Color;
import unibanden.ist.gameengine.Drawing;
import unibanden.ist.gameengine.GameObject;

/**
 * All available Pickups as well as functionality for creating them.
 */
public enum PickupType {
    Airstrike,
    Barrier,
    Nuke,
    Shotgun,
    Heart;

    /**
     * Create a GameObject with a Pickup of the chosen type.
     * @param type The type of Pickup to create.
     * @param xPos x position in world coordinates.
     * @param yPos y position in world coordinates.
     * @return The GameObject.
     */
    public static GameObject instantiatePickup(PickupType type, double xPos, double yPos){
        switch (type){
            case Airstrike -> {
                return Pickup.getPrefab(xPos, yPos, new PickupAirstrike(), new Drawing(PickupType.class.getResourceAsStream("/unibanden/ist/gorillas/game/PickupAirstrike.vis"), 0, 0));
            }
            case Barrier -> {
                return Pickup.getPrefab(xPos, yPos, new PickupWeapon(new WeaponBarrier()), new Drawing(PickupType.class.getResourceAsStream("/unibanden/ist/gorillas/game/PickupBarrier.vis"), 0, 0));
            }
            case Nuke -> {
                return Pickup.getPrefab(xPos, yPos, new PickupWeapon(new WeaponNuke()), new Drawing(PickupType.class.getResourceAsStream("/unibanden/ist/gorillas/game/PickupNuke.vis"), 0, 0));
            }
            case Shotgun -> {
                return Pickup.getPrefab(xPos, yPos, new PickupWeapon(new WeaponShotgun()), new Drawing(PickupType.class.getResourceAsStream("/unibanden/ist/gorillas/game/PickupShotgun.vis"), 0, 0));
            }
            case Heart -> {
                return Pickup.getPrefab(xPos, yPos, new PickupHeart(), new Drawing(PickupType.class.getResourceAsStream("/unibanden/ist/gorillas/game/PickupHeart.vis"), 0, 0));
            }
            default -> {
                return Pickup.getPrefab(xPos, yPos, new Pickup(), new Drawing(new Color[][]{{null}}, 0, 0));
            }
        }
    }
}
