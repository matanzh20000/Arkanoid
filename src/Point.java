/**
 * The {@code Point} class represents a point in a 2D space with x and y coordinates.
 */
public class Point {
    private final double x;
    private final double y;

    /**
     * Constructs a new {@code Point} with the specified coordinates.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param other the point to which the distance is to be calculated
     * @return the distance between this point and the specified point
     */
    public double distance(Point other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }



    /**
     * @param other the point to be compared with this point
     * @return {@code true} if the specified point is equal to this point, {@code false} otherwise
     */
    public boolean equals(Point other) {
        double eps = 0.001;
        return (this.x - other.x < eps) && (this.y - other.y < eps);
    }

    /**
     * @return the x-coordinate of this point
     */
    public double getX() {
        return x;
    }

    /**
     * @return the y-coordinate of this point
     */
    public double getY() {
        return y;
    }
}
