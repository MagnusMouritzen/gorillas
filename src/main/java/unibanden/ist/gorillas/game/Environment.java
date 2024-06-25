package unibanden.ist.gorillas.game;

import unibanden.ist.gameengine.*;

/**
 * Responsible: Magnus Mouritzen s214931
 * Allows a Collidable and Drawing to have bits of it destroyed.
 * Depends on: Collidable, Drawing.
 */
public class Environment extends Component {
    private Collidable collidable;
    private Drawing drawing;

    @Override
    public void activate(GameObject gameObject) {
        super.activate(gameObject);
        this.collidable = getGameObject().getDependency(Collidable.class, getClass());
        this.drawing = getGameObject().getDependency(Drawing.class, getClass());
    }

    /**
     * Destroy the parts of this environment that overlaps with the provided Collidable.
     * In a given pixel, the Drawing will only be modified if the Collidable also was.
     * Only pixels of layer Destructible can be destroyed.
     * @param destroyingCol The Collidable that determines what is removed. Only Trigger layer counts. This must interact with the Destructible layer.
     */
    public void destroyCollidableOverlap(Collidable destroyingCol){
        int xDiff = ((int)destroyingCol.getGameObject().xPos + destroyingCol.xOffset) - ((int)getGameObject().xPos + collidable.xOffset);
        int yDiff = ((int)destroyingCol.getGameObject().yPos + destroyingCol.yOffset) - ((int)getGameObject().yPos + collidable.yOffset);
        int wDiff = destroyingCol.structure[0].length - collidable.structure[0].length;
        int hDiff = destroyingCol.structure.length - collidable.structure.length;

        // Go through the pixels that both Collidables are on.
        for (int x = Math.max(0, xDiff); x < Math.min(collidable.structure[0].length, collidable.structure[0].length + xDiff + wDiff); x++){
            for (int y = Math.max(0, yDiff); y < Math.min(collidable.structure.length, collidable.structure.length + yDiff + hDiff); y++){
                // If the layers match up.
                if (collidable.structure[y][x] == Layer.Destructible.ordinal() && destroyingCol.structure[y - yDiff][x - xDiff] == Layer.Trigger.ordinal()){
                    collidable.structure[y][x] = Layer.Nothing.ordinal();
                    if (Generic.checkWithinBounds(x, y, drawing.visuals[0].length, drawing.visuals.length)){
                        drawing.visuals[y][x] = null;
                    }
                }
            }
        }
    }
}
