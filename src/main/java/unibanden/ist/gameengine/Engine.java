package unibanden.ist.gameengine;

import java.util.ArrayList;

/**
 * Responsible: Magnus Mouritzen s214931
 * The master of the entire game engine.
 */
public class Engine {
    private static boolean turnedOn = false;
    public static boolean getTurnedOn() { return turnedOn; }
    public static Physics physics;
    public static Visualiser visualiser;
    public static InputHandler inputHandler;
    public static boolean checkForDestructionQueries = false;

    private static ArrayList<Updatable> updateListeners;
    private static ArrayList<GameObject> gameObjects;
    private static GameLoop gameLoop;

    /**
     * Starts the game engine an enables the actual game to be started.
     * @param inputHandler Something to make use of the built-in input system. Can be left at null.
     * @param gameLoop Something to handle running the update loop.
     */
    public static void start(InputHandler inputHandler, GameLoop gameLoop){
        updateListeners = new ArrayList<Updatable>();
        gameObjects = new ArrayList<GameObject>();
        physics = new Physics();
        visualiser = new Visualiser();
        Engine.inputHandler = inputHandler;
        Engine.gameLoop = gameLoop;
        gameLoop.start();
        turnedOn = true;
    }

    /**
     * Stop the engine and destroy all GameObjects.
     */
    public static void stop(){
        turnedOn = false;
        gameLoop.stop();
        for (int i = gameObjects.size() - 1; i >= 0; i--){
            gameObjects.get(i).queryDestroy();
        }

        // Force out any remaining listeners.
        for (int i = updateListeners.size() - 1; i >= 0; i--){
            removeUpdateListener(updateListeners.get(i));
        }
    }

    /**
     * Call update on all objects signed up to listen. (Primarily GameObjects)
     * @param deltaTime The time in seconds since the last update call. This allows listeners to perform operations at a certain speed independent of framerate.
     */
    public static void invokeUpdate(double deltaTime) {
        for (int i = updateListeners.size() - 1; i >= 0; i--){
            updateListeners.get(i).update(deltaTime);
        }
        if (checkForDestructionQueries){  // If a GameObject has queried itself for destruction.
            for (int i = gameObjects.size() - 1; i >= 0; i--){
                gameObjects.get(i).destroyIfQueried();
            }
            checkForDestructionQueries = false;
        }
        visualiser.updateVisuals();
    }

    public static void addUpdateListener(Updatable updatable) { updateListeners.add(updatable); }

    public static void removeUpdateListener(Updatable updatable) { updateListeners.remove(updatable); }

    public static void addGameObject(GameObject gameObject) { gameObjects.add(gameObject); }

    public static void removeGameObject(GameObject gameObject) { gameObjects.remove(gameObject); }
}
