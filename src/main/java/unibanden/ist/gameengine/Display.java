package unibanden.ist.gameengine;

import java.awt.Color;

/**
 * Responsible: Magnus Mouritzen s214931
 * Used by Visualiser to output the visuals.
 */
public interface Display {
    /**
     * Shows how big second dimension of visuals array should be for updateVisuals.
     * @return Integer width of display in pixels.
     */
    public int getWidth();

    /**
     * Shows how big first dimension of visuals array should be for updateVisuals.
     * @return Integer height of display in pixels.
     */
    public int getHeight();

    /**
     * Refresh the screen with new graphics.
     * @param visuals No item should be null. (0,0) is bottom left corner. Dimensions should match width and height.
     */
    public void updateVisuals(Color[][] visuals);
}
