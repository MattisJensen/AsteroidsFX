package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            setShape(bullet, world, gameData);
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

    private void setShape(Entity entity, World world, GameData gameData) {
        double changeX = Math.cos(Math.toRadians(entity.getRotation()));
        double changeY = Math.sin(Math.toRadians(entity.getRotation()));
        entity.setX(entity.getX() + changeX * 3.5);
        entity.setY(entity.getY() + changeY * 3.5);

        removeOutOfWindowBullet(entity, world, gameData);
    }

    private void removeOutOfWindowBullet(Entity entity, World world, GameData gameData) {
        if (entity.getX() < 0) {
            world.removeEntity(entity);
        } else if (entity.getX() > gameData.getDisplayWidth()) {
            world.removeEntity(entity);
        } else if (entity.getY() < 0) {
            world.removeEntity(entity);
        } else if (entity.getY() > gameData.getDisplayHeight()) {
            world.removeEntity(entity);
        }
    }
}
