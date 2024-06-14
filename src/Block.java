import biuoop.DrawSurface;

/**
 * The Block class represents a block that is both a collidable and a sprite.
 * It is defined by a rectangle and a color.
 */
public class Block implements Collidable, Sprite {
    private Rectangle rect;
    private java.awt.Color color;

    /**
     * Constructs a Block object.
     *
     * @param rect  the rectangle defining the block's bounds
     * @param color the color of the block
     */
    public Block(Rectangle rect, java.awt.Color color) {
        this.rect = rect;
        this.color = color;
    }

    /**
     * Draws the block on the given DrawSurface.
     *
     * @param surface the DrawSurface to draw on
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth(), (int) this.rect.getHeight());
        surface.setColor(java.awt.Color.BLACK);
        surface.drawRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth(), (int) this.rect.getHeight());
    }

    /**
     * Indicates that time has passed. Currently, this method does nothing.
     */
    public void timePassed() {
        // do nothing
    }

    /**
     * Gets the rectangle defining the block's bounds.
     *
     * @return the rectangle of the block
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * Gets the color of the block.
     *
     * @return the color of the block
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Notifies the object that a collision occurred at the specified point with the given velocity.
     * The method returns the new velocity after the hit based on the collision's impact.
     *
     * @param collisionPoint  the point of collision
     * @param currentVelocity the current velocity
     * @return the new velocity after the collision
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        double x = collisionPoint.getX();
        double y = collisionPoint.getY();

        // Check for collision with vertical sides
        if (x == this.rect.getUpperLeft().getX() + this.rect.getWidth() || x == this.rect.getUpperLeft().getX()) {
            dx = -dx;
        }

        // Check for collision with horizontal sides
        if (y == this.rect.getUpperLeft().getY() + this.rect.getHeight() || y == this.rect.getUpperLeft().getY()) {
            dy = -dy;
        }
        return new Velocity(dx, dy);
    }

    /**
     * Adds the block to the game as both a collidable and a sprite.
     *
     * @param g the game to add the block to
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
}
