package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.processing.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.processing.IMovableProcessingService;

/**
 * SmallBulletControlSystem: Controls the bullet entities.
 */
public class SmallBulletControlSystem implements IEntityProcessingService, IMovableProcessingService, BulletSPI {

    private GameData gameData;
    private World world;

    @Override
    public void process(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;
        for (Entity bullet : world.getEntities(Bullet.class)) {
            moveEntity(bullet);
            removeOutOfWindowEntity(bullet);
        }
    }

    @Override
    public Entity createBullet(GameData gameData, Entity entity) {
        double[] bulletPolygon = {1.0001000000047497, 2.9602200984954834, 1.574170708656311, 4.346149206161499, 2.9601000547409058, 4.920220136642456, 4.346027612686157, 4.346151351928711, 4.920099973678589, 2.9602200984954834, 4.3460307121276855, 1.5742921829223633, 2.9600998163223267, 1.0002200985036325, 1.5741719603538513, 1.574289619922638};
        Bullet bullet = new Bullet(200, 3.5, 1, 2, bulletPolygon);

        double spawnDistanceFromCenter = (entity.getHeight() / 2) + 2;

        // Calculate the center of the entity
        // TODO: this is already a method in the entity class
        double centerX = entity.getXCoordinate() + entity.getWidth() / 2;
        double centerY = entity.getYCoordinate() + entity.getHeight() / 2;

        // Calculate the position of the bullet
        double rotationInRadians = Math.toRadians(entity.getRotation());
        double coordX = (centerX + Math.sin(rotationInRadians) * spawnDistanceFromCenter);
        double coordY = (centerY - Math.cos(rotationInRadians) * spawnDistanceFromCenter);

        // Set the position and rotation of the bullet
        bullet.setXCoordinate(coordX);
        bullet.setYCoordinate(coordY);
        bullet.setRotation(entity.getRotation());

        return bullet;
    }

    @Override
    public void moveEntity(Entity entity) {
        double changeX = Math.sin(Math.toRadians(entity.getRotation()));
        double changeY = Math.cos(Math.toRadians(entity.getRotation()));
        entity.setXCoordinate(entity.getXCoordinate() + changeX * 3.5);
        entity.setYCoordinate(entity.getYCoordinate() - changeY * 3.5);
    }

    @Override
    public void removeOutOfWindowEntity(Entity entity) {
        if (entity.getXCoordinate() < -5) {
            world.removeEntity(entity);
        } else if (entity.getXCoordinate() > gameData.getDisplayWidth() + 5) {
            world.removeEntity(entity);
        } else if (entity.getYCoordinate() < -5) {
            world.removeEntity(entity);
        } else if (entity.getYCoordinate() > gameData.getDisplayHeight() + 5) {
            world.removeEntity(entity);
        }
    }
}
