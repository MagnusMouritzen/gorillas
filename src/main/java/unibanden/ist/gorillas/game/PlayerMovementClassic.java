package unibanden.ist.gorillas.game;

import java.awt.Color;
import unibanden.ist.gameengine.Drawing;
import unibanden.ist.gameengine.GameObject;

/**
 * Responsible: William Lohmann s204469
 * The PlayerMovement used for the Classic GameMode.
 */
public class PlayerMovementClassic extends PlayerMovement {
    protected double angle = (Math.PI / 6);
    protected final double deltaAngle = Math.PI / 3;
    protected double power = 95;
    protected final double deltaPower = 85;
    protected final double minPower = 50;
    protected final double maxPower = 400;
    protected boolean holding = false;
    protected Drawing aimArrow;
    protected int aimArrowMiddle;
    protected final double pixelsPerPower = .25;

    /**
     * Creates a PlayerMovementClassic object, with a unique id and an amount of lives.
     * @param id The id of the player.
     * @param hearts The amount of lives the player should start with.
     */
    public PlayerMovementClassic(int id, int hearts) {
        super(id, hearts);
    }

    @Override
    public void activate(GameObject gameObject) {
        super.activate(gameObject);
        int size = (int)(maxPower * pixelsPerPower) * 2 + 1;
        aimArrowMiddle = -size / 2;
        aimArrow = new Drawing(new Color[size][size], aimArrowMiddle, aimArrowMiddle + 14);
        gameObject.addComponent(aimArrow);
    }

    /**
     * Uses the user-input to determine power, angle and when to shoot.
     * @param xin x-axis input, should be an int from -1 to 1. This determines the angle of the shot.
     * @param yin y-axis input, should be an int from -1 to 1. This determines the power of the shot.
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

            if (action && !holding) {
                System.out.println("I am monke");
                shoot(angle, power);
                hasControl = false;
                holding = true;
                clearArrow();
            }
        }

        if (!action) {
            holding = false;
        }
    }

    @Override
    public void takeTurn() {
        hasControl = true;
        drawArrow();
    }

    /**
     * Draws an arrow to indicate with how much power and at what angle the player will shoot.
     */
    protected void drawArrow(){
        int lengthX = (int)(Math.cos(angle) * power * pixelsPerPower);
        int lengthY = (int)(Math.sin(angle) * power * pixelsPerPower);

        for (int x = 0; x < aimArrow.visuals[0].length; x++) {
            for (int y = 0; y < aimArrow.visuals.length; y++) {
                if ((x + aimArrowMiddle >= Math.min(lengthX, 0) && x + aimArrowMiddle <= Math.max(lengthX, 0)  // x is within the proper range
                        && y + aimArrowMiddle >= Math.min(lengthY, 0) && y + aimArrowMiddle <= Math.max(lengthY, 0))  // y is within the proper range
                        && ((Math.abs(lengthX) >= Math.abs(lengthY) && y + aimArrowMiddle == (int)(lengthY * (double)(x + aimArrowMiddle) / lengthX))  // If xVel will be greater than yVel, we should draw one pixel for each x. So if the cur y matches the x.
                        || (Math.abs(lengthY) > Math.abs(lengthX) && x + aimArrowMiddle == (int)(lengthX * (double)(y + aimArrowMiddle) / lengthY)))){  // Opposite of above
                    aimArrow.visuals[y][x] = Color.GREEN;
                }
                else{
                    aimArrow.visuals[y][x] = null;
                }
            }
        }
    }

    /**
     * Removes the arrow visuals. Used to hide the arrow when it isn't the given players turn.
     */
    protected void clearArrow(){
        for (int y = 0; y < aimArrow.visuals.length; y++) {
            for (int x = 0; x < aimArrow.visuals[0].length; x++) {
                aimArrow.visuals[y][x] = null;
            }
        }
    }
}
