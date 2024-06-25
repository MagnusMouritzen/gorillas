package unibanden.ist.gameengine;

import java.awt.Color;

import java.util.ArrayList;

/**
 * Responsible: Magnus Mouritzen s214931
 * Processes all registered drawings and compiles them into one image, which it displays on the connected Display.
 */
public class Visualiser {
    private ArrayList<Drawing> drawings;
    private Display display;
    private Color bgColor;

    public Visualiser(){
        drawings = new ArrayList<Drawing>();
        bgColor = Color.BLUE;
    }

    /**
     * Attach a display for visuals to be sent to.
     * @param display
     */
    public void attachDisplay(Display display){
        this.display = display;
    }

    public void addDrawing(Drawing drawing){
        drawings.add(drawing);
    }

    public void removeDrawing(Drawing drawing){
        drawings.remove(drawing);
    }

    /**
     * Change the color to be painted on all otherwise empty pixels.
     * @param color Background color.
     */
    public void setBackgroundColor(Color color){
        bgColor = color;
    }

    /**
     * Compile all drawings into one big image which is sent to the display.
     */
    public void updateVisuals(){
        if (display == null){
            return;
        }
        Color[][] visuals = new Color[display.getHeight()][display.getWidth()];
        // Set the background color everywhere.
        for (int y = 0; y < visuals.length; y++){
            for (int x = 0; x < visuals[0].length; x++){
                visuals[y][x] = bgColor;
            }
        }
        for (Drawing drawing : drawings){
            int drawingX = (int)drawing.getGameObject().xPos + drawing.xOffset;
            int drawingY = (int)drawing.getGameObject().yPos + drawing.yOffset;
            // Check if the drawing is even on the screen.
            if (Generic.checkOverlap(0, 0, visuals[0].length, visuals.length, drawingX, drawingY, drawing.visuals[0].length, drawing.visuals.length)){
                // Fill the pixels of the drawing into the big image.
                for(int y = 0; y < drawing.visuals.length; y++){
                    for(int x = 0; x < drawing.visuals[0].length; x++){
                        int mX = drawing.mirrored ? (drawing.visuals[y].length - x - 1) : x;
                        if (drawing.visuals[y][mX] != null && Generic.checkWithinBounds(drawingX + x, drawingY + y, visuals[0].length, visuals.length)){
                            visuals[drawingY + y][drawingX + x] = drawing.visuals[y][mX];
                        }
                    }
                }
            }
        }
        display.updateVisuals(visuals);
    }
}
