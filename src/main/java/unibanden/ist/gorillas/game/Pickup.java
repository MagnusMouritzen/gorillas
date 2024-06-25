package unibanden.ist.gorillas.game;

import unibanden.ist.gameengine.Collidable;
import unibanden.ist.gameengine.Component;
import unibanden.ist.gameengine.Drawing;
import unibanden.ist.gameengine.GameObject;

/**
 * Responsible: Magnus Mouritzen s214931
 * An item on the map whose effect can be activated by a player or projectile colliding with it.
 * Depends on: Collidable.
 */
public class Pickup extends Component {
    private Collidable collidable;
    private boolean hasTriggered;

    @Override
    public void activate(GameObject gameObject) {
        super.activate(gameObject);
        hasTriggered = false;
        collidable = getGameObject().getDependency(Collidable.class, getClass());
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        if (hasTriggered){
            return;
        }
        // Check if a player or projectile has entered its collidable.
        for (Collidable col : collidable.checkCollisions()){
            Projectile proj = col.getGameObject().getComponent(Projectile.class);
            if (proj != null){
                trigger(proj.source);
            }
            else{
                PlayerMovement player = col.getGameObject().getComponent(PlayerMovement.class);
                if (player != null){
                    trigger(player);
                }
            }
        }
    }

    /**
     * Something has entered the Pickup. Override this to determine effect.
     * @param source The player who triggered it.
     */
    protected void trigger(PlayerMovement source){
        hasTriggered = true;
        getGameObject().queryDestroy();
    }

    /**
     * Create a GameObject with the required Components to have a functional pickup.
     * @param xPos The x position in world coordinates.
     * @param yPos The y position in world coordinates.
     * @param pickup The actual Pickup Component.
     * @param drawing The Drawing to attach.
     * @return The GameObject.
     */
    public static GameObject getPrefab(double xPos, double yPos, Pickup pickup, Drawing drawing){
        int[][] structure = new int[20][20];
        for (int y = 0; y < 18; y++) {
            for (int x = 0; x < 18; x++) {
                structure[y][x] = Layer.Trigger.ordinal();
            }
        }
        return new GameObject(xPos, yPos,
                new Collidable(structure, 0, 0, Layer.generateLayerMask(Layer.Player, Layer.Projectile), Layer.generateLayerMask(Layer.Trigger, Layer.Nothing)),
                drawing, pickup);
    }
}
