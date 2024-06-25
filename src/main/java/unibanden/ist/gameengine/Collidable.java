package unibanden.ist.gameengine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import unibanden.ist.gorillas.game.*;

/**
 * Responsible: William Lohmann s204469
 * This takes physical space in the game and can be detected by other Collidables. It's managed by Physics.
 * It works on a system of layers represented by integers, where each number is a different layer. 0 means nothing and does not trigger collisions.
 */
public class Collidable extends Component{
    public int[][] structure;
    public int xOffset;
    public int yOffset;
    public int collidesWith;
    public int layersPresent;

    /**
     * Create a new Collidable where all parts are provided.
     * @param structure The colliders structure where each integer corresponds to a layer.
     * @param xOffset x position of Collidable relative to its GameObject.
     * @param yOffset y position of Collidable relative to its GameObject.
     * @param collidesWith LayerMask of the layers it can collide with. (Bitmask)
     * @param layersPresent LayerMask of the layers present in this collider. (Bitmask)
     */
    public Collidable(int[][] structure, int xOffset, int yOffset, int collidesWith, int layersPresent){
        this.structure = structure;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.collidesWith = collidesWith;
        this.layersPresent = layersPresent;
    }

    /**
     * Create new Collidable from .col file.
     * @param path Path to .col file.
     * @param xOffset x position of Collidable relative to its GameObject.
     * @param yOffset y position of Collidable relative to its GameObject.
     * @param collidesWith LayerMask of the layers it can collide with.
     */
    public Collidable(InputStream input, int xOffset, int yOffset, int collidesWith){
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.collidesWith = collidesWith;

        /*
         * File structure:
         * Height
         * Width
         * layersPresent
         * Matrix of layers as integers
         */
        try{  // Build structure and set layersPresent.
        	BufferedReader br = new BufferedReader(new InputStreamReader(input));
            structure = new int[Integer.parseInt(br.readLine())][Integer.parseInt(br.readLine())];
            layersPresent = Integer.parseInt(br.readLine());
            String[] words;
            for (int y = structure.length - 1; y >= 0 ; y--) {
                words = br.readLine().split(" ");
                for (int x = 0; x < structure[0].length; x++) {
                    structure[y][x] = Integer.parseInt(words[x]);
                }
            }
        }
        catch(Exception e){
            System.out.println("Failed to open or read file at path " + input);
            this.structure = new int[][]{{0}};
            this.layersPresent = 1;
        }
    }

    /**
     * Determine if this collider currently overlaps other colliders, filtered by this object's collidesWith.
     * @return All hit colliders.
     */
    public ArrayList<Collidable> checkCollisions() {
        return Engine.physics.checkCollisions(this);
    }

    @Override
    public void activate(GameObject gameObject) {
        super.activate(gameObject);
        Engine.physics.addCollidable(this);
    }

    @Override
    public void deactivate(){
        super.deactivate();
        Engine.physics.removeCollidable(this);
    }
}
