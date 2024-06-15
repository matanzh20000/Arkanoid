import biuoop.DrawSurface;

/**
 * The Ball class represents a ball with a center point, radius, color, and velocity.
 */
public class Ball implements Sprite {
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity v;
    private GameEnvironment gameEnvironment;

    /**
     * Constructs a Ball object.
     *
     * @param center the center point of the ball
     * @param r      the radius of the ball
     * @param color  the color of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
    }

    /**
     * Constructs a Ball object.
     *
     * @param dx    the x-coordinate of the center point
     * @param dy    the y-coordinate of the center point
     * @param r     the radius of the ball
     * @param color the color of the ball
     */
    public Ball(double dx, double dy, int r, java.awt.Color color) {
        this.center = new Point(dx, dy);
        this.r = r;
        this.color = color;
    }

    /**
     * Constructs a Ball object with a game environment.
     *
     * @param dx               the x-coordinate of the center point
     * @param dy               the y-coordinate of the center point
     * @param r                the radius of the ball
     * @param color            the color of the ball
     * @param gameEnvironment  the game environment of the ball
     */
    public Ball(double dx, double dy, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        this.center = new Point(dx, dy);
        this.r = r;
        this.color = color;
        this.gameEnvironment = gameEnvironment;
    }

    /**
     * Gets the x-coordinate of the center point.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Gets the y-coordinate of the center point.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Gets the radius of the ball.
     *
     * @return the radius
     */
    public int getSize() {
        return this.r;
    }

    /**
     * Gets the center point of the ball.
     *
     * @return the center point
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * Gets the color of the ball.
     *
     * @return the color
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Draws the ball on the given DrawSurface.
     *
     * @param surface the DrawSurface to draw on
     */
    public void drawOn(DrawSurface surface) {
        int x = getX();
        int y = getY();
        int r = getSize();
        surface.setColor(getColor());
        surface.fillCircle(x, y, r);
    }

    @Override
    public void timePassed() {
        moveOneStep(this.gameEnvironment);
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param v the velocity
     */
    public void setVelocity(Velocity v) {
        this.v = v;
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param dx the change in x-coordinate
     * @param dy the change in y-coordinate
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }

    /**
     * Gets the velocity of the ball.
     *
     * @return the velocity
     */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * Moves the ball one step according to its velocity and handles collisions.
     *
     * @param environment the game environment containing collidable objects
     */
    public void moveOneStep(GameEnvironment environment) {
        double dx = v.getDx();
        double dy = v.getDy();
        Point nextPositionNoCollision = new Point(center.getX() + dx, center.getY() + dy);

        // Extend the trajectory line by the radius in the direction of the velocity
        Point extendedNextPosition = new Point(
                center.getX() + dx + r * (dx / Math.abs(dx)),
                center.getY() + dy + r * (dy / Math.abs(dy))
        );

        // Create a trajectory line from the current center to the extended position
        Line trajectory = new Line(center, extendedNextPosition);

        CollisionInfo collisionInfo = environment.getClosestCollision(trajectory);
        if (collisionInfo == null) {
            // No collision, move to end of trajectory
            this.center = nextPositionNoCollision;
        } else {
            // Collision detected
            Point collisionPoint = collisionInfo.collisionPoint();
            Collidable collisionObject = collisionInfo.collisionObject();

            // Move to just before the collision point
            double distanceToCollision = center.distance(collisionPoint);
            double distanceToMove = distanceToCollision - this.r; // Subtract the ball's radius
            double fraction = distanceToMove / center.distance(extendedNextPosition);

            // Ensure fraction is within valid range
            if (fraction < 0) {
                fraction = 0;
            }
            if (fraction > 1) {
                fraction = 1;
            }

            // Move to the almost collision point
            this.center = trajectory.getPointAtFraction(fraction);

            // Update velocity after collision
            this.v = collisionObject.hit(collisionPoint, v);

            // Move the ball slightly with the new velocity to avoid sticking
            this.center = this.v.applyToPoint(this.center);
        }
    }

    /**
     * Moves the ball one step within a defined boundary (50,50) to (500,500).
     * Reverses direction if the ball hits the boundary.
     */
    public void moveOneStepWithin() {
        double dx = v.getDx();
        double dy = v.getDy();
        double newVX = center.getX() + dx;
        double newVY = center.getY() + dy;
        if (newVX > 500 - this.r || newVX < 50 + this.r) {
            dx = -dx;
        }
        if (newVY > 500 - this.r || newVY < 50 + this.r) {
            dy = -dy;
        }
        setVelocity(dx, dy);
        this.center = this.getVelocity().applyToPoint(this.center);
    }

    /**
     * Checks and reverses the velocity if the ball hits the boundaries of the surface.
     *
     * @param width  the width of the surface
     * @param height the height of the surface
     */
    public void checkAndReverseVelocity(int width, int height) {
        if (this.getX() - this.r < 0 || this.getX() + this.r >= width) {
            this.v = new Velocity(-v.getDx(), v.getDy());
        }
        if (this.getY() - this.r <= 0 || this.getY() + this.r >= height) {
            this.v = new Velocity(v.getDx(), -v.getDy());
        }
    }

    /**
     * Checks if the ball is inside a rectangle defined by its start and end points.
     *
     * @param startX the starting x-coordinate of the rectangle
     * @param startY the starting y-coordinate of the rectangle
     * @param endX   the ending x-coordinate of the rectangle
     * @param endY   the ending y-coordinate of the rectangle
     * @return true if the ball is inside the rectangle, false otherwise
     */
    public boolean isInsideRectangle(int startX, int startY, int endX, int endY) {
        return (this.getX() - this.getSize() <= startX + endX) && (this.getX() + this.getSize() >= startX)
                && (this.getY() - this.getSize() <= startY + endY) && (this.getY() + this.getSize() >= startY);
    }

    /**
     * Moves the ball one step according to its velocity.
     */
    public void moveOneStepOutside() {
        this.center = this.getVelocity().applyToPoint(this.center);
    }

    /**
     * Adds the ball to the game as a sprite.
     *
     * @param game the game to add the ball to
     */
    public void addToGame(Game game) {
        game.addSprite(this);
    }
}
