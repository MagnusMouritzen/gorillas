package unibanden.ist.gameengine;

/**
 * Responsible: Magnus Mouritzen s214931
 * Useful methods that aren't specifically tied to anything else.
 */
public class Generic {
    /**
     * Check if a given position is within the given width and height.
     * @param x x position to check
     * @param y y position to check
     * @param width The width which x has to be less than.
     * @param height The height that y has to be less than.
     * @return true if x is within width and y is within height.
     */
    public static boolean checkWithinBounds(int x, int y, int width, int height){
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Check if the given rectangles overlap.
     * @param x1 x position of first rectangle
     * @param y1 y position of first rectangle
     * @param w1 width of first rectangle
     * @param h1 height of first rectangle
     * @param x2 x position of second rectangle
     * @param y2 y position of second rectangle
     * @param w2 width of second rectangle
     * @param h2 height of second rectangle
     * @return true if the rectangles have any overlap.
     */
    public static boolean checkOverlap(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2){
        return x1 + w1 > x2 && x2 + w2 > x1 && y1 + h1 > y2 && y2 + h2 > y1;
    }
}
