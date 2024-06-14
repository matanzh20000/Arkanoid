import java.util.ArrayList;
import java.util.List;

public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    public Point getUpperLeft() {
        return upperLeft;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPoints = new ArrayList<>();

        // Define the edges of the rectangle
        Line[] edges = {
                new Line(upperLeft, new Point(upperLeft.getX() + width, upperLeft.getY())), // Top edge
                new Line(new Point(upperLeft.getX() + width, upperLeft.getY()), new Point(upperLeft.getX() + width, upperLeft.getY() + height)), // Right edge
                new Line(new Point(upperLeft.getX(), upperLeft.getY() + height), new Point(upperLeft.getX() + width, upperLeft.getY() + height)), // Bottom edge
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