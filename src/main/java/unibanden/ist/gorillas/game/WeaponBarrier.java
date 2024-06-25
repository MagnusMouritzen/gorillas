package unibanden.ist.gorillas.game;

import java.awt.Color;
import unibanden.ist.gameengine.Collidable;
import unibanden.ist.gameengine.Drawing;
import unibanden.ist.gameengine.GameObject;

/**
 * Responsible: Magnus Mouritzen s214931
 * Creates a barrier instead of firing a projectile.
 */
public class WeaponBarrier extends WeaponSpecial{
    private static final int height = 60;
    private static final int width = 30;
    private static final double distance = .5;

    @Override
    protected void fire(double xPos, double yPos, double xVel, double yVel) {
        int[][] structure = new int[height][width];
        Color[][] visuals = new Color[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                structure[y][x] = Layer.Destructible.ordinal();
                visuals[y][x] = new Color(119, 61, 56);
            }
        }

        new GameObject(xPos + xVel * distance, yPos,
                new Drawing(visuals, -width / 2, -height/2),
                new Collidable(structure, -width / 2, -height / 2, 0, Layer.generateLayerMask(Layer.Destructible)),
                new Environment());  // Adding the environment makes it possible to destroy it.
        GameMaster.getGameMode().registerStepDone();
    }
}
