package unibanden.ist.gorillas.game;

/**
 * Responsible: William Lohmann s204469
 * The PlayerMovement used for the Modern GameMode.
 */
public class PlayerMovementModern extends PlayerMovementClassic {
    private double jumpPower = 0.65;
    private boolean jumped = false;
    private boolean shot = false;

    /**
     * Creates a PlayerMovementModern object, with a unique id and an amount of lives.
     * @param id The id of the player.
     * @param hearts The amount of lives the player should start with.
     */
    public PlayerMovementModern(int id, int hearts) {
        super(id, hearts);
    }

    /**
     * Uses the user-input to determine power and angle, these values are used to both jump and shoot.
     * @param xin x-axis input, should be an int from -1 to 1. This determines the angle of the jump/shot.
     * @param yin y-axis input, should be an int from -1 to 1. This determines the power of the jump/shot.
     * @param action The shoot/confirm button input.
     * @param deltaTime The time since last frame.
     */
    @Override
    public void input(int xin, int yin, boolean action, double deltaTime) {
        if (hasControl) {
            if (xin != 0 || yin != 0) {
                angle -= xin * deltaAngle * deltaTime;
                power += yin * deltaPower * deltaTime;
                if (power < minPower) {
                    power = minPower;
                } else if (power > maxPower) {
                    power = maxPower;
                }
                drawArrow();
            }

            if (action && !holding && grounded) {
                System.out.println("I am monke");
                if (!jumped) jump(angle, power);
                else shoot(angle, power);
                holding = true;
            }
        }

        if (jumped && shot) {
            hasControl = false;
            clearArrow();
        }

        if (!action) {
            holding = false;
        }
    }

    /**
     * Makes sure the Player's state is set so the Player is ready to take his turn.
     */
    @Override
    public void takeTurn() {
        hasControl = true;
        jumped = false;
        shot = false;
        drawArrow();
    }

    @Override
    public void shoot(double angle, double power) {
        super.shoot(angle, power);
        shot = true;
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

            yVel += jumpPower * s * power;
            xVel += jumpPower * c * power;

            jumped = true;
        }
    }
}