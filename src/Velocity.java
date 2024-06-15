/**
 * The Velocity class specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructs a Velocity object.
     *
     * @param dx the change in x-coordinate
     * @param dy the change in y-coordinate
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Gets the change in x-coordinate.
     *
     * @return the change in x-coordinate
     */
    double getDx() {
        return this.dx;
    }

    /**
     * Gets the change in y-coordinate.
     *
     * @return the change in y-coordinate
     */
    double getDy() {
        return this.dy;
    }

    /**
     * Applies the velocity to a given point and returns the new point.
     *
     * @param p the point to which the velocity is applied
     * @return the new point with updated coordinates
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }

    /**
     * Creates a Velocity object from an angle and speed.
     *
     * @param angle the angle of the velocity in degrees
     * @param speed the speed of the velocity
     * @return the velocity object created from the angle and speed
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double rad = Math.toRadians(angle);
        double dy = Math.cos(rad) * speed;
        double dx = Math.sin(rad) * speed;
        return new Velocity(dx, dy);
    }
}
