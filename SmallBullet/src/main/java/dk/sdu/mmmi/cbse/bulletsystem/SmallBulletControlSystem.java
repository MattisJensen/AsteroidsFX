package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.CustomColor;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.processing.IEntityProcessingService;

/**
 * SmallBulletControlSystem: Controls the bullet entities.
 */
public class SmallBulletControlSystem implements IEntityProcessingService, BulletSPI {
    private GameData gameData;
    private World world;

    @Override
    public void process(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;
        for (Entity bullet : this.world.getEntities(Bullet.class)) {
            moveEntity((Bullet) bullet);
            windowBoundaryInteraction(bullet);
        }
    }

    @Override
    public Entity createBullet(GameData gameData, Entity shooter) {
        double[] bulletPolygon = {1.0001000000047497, 2.9602200984954834, 1.574170708656311, 4.346149206161499, 2.9601000547409058, 4.920220136642456, 4.346027612686157, 4.346151351928711, 4.920099973678589, 2.9602200984954834, 4.3460307121276855, 1.5742921829223633, 2.9600998163223267, 1.0002200985036325, 1.5741719603538513, 1.574289619922638};
        CustomColor bulletColor = new CustomColor(188, 66, 202);
        Bullet bullet = new Bullet(shooter, 200, 250, 1, 100, bulletColor, bulletPolygon);

        double spawnDistanceFromCenter = (shooter.getHeight() / 2) + 2;

        // Calculate the center of the shooter
        double centerX = shooter.getCenterXCoordinate();
        double centerY = shooter.getCenterYCoordinate();

        // Calculate the position of the bullet
        double rotationInRadians = Math.toRadians(shooter.getRotation());
        double coordX = (centerX + Math.sin(rotationInRadians) * spawnDistanceFromCenter);
        double coordY = (centerY - Math.cos(rotationInRadians) * spawnDistanceFromCenter);

        // Set the position and rotation of the bullet
        bullet.setXCoordinate(coordX);
        bullet.setYCoordinate(coordY);
        bullet.setRotation(shooter.getRotation());

        return bullet;
    }

    /**
     * Moves the entity's shape
     *
     * @param bullet The entity to move
     */
    public void moveEntity(Bullet bullet) {
        double changeX = Math.sin(Math.toRadians(bullet.getRotation()));
        double changeY = Math.cos(Math.toRadians(bullet.getRotation()));
        bullet.setXCoordinate(bullet.getXCoordinate() + changeX * bullet.getMovingSpeed() * this.gameData.getDeltaTime());
        bullet.setYCoordinate(bullet.getYCoordinate() - changeY * bullet.getMovingSpeed() * this.gameData.getDeltaTime());
    }

    /**
     * Handles the interaction between the entity and the window boundaries.
     *
     * @param entity The entity to check
     */
    public void windowBoundaryInteraction(Entity entity) {
        if (entity.getXCoordinate() < -5) {
            this.world.removeEntity(entity);
        } else if (entity.getXCoordinate() > this.gameData.getDisplayWidth() + 5) {
            this.world.removeEntity(entity);
        } else if (entity.getYCoordinate() < -5) {
            this.world.removeEntity(entity);
        } else if (entity.getYCoordinate() > this.gameData.getDisplayHeight() + 5) {
            this.world.removeEntity(entity);
        }
    }
}
