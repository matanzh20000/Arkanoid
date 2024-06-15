import java.util.ArrayList;
import java.util.List;

/**
 * The Rectangle class represents a rectangle defined by its upper-left corner, width, and height.
 * It provides methods to get its properties and calculate intersection points with a line.
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * Constructs a Rectangle with the specified upper-left corner, width, and height.
     *
     * @param upperLeft the upper-left corner of the rectangle
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the upper-left corner of the rectangle.
     *
     * @return the upper-left corner of the rectangle
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return the width of the rectangle
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return the height of the rectangle
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns a list of intersection points between the rectangle and a specified line.
     * If there are no intersection points, the list will be empty.
     *
     * @param line the line to check for intersections with the rectangle
     * @return a list of intersection points between the rectangle and the line
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPoints = new ArrayList<>();

        // Define the edges of the rectangle
        Line[] edges = {
                new Line(upperLeft, new Point(upperLeft.getX() + width, upperLeft.getY())), // Top edge
                new Line(new Point(upperLeft.getX() + width, upperLeft.getY()),
                        new Point(upperLeft.getX() + width, upperLeft.getY() + height)), // Right edge
                new Line(new Point(upperLeft.getX(), upperLeft.getY() + height),
                        new Point(upperLeft.getX() + width, upperLeft.getY() + height)), // Bottom edge
                new Line(upperLeft, new Point(upperLeft.getX(), upperLeft.getY() + height)) // Left edge
        };

        // Check intersections with each edge
        for (Line edge : edges) {
            Point intersection = line.intersectionWith(edge);
            if (intersection != null) {
                intersectionPoints.add(intersection);
            }
        }

        return intersectionPoints;
    }
}
