/**
 * The Collidable interface represents objects that can be collided with in the game.
 * Objects implementing this interface must provide methods to get their collision shape
 * and to handle collision events.
 */
public interface Collidable {

    /**
     * Returns the "collision shape" of the object.
     * This shape is used to detect collisions with other objects.
     *
     * @return the rectangle representing the collision shape of the object
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies the object that a collision occurred at the specified point with the given velocity.
     * The method calculates and returns the new velocity after the collision, based on the force
     * the object inflicted.
     *
     * @param collisionPoint  the point at which the collision occurred
     * @param currentVelocity the current velocity of the object that collided with this object
     * @return the new velocity after the collision
     */
    Velocity hit(Point collisionPoint, Velocity currentVelocity);
}

