import java.util.ArrayList;
import java.util.List;

/**
 * The GameEnvironment class represents the environment of the game, containing collidable objects.
 * It manages a list of collidables and provides methods for adding collidable objects and detecting collisions.
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * Constructs a new GameEnvironment object with an empty list of collidables.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Adds a collidable object to the game environment.
     *
     * @param c the collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Finds the closest collision point between a trajectory line and any collidable object in the environment.
     *
     * @param trajectory the trajectory line to check for collisions
     * @return the collision information of the closest collision, or null if no collision occurs
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo closestCollision = null;
        double closestDistance = Double.MAX_VALUE;

        // Iterate over all collidable objects
        for (Collidable collidable : collidables) {
            // Get the collision rectangle of the collidable object
            Rectangle rect = collidable.getCollisionRectangle();

            // Find the closest intersection point between the trajectory line and the collision rectangle
            Point closestPoint = trajectory.closestIntersectionToStartOfLine(rect);

            // If an intersection point is found
            if (closestPoint != null) {
                // Calculate the distance between the start point of the trajectory line and the intersection point
                double distance = trajectory.start().distance(closestPoint);

                // Update the closest collision information if the distance is smaller than the current closest distance
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestCollision = new CollisionInfo(closestPoint, collidable);
                }
            }
        }
        return closestCollision;
    }
}
