package unibanden.ist.gorillas.frontend;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import unibanden.ist.gameengine.InputHandler;

/**
 * Responsible: Magnus Mouritzen s214931
 * Reads KeyCode inputs from a JavaFX scene so that they can be read with the input system in Engine.
 */
public class KeybindInputHandler implements InputHandler {
    private final boolean[][] inputs;

    /**
     * Set up a listener for all the given KeyCodes and allow all game elements to read them.
     * @param scene The scene to read KeyCodes from.
     * @param keyCodes All KeyCodes to be listened to. The structure of this determines the indices they can be accessed with.
     */
    public KeybindInputHandler(Scene scene, KeyCode[][] keyCodes){
        // We're just using a couple of loops instead of a hash table because the amount of inputs is so small.
        inputs = new boolean[keyCodes.length][];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = new boolean[keyCodes[i].length];
        }

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                for (int i = 0; i < keyCodes.length; i++) {
                    for (int j = 0; j < keyCodes[i].length; j++) {
                        if (keyEvent.getCode() == keyCodes[i][j]){
                            inputs[i][j] = true;
                        }
                    }
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                for (int i = 0; i < keyCodes.length; i++) {
                    for (int j = 0; j < keyCodes[i].length; j++) {
                        if (keyEvent.getCode() == keyCodes[i][j]){
                            inputs[i][j] = false;
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean getInput(int i, int j) {
        return inputs[i][j];
    }
}
