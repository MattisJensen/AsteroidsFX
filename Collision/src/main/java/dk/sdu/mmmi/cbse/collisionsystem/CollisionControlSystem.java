package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.entityproperties.ICollidable;
import dk.sdu.mmmi.cbse.common.services.processing.IPostEntityProcessingService;

import java.util.Collection;

/**
 * CollisionControlSystem: Detects collisions between entities and removes them if they are able to deal damage and take damage.
 */
public class CollisionControlSystem implements IPostEntityProcessingService {
    Collection<Entity> entities;

    @Override
    public void process(GameData gameData, World world) {
        this.entities = world.getEntities();

        for (Entity entity1 : this.entities) {
            if (entity1 instanceof ICollidable collidable1) {

                for (Entity entity2 : this.entities) {
                    // Skip if the entities are the same
                    if (entity1 == entity2) {
                        continue;
                    }

                    if (entity2 instanceof ICollidable collidable2) {
                        // Apply collision if the entities are colliding
                        if (isCollision(entity1, entity2)) {
                            collidable1.collision(world, entity2);
                            collidable2.collision(world, entity1);
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if two entities are colliding.
     *
     * @param e1 The first entity.
     * @param e2 The second entity.
     * @return True if the entities are colliding, false otherwise.
     */
    public boolean isCollision(Entity e1, Entity e2) {
        // distance between the two entities
        double distance = Math.sqrt(Math.pow(e2.getCenterXCoordinate() - e1.getCenterXCoordinate(), 2) + Math.pow(e2.getCenterYCoordinate() - e1.getCenterYCoordinate(), 2));

        return distance < (e1.getWidth() / 2) + (e2.getWidth() / 2);
    }
}