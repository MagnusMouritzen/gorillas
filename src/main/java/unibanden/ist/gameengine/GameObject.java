package unibanden.ist.gameengine;

import java.util.ArrayList;

/**
 * Responsible: Magnus Mouritzen s214931
 * Almost every element in the game should be a GameObject.
 * It's a grouping of functionality into a single entity.
 * Each GameObject has multiple Components attached which make up its functionality.
 */
public class GameObject implements Updatable {
    public double xPos;
    public double yPos;
    private boolean destructionQueried = false;
    private final ArrayList<Component> components;

    /**
     * Create the GameObject at the specified position with an optional amount of Components to be attached immediately.
     * @param xPos x position of this in global coordinates starting in the bottom left corner.
     * @param yPos y position of this in global coordinates starting in the bottom left corner.
     * @param components Components to be attached immediately. Beware that the order matters. The Components are activated from left to right, so a Component can only depend on those that came before.
     */
    public GameObject(double xPos, double yPos, Component... components){
        this.xPos = xPos;
        this.yPos = yPos;
        this.components = new ArrayList<Component>();
        for (Component component : components){
            addComponent(component);
        }
        Engine.addGameObject(this);
        Engine.addUpdateListener(this);
    }

    /**
     * Attach and activate the Component.
     * @param component The Component to be activated.
     */
    public void addComponent(Component component){
        components.add(component);
        component.activate(this);
    }

    /**
     * Queries GameObject to be destroyed and have all Components deactivated at the end of the update loop.
     */
    public void queryDestroy(){
        destructionQueried = true;
        Engine.checkForDestructionQueries = true;
    }

    /**
     * SHOULD ONLY BE CALLED BY ENGINE!
     * Deactivate all components and remove from the game.
     */
    void destroyIfQueried(){
        if (destructionQueried){
            for (Component component : components){
                component.deactivate();
            }
            Engine.removeGameObject(this);
            Engine.removeUpdateListener(this);
        }
    }

    /**
     * Find a Component of a certain type on the GameObject.
     * @param type The type of Component to find. Can be a superclass of an existing Component.
     * @param <T>
     * @return The first occurrence of the requested type of GameObject or null if none is found.
     */
    public <T extends Component> T getComponent(Class<T> type){
        for (Component component : components){
            // This for loop moves up the class hierarchy until it goes past Component itself. This allows a present child of a requested type to be detected.
            for (Class<?> componentType = component.getClass(); componentType != Component.class.getSuperclass(); componentType = componentType.getSuperclass()){
                if (componentType == type){
                    return (T)component;
                }
            }
        }
        return null;
    }

    /**
     * Get and require a Component of a certain type. If not found, throw an exception.
     * @param type The type of Component.
     * @param sender The name of the whatever depends on this Component.
     * @param <T>
     * @return The first occurrence of the requested type of GameObject or null if none is found.
     */
    public <T extends Component> T getDependency(Class<T> type, Class<?> sender){
        T component = getComponent(type);
        if (component == null){
            throw new RuntimeException(sender.getName() + " depends on component of type " + type.getName());
        }
        return component;
    }

    @Override
    public void update(double deltaTime) {
        // Notify all components of the update call.
        for (int i = components.size() - 1; i >= 0; i--) {
            components.get(i).update(deltaTime);
        }
    }
}
