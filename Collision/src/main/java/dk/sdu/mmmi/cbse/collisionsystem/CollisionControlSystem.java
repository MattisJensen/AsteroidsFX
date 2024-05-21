package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.entityproperties.ICollidable;
import dk.sdu.mmmi.cbse.common.services.processing.IPostEntityProcessingService;

import java.util.Collection;
import java.util.HashMap;

/**
 * CollisionControlSystem: Detects collisions between entities and removes them if they are able to deal damage and take damage.
 */
public class CollisionControlSystem implements IPostEntityProcessingService {
    private Collection<Entity> entities;
    private HashMap<Entity, Entity> checkedEntities;

    @Override
    public void process(GameData gameData, World world) {
        this.entities = world.getEntities();
        this.checkedEntities = new HashMap<>();

        // only keep ICollidable entities
        this.entities.removeIf(entity -> !(entity instanceof ICollidable));

        for (Entity entity1 : this.entities) {
            for (Entity entity2 : this.entities) {
                // Skip of collision check if the entities have been checked already
                if (this.checkedEntities.containsKey(entity2) && this.checkedEntities.get(entity2) == entity1) {
                    continue;
                } else {
                    // Else add the new entities to the checked entities
                    this.checkedEntities.put(entity1, entity2);
                }

                // Skip of collision check if the entities are the same
                if (entity1 == entity2) {
                    continue;
                }

                // Apply collision if the entities are colliding
                if (isCollision(entity1, entity2)) {
                    ((ICollidable) entity1).collision(world, entity2);
                    ((ICollidable) entity2).collision(world, entity1);
                }
            }
        }
    }

    /**
     * Checks if two entities are colliding.
     *
     * @param entity1 The first entity.
     * @param entity2 The second entity.
     * @return True if the entities are colliding, false otherwise.
     */
    private boolean isCollision(Entity entity1, Entity entity2) {
        // distance between the two entities
        double distance = Math.sqrt(Math.pow(entity2.getCenterXCoordinate() - entity1.getCenterXCoordinate(), 2) + Math.pow(entity2.getCenterYCoordinate() - entity1.getCenterYCoordinate(), 2));

        // collision if the distance between the two entities is less than the sum of their radius
        return distance < (entity1.getWidth() / 2) + (entity2.getWidth() / 2);
    }
}