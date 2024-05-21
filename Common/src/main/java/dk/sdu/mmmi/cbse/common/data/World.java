package dk.sdu.mmmi.cbse.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * World: Contains all entities in the game
 */
public class World {
    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();

    /**
     * Add an entity to the world
     *
     * @param entity The entity to add
     * @return The ID of the entity
     */
    public String addEntity(Entity entity) {
        this.entityMap.put(entity.getID(), entity);
        return entity.getID();
    }

    /**
     * Remove an entity from the world based on its ID
     *
     * @param entityID The ID of the entity to remove
     */
    public void removeEntity(String entityID) {
        this.entityMap.remove(entityID);
    }

    /**
     * Remove an entity from the world based on the entity instance
     *
     * @param entity The entity to remove
     */
    public void removeEntity(Entity entity) {
        this.entityMap.remove(entity.getID());
    }

    /**
     * Get all entities in the world
     *
     * @return A collection of all entities in the world
     */
    public Collection<Entity> getEntities() {
        return this.entityMap.values();
    }

    /**
     * Get all entities of a specific type in the world.
     *
     * @param entityType The type of entity to get from the world
     * @return A list with entities of the specified type
     */
    public <E extends Entity> List<Entity> getEntities(Class<E>... entityType) {
        List<Entity> requestedEntities = new ArrayList<>();
        for (Entity entity : getEntities()) {
            for (Class<E> requestedEntityType : entityType) {
                if (requestedEntityType.equals(entity.getClass())) {
                    requestedEntities.add(entity);
                }
            }
        }
        return requestedEntities;
    }

    /**
     * Gets a specific entity from the world based on its ID
     *
     * @param ID The ID of the entity to get
     * @return The entity with the specified ID
     */
    public Entity getEntity(String ID) {
        return this.entityMap.get(ID);
    }

}
