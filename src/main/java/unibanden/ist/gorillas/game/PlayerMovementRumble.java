package unibanden.ist.gorillas.game;

import unibanden.ist.gameengine.GameObject;

/**
 * Responsible: William Lohmann s204469
 * The PlayerMovement used for the Rumble GameMode.
 */
public class PlayerMovementRumble extends PlayerMovement {
    private double moveSpeed = 70;
    private double jumpPower = 127;
    private boolean jumping = false;
    private boolean holding = false;
    private boolean shotReady = false;
    private double shotDelay = .65;
    private double currDelay = 0;
    private double shotPower = 95;

    /**
     * Creates a PlayerMovementRumble object, with a unique id and an amount of lives.
     * @param id The id of the player.
     * @param hearts The amount of lives the player should start with.
     */
    public PlayerMovementRumble(int id, int hearts) {
        super(id, hearts);
    }

    @Override
    public void activate(GameObject gameObject) {
        super.activate(gameObject);
    }

    /**
     * Uses the user-input to control the Player, both movement wise and to shoot.
     * @param xin x-axis input, should be an int from -1 to 1. This determines the Player's x-movement.
     * @param yin y-axis input, should be an int from -1 to 1. This determines if the Player jumps.
     * @param action The shoot/confirm button input.
     * @param deltaTime The time since last frame.
     */
    @Override
    public void input(int xin, int yin, boolean action, double deltaTime) {
        if (xin > 0) setFacingRight(true);
        else if (xin < 0) setFacingRight(false);

        if (hasControl) {
            xVel = xin * moveSpeed;

            if (yin > 0 && grounded && !jumping) {
                jumping = true;
                jump(Math.PI / 2., jumpPower);
            }

            if (action && !holding && shotReady) {
                if (getFacingRight()) shoot((Math.PI / 4.), shotPower);
                else shoot(Math.PI * 3./4., shotPower);
                currDelay = shotDelay;
                shotReady = false;
                holding = true;
            }
        }

        if (!action) {
            holding = false;
        }

        if (yin <= 0 && grounded) {
            jumping = false;
        }

        if (currDelay <= 0 && !shotReady) shotReady = true;
        else if (!shotReady) currDelay -= deltaTime;
    }

    @Override
    public void takeTurn() {
        hasControl = true;
    }

    /**
     * Makes the Player jump at the given angle with the given amount of force.
     * @param angle The angle of the jump.
     * @param power The force applied to the Player.
     */
    public void jump(double angle, double power) {
        if (hasControl) {
            double c = Math.cos(angle);
            double s = Math.sin(angle);

            yVel += s * power;
            xVel += c * power;
        }
    }
}