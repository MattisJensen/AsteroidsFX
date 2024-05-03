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
     * Get all entities in the world of a specific or multiple types
     *
     * @param entityTypes The types of entities to get
     * @return A list of entities of the specified types
     */
    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(e.getClass())) {
                    r.add(e);
                }
            }
        }
        return r;
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
