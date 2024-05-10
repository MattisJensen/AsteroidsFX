package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.processing.IEntityProcessingService;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {
    private GameData gameData;
    private World world;

    @Override
    public void process(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;

        for (Entity enemy : this.world.getEntities(Enemy.class)) {
            moveEntity((Enemy) enemy);
            windowBoundaryInteraction(enemy);
            shootIfPossible((Enemy) enemy);
            rotateIfEntityInRange((Enemy) enemy);
        }
    }

    /**
     * Shoots a bullet if the enemy is allowed to shoot
     * Moreover the enemy shot is based on a random number
     *
     *
     * @param enemy The enemy entity
     */
    public void shootIfPossible(Enemy enemy) {
        // If random number is higher than the chance to shoot, the enemy will not shoot
        if (Math.random() > enemy.getChanceToShoot()) {
            return;
        }

        for (BulletSPI bulletSPI : getBulletSPIs()) {
            Entity bullet = bulletSPI.createBullet(this.gameData, enemy);
            if (enemy.isAllowedToShoot(400, System.currentTimeMillis())) {
                this.world.addEntity(bullet);
            }
            break;
        }
    }

    /**
     * Moves the entity's shape
     *
     * @param enemy The entity to move
     */
    public void moveEntity(Enemy enemy) {
        Random random = new Random();
        int randomInt = random.nextInt(1, 20);

        if (randomInt <= 2) {
            enemy.setRotation(enemy.getRotation() - 5);
        }
        if (randomInt <= 4 && randomInt > 2) {
            enemy.setRotation(enemy.getRotation() + 5);
        }

        double changeX = Math.sin(Math.toRadians(enemy.getRotation()));
        double changeY = Math.cos(Math.toRadians(enemy.getRotation()));

        if (randomInt > 14) {
            enemy.setXCoordinate(enemy.getXCoordinate() + changeX * 2.3 * enemy.getMovingSpeed() * this.gameData.getDeltaTime());
            enemy.setYCoordinate(enemy.getYCoordinate() - changeY * 2.3 * enemy.getMovingSpeed() * this.gameData.getDeltaTime());
        } else {
            enemy.setXCoordinate(enemy.getXCoordinate() + changeX * enemy.getMovingSpeed() * this.gameData.getDeltaTime());
            enemy.setYCoordinate(enemy.getYCoordinate() - changeY * enemy.getMovingSpeed() * this.gameData.getDeltaTime());
        }
    }

    /**
     * Rotates the enemy if an entity is in range
     *
     * @param enemy The enemy entity which should rotate
     */
    public void rotateIfEntityInRange(Enemy enemy) {
        for (Entity entity : this.world.getEntities()) {
            if (entity != enemy && distanceBetweenEntities(enemy, entity) <= 16) {
                enemy.setRotation(enemy.getRotation() + 6);
                break;
            }
        }
    }

    /**
     * Calculates the distance between two entities
     *
     * @param entity1 The first entity
     * @param entity2 The second entity
     * @return The distance between the two entities
     */
    private double distanceBetweenEntities(Entity entity1, Entity entity2) {
        double distanceX = entity1.getXCoordinate() - entity2.getXCoordinate(); // x1 - x2
        double distanceY = entity1.getYCoordinate() - entity2.getYCoordinate(); // y1 - y2
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY); // sqrt((x1 - x2)^2 + (y1 - y2)^2)
    }

    /**
     * Handles the interaction between the entity and the window boundaries.
     *
     * @param entity The entity to check
     */
    public void windowBoundaryInteraction(Entity entity) {
        if (entity.getXCoordinate() < 30) {
            entity.setRotation(entity.getRotation() - 8);
            entity.setXCoordinate(30);
        } else if (entity.getXCoordinate() > this.gameData.getDisplayWidth() - 60) {
            entity.setRotation(entity.getRotation() - 8);
            entity.setXCoordinate(this.gameData.getDisplayWidth() - 60);
        }

        if (entity.getYCoordinate() < 30) {
            entity.setRotation(entity.getRotation() - 8);
            entity.setYCoordinate(30);
        } else if (entity.getYCoordinate() > this.gameData.getDisplayHeight() - 60) {
            entity.setRotation(entity.getRotation() - 8);
            entity.setYCoordinate(this.gameData.getDisplayHeight() - 60);
        }
    }

    /**
     * Returns a collection of all available BulletSPIs
     *
     * @return A collection of all available BulletSPIs
     */
    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
