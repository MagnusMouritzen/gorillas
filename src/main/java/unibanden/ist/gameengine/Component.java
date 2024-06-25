package unibanden.ist.gameengine;

/**
 * Responsible: Magnus Mouritzen s214931
 * Almost every element in the game with functionality should extend this.
 * Components are meant to be attached to GameObjects, which gives a reference for their functionality and ties them together.
 * A Component represents an attribute of a GameObject.
 */
public class Component {
    private GameObject gameObject;
    public GameObject getGameObject() { return gameObject; }

    /**
     * When the component is attached to a GameObject, the GameObject calls this.
     * This is essentially a parameterless constructor that is called later, giving the ability to make sure that all dependencies are in place.
     * A Component should not have any functionality before it is activated.
     * @param gameObject The parent that this is attached to.
     */
    public void activate(GameObject gameObject){
        this.gameObject = gameObject;
    }

    /**
     * Makes the component inactive again. Everything that the Component signs up for in activate should be removed again here.
     */
    public void deactivate(){}

    public void update(double deltaTime){ }
}
