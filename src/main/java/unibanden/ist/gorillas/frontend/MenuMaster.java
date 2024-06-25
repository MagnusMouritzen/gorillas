package unibanden.ist.gorillas.frontend;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import unibanden.ist.gameengine.Engine;
import unibanden.ist.gorillas.game.GameMaster;
import unibanden.ist.gorillas.game.Input;
import unibanden.ist.gorillas.game.Map;
import unibanden.ist.gorillas.game.PickupType;
import unibanden.ist.gorillas.game.GameMode;
import unibanden.ist.gorillas.game.WinListener;

import java.io.*;
import java.util.Arrays;

/**
 * Responsible: Magnus Mouritzen s214931
 * Keeps track of settings throughout the menu screens.
 * Acts as the link between Controller and Model.
 */
public class MenuMaster {
    public static KeyCode[][] keyCodes;  // The KeyCodes to listen to when playing.
    public static boolean[] pickups;  // All allowed pickups in the order specified in the PickupType enum.
    public static int playerCount;  // The amount of players.
    public static double gravity;  // The gravity to use in game. Will be multiplied by -12.
    public static double windMax;  // The maximum possible wind speed in either direction.
    public static int width;  // Width of the application in pixels.
    public static int height;  // Height of the application in pixels, excluding the top bar.
    public static int scale;  // The scale of the in game graphics.

    public static Map map;  // The map to play on.
    public static GameMode gameMode;  // The game mode to play on.

    private static final String settingsPath = "settings.ser";
    public static final int minWidth = 900;
    public static final int minHeight = 600;

    /**
     * Set up the MenuMaster. This should be called as soon as the application starts.
     */
    public static void initialise(){
        // Load settings if the file exists, otherwise set them to default values.
        try {
            ObjectInput input = new ObjectInputStream (new BufferedInputStream(new FileInputStream(settingsPath)));
            keyCodes = (KeyCode[][])input.readObject();
            gravity = (double)input.readObject();
            windMax = (double)input.readObject();
            pickups = (boolean[])input.readObject();
            width = (int)input.readObject();
            height = (int)input.readObject();
            scale = (int)input.readObject();
            System.out.println("Succesfully loaded.");
        }
        catch (Exception e){
            System.out.println("Failed at loading.");
            setStandardValues();
        }
        playerCount = keyCodes.length;
    }

    /**
     * Default settings to standard values for 2 players.
     */
    private static void setStandardValues(){
        playerCount = 2;
        KeyCode[][] standardKeyCodes = new KeyCode[][]{{KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D, KeyCode.R}, {KeyCode.I, KeyCode.K, KeyCode.J, KeyCode.L, KeyCode.Y}};
        keyCodes = new KeyCode[playerCount][Input.values().length];
        for (int i = 0; i < keyCodes.length; i++) {
            for (int j = 0; j < keyCodes[i].length; j++) {
                if (standardKeyCodes[0].length > j){
                    keyCodes[i][j] = standardKeyCodes[i][j];
                }
            }
        }

        gravity = 10;
        windMax = 30;
        
        pickups = new boolean[PickupType.values().length];
        Arrays.fill(pickups, true);

        width = minWidth;
        height = minHeight;
        scale = 2;
    }

    /**
     * Save settings to a file.
     */
    public static void save(){
        try {
            ObjectOutput output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(settingsPath)));
            output.writeObject(keyCodes);
            output.writeObject(gravity);
            output.writeObject(windMax);
            output.writeObject(pickups);
            output.writeObject(width);
            output.writeObject(height);
            output.writeObject(scale);
            output.close();
            System.out.println("Succesfully saved.");
        }
        catch (Exception e){
            System.out.println("Failed to save settings.");
        }
    }

    /**
     * Starts the engine and the game with the chosen settings.
     * @param scene The scene to look for input in, ie. the scene the game runs in.
     * @param canvas The canvas to display the game on.
     * @param winListener The object to be notified when the game has a winner.
     */
    public static void startGame(Scene scene, Canvas canvas, WinListener winListener){
        Engine.start(new KeybindInputHandler(scene, MenuMaster.keyCodes), new AnimationTimerGameLoop());
        Engine.visualiser.attachDisplay(new CanvasDisplay(canvas, scale));
        GameMaster.yAcc = -gravity * 12;
        gameMode.setWinListener(winListener);

        GameMaster.start(map, gameMode, playerCount, width / scale, height / scale);
    }

    /**
     * Get array of allowed pickups.
     * @return Allowed pickups.
     */
    public static PickupType[] generateAllowedPickupTypes(){
        int size = 0;
        for (boolean pickup : pickups){
            if (pickup){
                size++;
            }
        }
        PickupType[] allowedPickups = new PickupType[size];
        int allowedPickupsIndex = 0;
        for (int i = 0; i < pickups.length; i++) {
            if (pickups[i]){
                allowedPickups[allowedPickupsIndex++] = PickupType.values()[i];
                System.out.println(PickupType.values()[i].name());
            }
        }
        return allowedPickups;
    }
}
