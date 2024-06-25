package unibanden.ist.gorillas.game;

import java.awt.Color;
import unibanden.ist.gameengine.*;
import unibanden.ist.gorillas.game.*;

/**
 * Responsible: Magnus Mouritzen s214931
 * A projectile bound by the laws of physics, which explodes on impact, damaging players and environment.
 * Depends on: Collidable.
 */
public class Projectile extends Component {
    private double xVel;
    private double yVel;
    private int explosionRadius;
    private int damage;
    private Collidable collidable;
    public PlayerMovement source;
    private boolean primary;

    /**
     * Set up projectile.
     * @param xVel Initial x velocity.
     * @param yVel Initial y velocity.
     * @param explosionRadius Explosion of radius upon impact.
     * @param damage Damage dealt to players in explosion.
     * @param source The player who sent the projectile. Used for Pickups.
     * @param primary If this is true, the projectile will tell the current GameMode when it lands.
     */
    public Projectile(double xVel, double yVel, int explosionRadius, int damage, PlayerMovement source, boolean primary){
        this.xVel = xVel;
        this.yVel = yVel;
        this.explosionRadius = explosionRadius;
        this.damage = damage;
        this.source = source;
        this.primary = primary;
    }

    @Override
    public void activate(GameObject gameObject) {
        super.activate(gameObject);
        this.collidable = getGameObject().getDependency(Collidable.class, getClass());
    }

    @Override
    public void update(double deltaTime) {
        getGameObject().xPos += xVel * deltaTime;
        getGameObject().yPos += yVel * deltaTime;
        xVel += GameMaster.xAcc * deltaTime;
        yVel += GameMaster.yAcc * deltaTime;

        // If it has hit something.
        boolean hit = collidable.checkCollisions().size() != 0;
        if (hit){
            // Generate explosion.
            int[][] explosionStructure = new int[explosionRadius * 2][explosionRadius * 2];
            Color[][] explosionVisuals = new Color[explosionStructure.length][explosionStructure[0].length];
            for (int y = -explosionRadius + 1; y < explosionRadius; y++) {
                for (int x = -explosionRadius + 1; x < explosionRadius; x++) {
                    if (x*x + y*y < explosionRadius*explosionRadius){
                        explosionStructure[y + explosionRadius - 1][x + explosionRadius - 1] = Layer.Trigger.ordinal();
                        explosionVisuals[y + explosionRadius - 1][x + explosionRadius - 1] = Color.ORANGE;
                    }
                }
            }
            int collidesWith = Layer.generateLayerMask(Layer.Player, Layer.Destructible);
            int layersPresent = Layer.generateLayerMask(Layer.Nothing, Layer.Trigger);
            Collidable explosion = new Collidable(explosionStructure, 0, 0, collidesWith, layersPresent);
            new GameObject(getGameObject().xPos - explosionRadius, getGameObject().yPos - explosionRadius, explosion, new Drawing(explosionVisuals, 0, 0), new SelfDestruct(0.2));

            // Go through all Collidables caught in explosion.
            for (Collidable col : explosion.checkCollisions()) {
                // If it's an Environment, destroy part of it.
                Environment env = col.getGameObject().getComponent(Environment.class);
                if (env != null){
                    env.destroyCollidableOverlap(explosion);
                }
                else {
                    // If it's a player, damage it.
                    PlayerMovement player = col.getGameObject().getComponent(PlayerMovement.class);
                    if (player != null){
                        player.takeDamage(damage);
                    }
                }
            }
        }
        if (hit || getGameObject().yPos < 0){
            done();
        }
    }

    /**
     * The projectile is done flying.
     */
    private void done(){
        getGameObject().queryDestroy();
        if (primary){
            GameMaster.getGameMode().registerStepDone();
        }
    }

    /**
     * Generate a new GameObject with all the necessary Components to function as a projectile.
     * @param xPos x position in world coordinates.
     * @param yPos y position in world coordinates.
     * @param xVel Initial x velocity.
     * @param yVel Initial y velocity.
     * @param explosionRadius Radius of explosion upon hitting something.
     * @param damage Damage to be dealt to players caught in explosion.
     * @param source The player firing the projectile. Used for Pickups.
     * @param primary If this is true, the Projectile will notify the current GameMode when it lands.
     * @return The GameObject.
     */
    public static GameObject getPrefab(double xPos, double yPos, double xVel, double yVel, int explosionRadius, int damage, PlayerMovement source, boolean primary){
        int collidesWith = Layer.generateLayerMask(Layer.Destructible, Layer.Indestructible, Layer.Player);
        return new GameObject(xPos, yPos,
                new Collidable(Projectile.class.getResourceAsStream("/unibanden/ist/gorillas/game/banana.col"), -8, -8, collidesWith),
                new Animation(Projectile.class.getResourceAsStream("/unibanden/ist/gorillas/game/banana.vis"), -8, -8, 0.25),
                new Projectile(xVel, yVel, explosionRadius, damage, source, primary));
    }
}
