package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    private GameData gameData;
    private World world;

    @Override
    public void process(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;
        for (Entity bullet : world.getEntities(Bullet.class)) {
            setShape(bullet);
        }
    }

    /**
     * Creates a bullet entity at the front side of the given entity.
     *
     * @param e The entity which creates the bullet.
     * @param gameData The game data.
     * @return The created bullet entity.
     */
    @Override
    public Entity createBullet(Entity e, GameData gameData) {
        Entity bullet = new Bullet();
        bullet.setPolygonCoordinates(0, 0, 3, 0, 3, 3, 0, 3);

        double distanceFromCenter = (e.getHeight() / 2) + 2;

        // Calculate the center of the entity
        double centerX = e.getX() + e.getWidth() / 2;
        double centerY = e.getY() + e.getHeight() / 2;

        // Calculate the position of the bullet
        double rotationInRadians = Math.toRadians(e.getRotation());
        double coordX = (centerX + Math.sin(rotationInRadians) * distanceFromCenter);
        double coordY = (centerY - Math.cos(rotationInRadians) * distanceFromCenter);

        // Set the position and rotation of the bullet
        bullet.setX(coordX);
        bullet.setY(coordY);
        bullet.setRotation(e.getRotation());

        return bullet;
    }

    private void setShape(Entity entity) {
        double changeX = Math.sin(Math.toRadians(entity.getRotation()));
        double changeY = Math.cos(Math.toRadians(entity.getRotation()));
        entity.setX(entity.getX() + changeX * 3.5);
        entity.setY(entity.getY() - changeY * 3.5);
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
