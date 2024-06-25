package unibanden.ist.gorillas.game;

import unibanden.ist.gameengine.*;

/**
 * Responsible: William Lohmann s204469
 * The controller that handles input from the player.
 */
public class PlayerController extends Component {
    private int id;
    private PlayerMovement pm;

    @Override
    public void activate(GameObject gameObject) {
        super.activate(gameObject);
        this.pm = getGameObject().getDependency(PlayerMovement.class, getClass());
    }

    @Override
    public void update(double deltaTime) {
        int x = 0; int y = 0; boolean jump = false;

        if (Engine.inputHandler.getInput(id - 1, Input.Up.ordinal())) {
            y += 1;
        }
        if (Engine.inputHandler.getInput(id - 1, Input.Down.ordinal())) {
            y -= 1;
        }
        if (Engine.inputHandler.getInput(id - 1, Input.Left.ordinal())) {
            x -= 1;
        }
        if (Engine.inputHandler.getInput(id - 1, Input.Right.ordinal())) {
            x += 1;
        }
        if (Engine.inputHandler.getInput(id - 1, Input.Shoot.ordinal())) {
            jump = true;
        }

        pm.input(x, y, jump, deltaTime);
    }

    /**
     * Set the id of the controller, choosing what player the controller commands.
     * @param id The id of the player the controller should send commands to.
     */
    public PlayerController(int id){
        this.id = id;
    }
}
