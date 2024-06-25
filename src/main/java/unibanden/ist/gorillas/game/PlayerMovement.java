package unibanden.ist.gorillas.game;

import java.awt.Color;
import unibanden.ist.gameengine.Collidable;
import unibanden.ist.gameengine.Component;
import unibanden.ist.gameengine.Drawing;
import unibanden.ist.gameengine.GameObject;
import unibanden.ist.gorillas.game.*;
import java.util.Arrays;



/**
 * Responsible: William Lohmann s204469
 * The parent class to all players. Takes care of anything that a player would need in all the gamemodes.
 * Like collisions, facing the right way, taking damage, shooting etc.
 */
public abstract class PlayerMovement extends Component {
    private int id;
    public int getID(){ return id; }
    private int hearts = 1;
    private boolean dead = false;
    private Drawing healthBar;

    private boolean facingRight = true;
    protected boolean getFacingRight() { return facingRight; }
    protected void setFacingRight(boolean value){
        facingRight = value;
        drawing.mirrored = !facingRight;
    }

    protected double xVel = 0;
    protected double yVel = 0;

    protected boolean grounded = true;
    private Collidable collidable;
    private Drawing drawing;
    private Collidable groundCheck;

    public boolean hasControl = false;
    private Weapon baseWeapon;
    private Weapon curWeapon;

    /**
     * Creates a PlayerMovement object, with a unique id and an amount of lives.
     * @param id The id of the player.
     * @param hearts The amount of lives the player should start with.
     */
    public PlayerMovement(int id, int hearts) {
        this.id = id;
        this.hearts = hearts;
    }

    @Override
    public void activate(GameObject gameObject) {
        super.activate(gameObject);
        this.healthBar = new Drawing(new Color[0][], 0, 35);
        getGameObject().addComponent(this.healthBar);
        updateHealthBar();

        this.collidable = getGameObject().getDependency(Collidable.class, getClass());
        this.drawing = getGameObject().getDependency(Drawing.class, getClass());

        setFacingRight(getGameObject().xPos < GameMaster.getGameWidth() /2.);
        int[][] structure = new int[1][17];
        Arrays.fill(structure[0], Layer.Trigger.ordinal());
        this.groundCheck = new Collidable(structure, collidable.xOffset + 5, -1, Layer.generateLayerMask(Layer.Indestructible, Layer.Destructible, Layer.Player), Layer.generateLayerMask(Layer.Trigger));
        getGameObject().addComponent(groundCheck);
        attachWeapon(new WeaponStandard());
        this.baseWeapon = this.curWeapon;
    }

    /**
     * Receives controller input, subclasses define how to handle the input.
     * @param xin x-axis input, should be an int from -1 to 1.
     * @param yin y-axis input, should be an int from -1 to 1.
     * @param action The shoot/confirm button input.
     * @param deltaTime The time since last frame.
     */
    public abstract void input(int xin, int yin, boolean action, double deltaTime);

    /**
     * Lets the player take their turn, subclasses define what a turn is.
     */
    public abstract void takeTurn();

    /**
     * Removes an amount of health from the players health-pool.
     * @param amount Amount of health to be removed.
     */
    public void takeDamage(int amount){
        hearts -= amount;
        if (hearts <= 0) die();
        else updateHealthBar();
    }

    /**
     * Adds an amount of health to the players health-pool.
     * @param amount Amount of health to be added.
     */
    public void heal(int amount){
        hearts += amount;
        updateHealthBar();
    }

    /**
     * Kills the player and announces it to the GameMaster.
     */
    public void die() {
        dead = true;
        hasControl = false;
        GameMaster.getGameMode().playerDead(this);
        getGameObject().queryDestroy();
    }

    /**
     * Fires a shot from the player.
     * @param angle The angle of the shot.
     * @param power The amount of force put on the shot.
     */
    public void shoot(double angle, double power) {
        if (hasControl) {
            double niceDistance = 28;
            double c = Math.cos(angle);
            double s = Math.sin(angle);

            curWeapon.shoot(getGameObject().xPos + (c * niceDistance), getGameObject().yPos + ((double) collidable.structure.length / 2) + s * niceDistance, c * power, s * power);
        }
    }

