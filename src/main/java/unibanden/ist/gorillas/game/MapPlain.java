package unibanden.ist.gorillas.game;

import java.awt.Color;

/**
 * Responsible: Magnus Mouritzen s214931
 * Generates a completely flat environment.
 */
public class MapPlain extends Map {

    @Override
    public void generateLevel(int height, int width){
        int[][] structure =  new int[height][width];
        Color[][] visuals = new Color[height][width];
        for (int y = 0; y < height / 3; y++){
            for (int x = 0; x < width; x++){
                structure[y][x] = Layer.Destructible.ordinal();
                visuals[y][x] = new Color(113, 68, 0);
            }
        }
        for (int y = height / 3; y < height; y++){
            for (int x = 0; x < width; x++){
                structure[y][x] = Layer.Nothing.ordinal();
            }
        }
        // Make indestructible sides
        for (int x : new int[]{0, width - 1}){
            for (int y = 0; y < height; y++) {
                structure[y][x] = Layer.Indestructible.ordinal();
            }
        }
        assemble(structure, visuals);
    }
}
