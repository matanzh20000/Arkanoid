import biuoop.DrawSurface;

/**
 * The Sprite interface represents an object that can be drawn on a DrawSurface and notified that time has passed.
 */
public interface Sprite {
    // draw the sprite to the screen

    /**
     * Draws the sprite on the given DrawSurface.
     *
     * @param d the DrawSurface to draw on.
     */
    void drawOn(DrawSurface d);

    /**
     * Indicates that time has passed.
     */
    // notify the sprite that time has passed
    void timePassed();

}