package unibanden.ist.gameengine;

import java.util.ArrayList;

/**
 * Responsible: William Lohmann s204469
 * Manages physics in the game. Used to tell if a Collidable is hitting any other Collidables.
 */
public class Physics {
    private ArrayList<Collidable> collidables;

    /**
     * Instantiates a new ArrayList that is used to hold all the Collidables in the game
     */
    public Physics() {
        collidables = new ArrayList<Collidable>();
    }

    /**
     * Add a Collidable to the currently active Collidables
     * @param collidable The Collidable to add.
     */
    public void addCollidable(Collidable collidable) {
        collidables.add(collidable);
    }

    /**
     * Remove a Collidable from the currently active Collidables
     * @param collidable The Collidable to remove.
     */
    public void removeCollidable(Collidable collidable) { collidables.remove(collidable); }

    /**
     * Check if the given Collidable is colliding with any other Collidables currently active.
     * @param collidable The Collidable to check. The results are filtered by its collidesWith mask.
     * @return A list of all Collidables that the given Collidable is overlapping (colliding with).
     */
    public ArrayList<Collidable> checkCollisions(Collidable collidable) {
        ArrayList<Collidable> collisions = new ArrayList<Collidable>();

        for (Collidable col : collidables) {
            if (col != collidable) {
                if (checkCollision(collidable, col)) collisions.add(col);
            }
        }

        return collisions;
    }

    /**
     * Check if two specific Collidables are colliding.
     * @param col1 This is the one checking for the collision, so its collidesWith mask is used.
     * @param col2 This is the Collidable that's checked against. Its collidesWith mask does not matter.
     * @return true if they are colliding.
     */
    private boolean checkCollision(Collidable col1, Collidable col2) {
        // If col2 contains no layers that col1 can interact with.
        if ((col1.collidesWith & col2.layersPresent) == 0){
            return false;
        }

        int col1X = (int) col1.getGameObject().xPos + col1.xOffset;
        int col1Y = (int) col1.getGameObject().yPos + col1.yOffset;
        int col2X = (int) col2.getGameObject().xPos + col2.xOffset;
        int col2Y = (int) col2.getGameObject().yPos + col2.yOffset;

        int xDiff = col2X - col1X;
        int yDiff = col2Y - col1Y;
        int wDiff = col2.structure[0].length - col1.structure[0].length;
        int hDiff = col2.structure.length - col1.structure.length;

        // For each game pixel of the first Collidable's structure which overlaps with a pixel of the other Collidable's structure.
        // This won't run a single time if the structures don't overlap at all.
        for (int x = Math.max(0, xDiff); x < Math.min(col1.structure[0].length, col1.structure[0].length + xDiff + wDiff); x++){
            for (int y = Math.max(0, yDiff); y < Math.min(col1.structure.length, col1.structure.length + yDiff + hDiff); y++){
                // Check if col1's layer in the specific pixel is not 0 and if col2's layer in the specific pixel is present in col2's collidesWith mask.
                if (col1.structure[y][x] != 0 && ((col1.collidesWith & (1 << col2.structure[y - yDiff][x - xDiff])) != 0)){
                    return true;
                }
            }
        }
        return false;
    }
}
