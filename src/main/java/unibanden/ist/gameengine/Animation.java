package unibanden.ist.gameengine;

import java.awt.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Responsible: Magnus Mouritzen s214931
 * A Drawing that automatically changes between given images at a given time interval, thus creating an animation.
 */
public class Animation extends Drawing {
    private Color[][][] frames;
    private double timeBetweenFrames;
    private double timer;
    private int currentFrame;

    /**
     * Creates an Animation from the given frames. Starts at the first frame.
     * @param frames The frames that make up the animation.
     * @param xOffset x position of drawing relative to GameObject.
     * @param yOffset y position of drawing relative to GameObject.
     * @param timeBetweenFrames Time in seconds to show each frame.
     */
    public Animation(Color[][][] frames, int xOffset, int yOffset, double timeBetweenFrames) {
        super(frames[0], xOffset, yOffset);
        this.frames = frames;
        this.timeBetweenFrames = timeBetweenFrames;
        timer = timeBetweenFrames;
        currentFrame = 0;
    }

    /**
     * Creates an Animation from a given file. Starts at the first frame.
     * @param path Path to the file.
     * @param xOffset x position of drawing relative to GameObject.
     * @param yOffset y position of drawing relative to GameObject.
     * @param timeBetweenFrames Time in seconds to show each frame.
     */
    public Animation(InputStream input, int xOffset, int yOffset, double timeBetweenFrames){
        this(new Color[][][]{{{Color.MAGENTA}}}, xOffset, yOffset, timeBetweenFrames);
        this.xOffset = xOffset;
        this.yOffset = yOffset;

        /**
         * File structure:
         * Height
         * Width
         * Amount of frames
         * Colors
         * stop
         * Matrices of colors separated by single lines.
         */

        try{
        	BufferedReader br = new BufferedReader(new InputStreamReader(input));
            int height = Integer.parseInt(br.readLine());
            int width = Integer.parseInt(br.readLine());
            int amountOfFrames = Integer.parseInt(br.readLine());
            HashMap<Character, Color> colors = getColorsFromFile(br);
            frames = new Color[amountOfFrames][][];
            for (int i = 0; i < amountOfFrames; i++) {
                frames[i] = getVisualsFromFile(br, colors, height, width);
                br.readLine();
            }
        }
        catch(Exception e){
            System.out.println("Failed to open or read file at path " + input);
        }
        this.visuals = frames[0];
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        // Change between frames.
        timer -= deltaTime;
        if (timer <= 0){
            timer += timeBetweenFrames;  // This means that a full cycle always takes the same time, even if there are delays on the way.
            currentFrame = (currentFrame + 1) % frames.length;
            visuals = frames[currentFrame];
        }
    }
}
