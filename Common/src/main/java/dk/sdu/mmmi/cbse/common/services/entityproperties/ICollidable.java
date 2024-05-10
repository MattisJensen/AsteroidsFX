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
     * @param entity The entity to collide with, which will be affected by the collision
     */
    void collision(World world, Entity entity);
}
