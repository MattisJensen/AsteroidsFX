package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {
    private static long lastShotExecutionTime = 0;
    private long shotBlockTime = 400;

    @Override
    public void process(GameData gameData, World world) {
        long currentSystemTime = System.currentTimeMillis();
        if (currentSystemTime - lastShotExecutionTime > shotBlockTime && gameData.getKeys().isDown(GameKeys.SPACE))  {
            lastShotExecutionTime = currentSystemTime;

            Collection<Entity> entities = world.getEntities();
            Entity newBullet = null;

            for (Entity entity : entities) {
                if (entity.getClass() != Bullet.class) {
                    Entity shooter = createBullet(entity, gameData);
                    newBullet = shooter;
                    break;
                }
            }

            if (newBullet != null) {
                world.addEntity(newBullet);
            }
        }

        for (Entity bullet : world.getEntities(Bullet.class)) {
            double changeX = Math.cos(Math.toRadians(bullet.getRotation()));
            double changeY = Math.sin(Math.toRadians(bullet.getRotation()));
            bullet.setX(bullet.getX() + changeX * 3.5);
            bullet.setY(bullet.getY() + changeY * 3.5);

            if (bullet.getX() < 0) {
                world.removeEntity(bullet);
            } else if (bullet.getX() > gameData.getDisplayWidth()) {
                world.removeEntity(bullet);
            } else if (bullet.getY() < 0) {
                world.removeEntity(bullet);
            } else if (bullet.getY() > gameData.getDisplayHeight()) {
                world.removeEntity(bullet);
            }
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity bullet = new Bullet();
        bullet.setPolygonCoordinates(-2, -2, 2, -2, 2, 2, -2, 2);
        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());
        bullet.setRotation(shooter.getRotation());
        return bullet;
    }

    private void setShape(Entity entity) {
    }

}
