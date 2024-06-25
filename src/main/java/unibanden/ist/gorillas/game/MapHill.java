package unibanden.ist.gorillas.game;

import java.awt.Color;

import java.util.Random;

/**
 * Responsible: Magnus Mouritzen s214931
 * Generate a hilly environment from a sin curve.
 */
public class MapHill extends Map {

    @Override
    public void generateLevel(int height, int width){
        int[][] structure =  new int[height][width];
        Color[][] visuals = new Color[height][width];
        Random random = new Random();
        double offset = random.nextDouble(Math.PI) - Math.PI / 2;  // Offset from the left to start the curves.
        double freq = random.nextDouble(0.03) + 0.01;  // How many hills there are.
        double scale = random.nextDouble(Math.min(70, height / 3) - 30);  // How tall the hills are.
        for (double x = 0; x < width; x += 1.0){
            for (int y = 0; y < (Math.sin(x * freq + offset) + 2) * scale; y++){
                structure[y][(int)x] = Layer.Destructible.ordinal();
                visuals[y][(int)x] = new Color(0, 140, 0);
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
