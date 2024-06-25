package unibanden.ist.gorillas.frontend;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import unibanden.ist.gameengine.Display;

/**
 * Responsible: Magnus Mouritzen s214931
 * A display for gameengine.Visualiser to be used with a JavaFX Canvas.
 */
public class CanvasDisplay implements Display {
    private int width;
    private int height;
    private final PixelWriter pixelWriter;
    private int scale;

    /**
     *
     * @param canvas To display graphics on.
     * @param scale Size of each game pixel in screen pixels.
     */
    public CanvasDisplay(Canvas canvas, int scale){
        this.pixelWriter = canvas.getGraphicsContext2D().getPixelWriter();
        this.scale = scale;
        this.width = (int)(canvas.getWidth() / scale);
        this.height = (int)(canvas.getHeight() / scale);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void updateVisuals(java.awt.Color[][] visuals) {
        for(int y = 0; y < visuals.length; y++){
            for(int x = 0; x < visuals[0].length; x++){
                // If parts of visuals are null, these will be made purple to catch the dev's attention.
                // The color must be converted from java.awt.Color to javafx.scene.paint.Color.
                Color color = visuals[y][x] == null ? Color.PURPLE : Color.rgb(visuals[y][x].getRed(), visuals[y][x].getGreen(), visuals[y][x].getBlue());
                // For each game pixel, a number of screen pixels will be filled with the same colour.
                for (int i = 0; i < scale; i++) {
                    for (int j = 0; j < scale; j++) {
                        // The y is reversed because (0,0) in Canvas is in the top left corner.
                        pixelWriter.setColor(x * scale + i, (visuals.length - y - 1) * scale + j, color);
                    }
                }
            }
        }
    }
}