    /**
     * Changes the current weapon of the player.
     * @param weapon The weapon the player should swap to.
     */
    public void attachWeapon(Weapon weapon){
        curWeapon = weapon;
        weapon.owner = this;
    }

    /**
     * Sets the player's weapon to the default weapon.
     */
    public void detachWeapon(){
        curWeapon.owner = null;
        curWeapon = baseWeapon;
    }

    /**
     * Updates the health bar above the players head.
     */
    private void updateHealthBar(){
        int height = 5;
        int width = (hearts - 1) * 10 + height;
        healthBar.xOffset = -width / 2;
        healthBar.visuals = new Color[height][width];
        for (int i = 0; i < hearts; i++) {
            for (int y = 0; y < height; y++) {
                for (int x = (i * 10); x < (i * 10) + height; x++) {
                    healthBar.visuals[y][x] = Color.RED;
                }
            }
        }
    }

    @Override
    public void update(double deltaTime){
        // Movement from velocity as well as checking if the player is standing on something is handled here.

        super.update(deltaTime);
        if (getGameObject().yPos < -30){
            die();
        }

        if (yVel != 0 || xVel != 0){
            // Decide the amount of movement
            double xMov = xVel * deltaTime;
            double yMov = yVel * deltaTime;
            getGameObject().xPos += xMov;
            getGameObject().yPos += yMov;
            // Check if player moved into another collider.
            if (collidable.checkCollisions().size() != 0){
                xVel = 0;
                yVel = 0;

                // Used for calculations that require the absolute value of the movement.
                double xDir = xMov < 0 ? -1 : 1;
                double yDir = yMov < 0 ? -1 : 1;
                // The ratio of x to y movement.
                double xToY = (xMov != 0 && yMov != 0) ? ((yMov * yDir) / (xMov * xDir)) : 0;

                // Move backwards step by step until we no longer collide. This is done one pixel at a time on the axis with the greatest movement.
                do {
                    double yRed;
                    double xRed;
                    if (yMov * yDir > xMov * xDir){  // If the y movement was greater than the y movement.
                        yRed = Math.min(1, yMov * yDir);  // If one full pixel can be moved backwards, do that, otherwise move the remaining amount.
                        xRed = xToY != 0 ? yRed / xToY : 0;  // Move a corresponding amount on the other axis. The check is there to avoid division by zero.
                    }
                    else{
                        xRed = Math.min(1, xMov * xDir);  // If one full pixel can be moved backwards, do that, otherwise move the remaining amount.
                        yRed = xRed * xToY;  // Move a corresponding amount on the other axis.
                    }
                    // Flip the reduction to the correct direction (it was positive before).
                    yRed *= yDir;
                    xRed *= xDir;

                    getGameObject().xPos -= xRed;
                    getGameObject().yPos -= yRed;
                    xMov -= xRed;
                    yMov -= yRed;
                } while ((yMov != 0 || xMov != 0) && collidable.checkCollisions().size() != 0);
            }

        }

        // Check if the pixels directly under the player are occupied.
        grounded = groundCheck.checkCollisions().size() != 0;
        if (!grounded){
            // Apply gravity acceleration.
            yVel += GameMaster.yAcc * deltaTime;
        }
    }

    /**
     * Creates a GameObject complete with all the required Components to have a functional Player.
     * @param xPos The x position in world coordinates to spawn the Player at.
     * @param yPos The y position in world coordinates to spawn the Player at.
     * @param id The unique id of the Player.
     * @param pm The PlayerMovement that dictates the kind of Player that is spawned.
     * @return The GameObject.
     */
    public static GameObject getPrefab(double xPos, double yPos, int id, PlayerMovement pm){
        int collidesWith = Layer.generateLayerMask(Layer.Destructible, Layer.Indestructible, Layer.Player);
        
        return new GameObject(xPos, yPos,
                new Collidable(PlayerMovement.class.getResourceAsStream("/unibanden/ist/gorillas/game/monke.col"), -14, 0, collidesWith),
                new Drawing(PlayerMovement.class.getResourceAsStream("/unibanden/ist/gorillas/game/monke.vis"), -14, 0),
                pm, new PlayerController(id));
    }
}
