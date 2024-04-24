package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.ArrayList;
import java.util.Collection;

public class CollisionControlSystem implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        Collection<Entity> entities = world.getEntities();
        ArrayList<Entity> entitiesToRemove = new ArrayList<>();

        for (Entity entity1 : entities) {
            for (Entity entity2 : entities) {
                if (entity1 != entity2 && !entitiesToRemove.contains(entity1) && !entitiesToRemove.contains(entity2) && isCollision(entity1, entity2)) {
                    entity1.removeLivePoints(entity2.getCurrentDamage());
                    entity2.removeLivePoints(entity1.getCurrentDamage());

                    if (entity1.getLivePoints() <= 0) {
                        entitiesToRemove.add(entity1);
                    }

                    if (entity2.getLivePoints() <= 0) {
                        entitiesToRemove.add(entity2);
                    }
                }
            }
        }

        for (Entity entity : entitiesToRemove) {
            world.removeEntity(entity);
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
        double distance = Math.sqrt(Math.pow(e2.getCenterX() - e1.getCenterX(), 2) + Math.pow(e2.getCenterY() - e1.getCenterY(), 2));

        return distance < (e1.getWidth() / 2) + (e2.getWidth() / 2);
    }
}