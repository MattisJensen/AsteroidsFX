package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.Collection;

public class CollisionControlSystem implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        Collection<Entity> entities = world.getEntities();
        for (Entity entity1 : entities) {
            for (Entity entity2 : entities) {
                if (entity1 != entity2 && isCollision(entity1, entity2)) {
                    world.removeEntity(entity1);
                    world.removeEntity(entity2);
                }
            }
        }
    }

    public boolean isCollision(Entity e1, Entity e2) {
        double x1 = e1.getX() + (e1.getWidth() / 2);
        double y1 = e1.getY() + (e1.getHeight() / 2);

        double x2 = e2.getX() + (e2.getWidth() / 2);
        double y2 = e2.getY() + (e2.getHeight() / 2);

        // sqrt( (x1 - x2)^2 - (y1 - y2)^2 )
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

        return distance < (e1.getWidth() / 2) + (e2.getWidth() / 2);
    }
}