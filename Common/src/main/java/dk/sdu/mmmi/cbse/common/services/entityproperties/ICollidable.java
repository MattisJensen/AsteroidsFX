package dk.sdu.mmmi.cbse.common.services.entityproperties;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * Interface for handling collisions between entities
 */
public interface ICollidable {
    /**
     * Handles what happens to the given entity when a collision occurs with the current entity
     *
     * @param world  The world in which the collision of the entities occurs
     * @param entity The entity to collide with, which will be affected by the collision
     */
    void collision(World world, Entity entity);

    /**
     * Handles what happens to the given entity when it needs to be removed from the world due to collision.
     * This should be same world and entity as the one that was collided with in {@code collision}.
     *
     * @param world  The world in which the collision of the entities occurs
     * @param entity The entity to destroy
     */
    void collisionRemoval(World world, Entity entity);
}
