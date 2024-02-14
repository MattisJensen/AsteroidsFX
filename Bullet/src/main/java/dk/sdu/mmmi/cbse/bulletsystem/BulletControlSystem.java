package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    GameData gameData;
    World world;

    @Override
    public void process(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;
        for (Entity bullet : world.getEntities(Bullet.class)) {
            setShape(bullet);
        }
    }

    @Override
    public Entity createBullet(Entity e, GameData gameData) {
        Entity bullet = new Bullet();
        bullet.setPolygonCoordinates(-1, -1, 1, -1, 1, 1, -1, 1);
        bullet.setX(e.getX());
        bullet.setY(e.getY());
        bullet.setRotation(e.getRotation());
        return bullet;
    }

    private void setShape(Entity entity) {
        double changeX = Math.cos(Math.toRadians(entity.getRotation()));
        double changeY = Math.sin(Math.toRadians(entity.getRotation()));
        entity.setX(entity.getX() + changeX * 3.5);
        entity.setY(entity.getY() + changeY * 3.5);

        removeOutOfWindowBullet(entity);
    }

    private void removeOutOfWindowBullet(Entity entity) {
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
