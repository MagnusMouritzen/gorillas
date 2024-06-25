package unibanden.ist.gameengine;

import java.awt.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

/**
 * Responsible: Magnus Mouritzen s214931
 * Something visual to be shown on the display. It's managed by Visualiser.
 */
public class Drawing extends Component {
    public Color[][] visuals;
    public int xOffset;
    public int yOffset;
    public boolean mirrored = false;

    /**
     * Make a drawing with the given visuals.
     * @param visuals The visuals of the drawing.
     * @param xOffset x position of drawing relative to GameObject.
     * @param yOffset y position of drawing relative to GameObject.
     */
    public Drawing(Color[][] visuals, int xOffset, int yOffset){
        this.visuals = visuals;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    /**
     * Make a drawing from a specified file.
     * @param path Path to the file.
     * @param xOffset x position of drawing relative to GameObject.
     * @param yOffset y position of drawing relative to GameObject.
     */
    public Drawing(InputStream input, int xOffset, int yOffset){
        this.xOffset = xOffset;
        this.yOffset = yOffset;

        /*
         * File structure:
         * Height
         * Width
         * Colors
         * stop
         * Matrix of colors
         */

        try{
        	BufferedReader br = new BufferedReader(new InputStreamReader(input));
            int height = Integer.parseInt(br.readLine());
            int width = Integer.parseInt(br.readLine());

            this.visuals = getVisualsFromFile(br, getColorsFromFile(br), height, width);
        }
        catch(Exception e){
            System.out.println("Failed to open or read file at path " + input);
            System.out.println(e.getMessage());
            this.visuals = new Color[][]{{Color.MAGENTA}};
        }
    }

    /**
     * Create a color hashmap from the colors specified in the file.
     * @param br The reader of the file.
     * @return The hashmap of colors.
     * @throws IOException
     */
    protected HashMap<Character, Color> getColorsFromFile(BufferedReader br) throws IOException {
        HashMap<Character, Color> colors = new HashMap<>();
        colors.put('n', null);
        String line;
        String[] words;
        while (!Objects.equals(line = br.readLine(), "stop")){
            words = line.split(" ");
            colors.put(words[0].charAt(0), Color.decode(words[1]));
        }
        return colors;
    }

    /**
     * Creates the visuals from the file.
     * @param br The file reader.
     * @param colors The color hashmap that can be produced by getColorsFromFile.
     * @param height Height of the visuals.
     * @param width Width of the visuals.
     * @return The visuals.
     * @throws IOException
     */
    protected Color[][] getVisualsFromFile(BufferedReader br, HashMap<Character, Color> colors, int height, int width) throws IOException {
        Color[][] curVisuals = new Color[height][width];
        String[] words;
        for (int y = height - 1; y >= 0 ; y--) {
            words = br.readLine().split(" ");
            for (int x = 0; x < curVisuals[0].length; x++) {
                curVisuals[y][x] = colors.get(words[x].charAt(0));
            }
        }
        return curVisuals;
    }

    @Override
    public void activate(GameObject gameObject) {
        super.activate(gameObject);
        Engine.visualiser.addDrawing(this);
    }

    @Override
    public void deactivate(){
        super.deactivate();
        Engine.visualiser.removeDrawing(this);
    }
}
