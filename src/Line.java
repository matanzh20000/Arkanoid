import java.util.List;

/**
 * The Line class represents a line segment defined by two points in a 2D space.
 * It provides methods to calculate the length, middle point, and intersection
 * with other line segments.
 */
public class Line {
    private final Point start;
    private final Point end;

    /**
     * Constructs a Line given the start and end points.
     *
     * @param start the starting point of the line
     * @param end   the ending point of the line
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs a Line given the coordinates of the start and end points.
     *
     * @param x1 the x-coordinate of the starting point
     * @param y1 the y-coordinate of the starting point
     * @param x2 the x-coordinate of the ending point
     * @param y2 the y-coordinate of the ending point
     */
    public Line(double x1, double y1, double x2, double y2) {
        start = new Point(x1, y1);
        end = new Point(x2, y2);
    }

    /**
     * Constructs a Line given a point and the coordinates of the end point.
     *
     * @param point the starting point of the line
     * @param x     the x-coordinate of the ending point
     * @param y     the y-coordinate of the ending point
     */

    public Line(Point point, double x, double y) {
        this.start = point;
        this.end = new Point(x, y);
    }

    /**
     * Returns the length of the line.
     *
     * @return the length of the line
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Returns the middle point of the line.
     *
     * @return the middle point of the line
     */
    public Point middle() {
        return new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    /**
     * Returns the starting point of the line.
     *
     * @return the starting point of the line
     */
    public Point start() {
        return start;
    }

    /**
     * Returns the ending point of the line.
     *
     * @return the ending point of the line
     */
    public Point end() {
        return end;
    }

    /**
     * Checks if a point q lies on the segment defined by points s and v.
     *
     * @param s the start point of the segment
     * @param q the point to check
     * @param v the end point of the segment
     * @return true if q lies on the segment sv, false otherwise
     */
    private boolean onSegment(Point s, Point q, Point v) {
        return q.getX() <= Math.max(s.getX(), v.getX()) && q.getX() >= Math.min(s.getX(), v.getX())
                && q.getY() <= Math.max(s.getY(), v.getY()) && q.getY() >= Math.min(s.getY(), v.getY());
    }

    /**
     * Determines the orientation of the triplet (s, t, v).
     *
     * @param s the first point
     * @param t the second point
     * @param v the third point
     * @return 0 if collinear, 1 if clockwise, 2 if counterclockwise
     */
    public int orientation(Point s, Point t, Point v) {
        double value = (s.getY() - t.getY()) * (v.getX() - t.getX()) - (v.getY() - t.getY()) * (s.getX() - t.getX());
        if (value == 0) {
            return 0;
        }
        return (value > 0) ? 1 : 2;
    }

    /**
     * Checks if two line segments intersect.
     *
     * @param start1 the starting point of the first segment
     * @param end1   the ending point of the first segment
     * @param start2 the starting point of the second segment
     * @param end2   the ending point of the second segment
     * @return true if the segments intersect, false otherwise
     */
    private boolean doesIntersect(Point start1, Point end1, Point start2, Point end2) {
        int o1 = orientation(start1, end1, start2);
        int o2 = orientation(start1, end1, end2);
        int o3 = orientation(start2, end2, start1);
        int o4 = orientation(start2, end2, end1);

        if (o1 != o2 && o3 != o4) {
            return true;
        }

        if (o1 == 0 && onSegment(start1, start2, end1)) {
            return true;
        }
        if (o2 == 0 && onSegment(start1, end2, end1)) {
            return true;
        }
        if (o3 == 0 && onSegment(start2, start1, end2)) {
            return true;
        }
        if (o4 == 0 && onSegment(start2, end1, end2)) {
            return true;
        }

        return false;
    }

    /**
     * Checks if this line intersects with another line.
     *
     * @param other the other line
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        return doesIntersect(this.start, this.end, other.start, other.end);
    }

    /**
     * Checks if this line intersects with two other lines.
     *
     * @param other1 the first other line
     * @param other2 the second other line
     * @return true if this line intersects with both other lines, false otherwise
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return doesIntersect(this.start, this.end, other1.start, other1.end)
                && doesIntersect(this.start, this.end, other2.start, other2.end);
    }

    /**
     * Finds the intersection point of two line segments using cross multiplication
     * and determinant calculation.
     *
     * @param start1 the starting point of the first segment
     * @param end1   the ending point of the first segment
     * @param start2 the starting point of the second segment
     * @param end2   the ending point of the second segment
     * @return the intersection point, or null if the segments do not intersect or are parallel
     */
    public Point findIntersection(Point start1, Point end1, Point start2, Point end2) {
        double a1 = end1.getY() - start1.getY();
        double b1 = start1.getX() - end1.getX();
        double c1 = a1 * start1.getX() + b1 * start1.getY();

        double a2 = start2.getY() - end2.getY();
        double b2 = end2.getX() - start2.getX();
        double c2 = a2 * start2.getX() + b2 * start2.getY();

        double determinant = a1 * b2 - a2 * b1;

        if (determinant == 0) {
            return null;
        } else {
            double x = (b2 * c1 - b1 * c2) / determinant;
            double y = (a1 * c2 - a2 * c1) / determinant;

            return new Point(x, y);
        }
    }

    /**
     * Finds the intersection point with another line.
     *
     * @param other the other line
     * @return the intersection point, or null if the lines do not intersect
     */
    public Point intersectionWith(Line other) {
        if (isIntersecting(other)) {
            return findIntersection(start, end, other.start, other.end);
        } else {
            return null;
        }
    }

    /**
     * Checks if this line is equal to another line.
     *
     * @param other the other line
     * @return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        return (this.start.equals(other.start) && this.end.equals(other.end))
                ||
                (this.start.equals(other.end) && this.end.equals(other.start));
    }

    /**
     * Returns the point at a given fraction of the line.
     *
     * @param fraction the fraction of the line
     * @return the point at the given fraction of the line
     */

    public Point getPointAtFraction(double fraction) {
        double x = start.getX() + fraction * (end.getX() - start.getX());
        double y = start.getY() + fraction * (end.getY() - start.getY());
        return new Point(x, y);
    }

    /**
     * Finds the closest intersection point to the start of the line with a given rectangle.
     *
     * @param rect the rectangle to check for intersection
     * @return the closest intersection point, or null if no intersection occurs
     */

    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersectionPoints = rect.intersectionPoints(this);
        if (intersectionPoints.isEmpty()) {
            return null;
        }
        Point closest = intersectionPoints.get(0);
        double minDistance = start.distance(closest);
        for (Point point : intersectionPoints) {
            double distance = start.distance(point);
            if (distance < minDistance) {
                closest = point;
                minDistance = distance;
            }
        }
        return closest;
    }
}
