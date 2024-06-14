import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The Paddle class represents the player-controlled paddle in the game.
 * It implements both the Sprite and Collidable interfaces.
 */
public class Paddle implements Sprite, Collidable {

    private Rectangle rect;
    private java.awt.Color color;
    private biuoop.KeyboardSensor keyboard;
    private final int borderLeft = 20;
    private final int borderRight = 780;

    /**
     * Constructs a Paddle with the specified parameters.
     *
     * @param rect     the rectangle representing the paddle's shape
     * @param color    the color of the paddle
     * @param keyboard the keyboard sensor for controlling the paddle
     */
    public Paddle(Rectangle rect, java.awt.Color color, KeyboardSensor keyboard) {
        this.rect = rect;
        this.color = color;
        this.keyboard = keyboard;
    }

    /**
     * Moves the paddle to the left.
     */
    public void moveLeft() {
        this.rect = new Rectangle(new Point(this.rect.getUpperLeft().getX() - 5, this.rect.getUpperLeft().getY()),
                this.rect.getWidth(), this.rect.getHeight());
    }

    /**
     * Moves the paddle to the right.
     */
    public void moveRight() {
        this.rect = new Rectangle(new Point(this.rect.getUpperLeft().getX() + 5, this.rect.getUpperLeft().getY()),
                this.rect.getWidth(), this.rect.getHeight());
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth(), (int) this.rect.getHeight());
    }

    /**
     * Indicates that time has passed. Currently, this method moves the paddle
     * left or right based on the keyboard input.
     * It also prevents the paddle from moving outside the game screen.
     * If the paddle reaches the left or right border, it will "teleport" to the opposite border.
     * This method is called once per frame.
     */
    public void timePassed() {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
        if (this.rect.getUpperLeft().getX() < borderLeft) {
            this.rect = new Rectangle(new Point(borderRight - this.rect.getWidth(),
                    this.rect.getUpperLeft().getY()), this.rect.getWidth(),
                    this.rect.getHeight());
        }
        if (this.rect.getUpperLeft().getX() + this.rect.getWidth() > 780) {
            this.rect = new Rectangle(new Point(borderLeft, this.rect.getUpperLeft().getY()), this.rect.getWidth(),
                    this.rect.getHeight());
        }
    }

    /**
     * Returns the "collision shape" of the object.
     *
     * @return the rectangle representing the collision shape of the object
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * Notifies the object that a collision occurred at the specified point with the given velocity.
     * The method calculates and returns the new velocity after the collision, based on the force
     * the object inflicted.
     *
     * @param collisionPoint  the point at which the collision occurred
     * @param currentVelocity the current velocity of the object that collided with this object
     * @return the new velocity after the collision
     */

    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        double x = collisionPoint.getX();
        double regionWidth = (borderRight - borderLeft) / 5.0;
        double speed = Math.sqrt(dx * dx + dy * dy);

        // Determine which region the ball hit
        if (x >= borderLeft && x < borderLeft + regionWidth) {
            return Velocity.fromAngleAndSpeed(60, -speed); // Region 1
        } else if (x >= borderLeft + regionWidth && x < borderLeft + 2 * regionWidth) {
            return Velocity.fromAngleAndSpeed(30, -speed); // Region 2
        } else if (x >= borderLeft + 2 * regionWidth && x < borderLeft + 3 * regionWidth) {
            return new Velocity(dx, -dy); // Region 3 (middle region, no angle change)
        } else if (x >= borderLeft + 3 * regionWidth && x < borderLeft + 4 * regionWidth) {
            return Velocity.fromAngleAndSpeed(330, -speed); // Region 4
        } else if (x >= borderLeft + 4 * regionWidth && x <= borderRight) {
            return Velocity.fromAngleAndSpeed(300, -speed); // Region 5
        }
        // In case the collision point doesn't fall into any specific region (shouldn't happen)
        return new Velocity(dx, dy);
    }

    /**
     * Adds this paddle to the game.
     *
     * @param g the game to add the paddle to
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
}
