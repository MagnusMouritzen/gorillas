package unibanden.ist.gorillas.game;

import java.awt.Color;
import unibanden.ist.gameengine.Collidable;
import unibanden.ist.gameengine.Drawing;
import unibanden.ist.gameengine.GameObject;

/**
 * Responsible: Magnus Mouritzen s214931
 * Holds functionality for producing an environment of a certain type to be played on.
 */
public abstract class Map {
    /**
     * Generate a GameObject with an environment of the given dimensions to be played on.
     * @param height Maximum height of the environment.
     * @param width Maximum width of the environment.
     */
    public abstract void generateLevel(int height, int width);

    /**
     * Create a new GameObject with an Environment Component as well as a Collidable and Drawing from the provided arrays.
     * @param structure Structure of the Colldiable. Should only contain layers Nothing, Destructible, and Indestructible.
     * @param visuals Visuals of the Drawing.
     */
    protected void assemble(int[][] structure, Color[][] visuals){
        Environment environment = new Environment();
        int layersPresent = Layer.generateLayerMask(Layer.Nothing, Layer.Destructible, Layer.Indestructible);
        new GameObject(0, 0, new Collidable(structure, 0, 0, 0, layersPresent), new Drawing(visuals, 0, 0), environment);
    }
}
