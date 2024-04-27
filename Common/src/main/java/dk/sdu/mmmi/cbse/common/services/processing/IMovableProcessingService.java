package dk.sdu.mmmi.cbse.common.services.processing;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 * Interface to ensure the processing of entities that can move.
 * Each method must be called by the corresponding ControlSystem processing service for each entity instance the ControlSystem is responsible for.
 */
public interface IMovableProcessingService {
    /**
     * Moves the entity's shape
     *
     * @param entity The entity to move
     */
    void moveEntity(Entity entity);

    /**
     * Removes entities if they are out of the window / screen
     *
     * @param entity The entity to remove
     */
    void removeOutOfWindowEntity(Entity entity);
}
