import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;

/**
 * The SpriteCollection class represents a collection of Sprite objects.
 * It provides methods to add sprites, notify all sprites that time has passed,
 * and draw all sprites on a given DrawSurface.
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * Constructs a new SpriteCollection.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Adds a sprite to the collection.
     *
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Notifies all sprites that time has passed, causing them to perform their actions.
     */
    public void notifyAllTimePassed() {
        for (Sprite s : sprites) {
            s.timePassed();
        }
    }

    /**
     * Draws all sprites on the given DrawSurface.
     *
     * @param d the DrawSurface to draw on
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : sprites) {
            s.drawOn(d);
        }
    }
}
