package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IWeapon;
import dk.sdu.mmmi.cbse.common.services.processing.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.processing.IMovableProcessingService;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService, IMovableProcessingService {
    private GameData gameData;
    private World world;
    private static long lastShotExecutionTime = 0;
    private int shotBlockTime = 400;

    @Override
    public void process(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;

        for (Entity enemy : world.getEntities(Enemy.class)) {
            moveEntity(enemy);
            removeOutOfWindowEntity(enemy);
            shootIfPossible((Enemy) enemy);
        }
    }

    /**
     * Shoots a bullet if the enemy is allowed to shoot
     * Moreover the enemy shot is based on a random number
     *
     * @param enemy The enemy entity
     */
    public void shootIfPossible(Enemy enemy) {
        boolean chanceToShoot = (int) (Math.random() * 20) + 1 > 18; // 5% chance to shoot

        BulletSPI bulletSPI = getBulletSPIs().stream().findFirst().orElse(null);
        if (bulletSPI != null) {
            Entity bullet = bulletSPI.createBullet(gameData, enemy);
            if (bullet instanceof IWeapon
                    && enemy.isAllowedToShoot(((IWeapon) bullet).getCooldown(), System.currentTimeMillis())
                    && chanceToShoot) {
                world.addEntity(bullet);
            }
        }
    }

    @Override
    public void moveEntity(Entity entity) {
        Random random = new Random();
        int randomInt = random.nextInt(1, 20);

        if (randomInt <= 2) {
            entity.setRotation(entity.getRotation() - 5);
        }
        if (randomInt <= 4 && randomInt > 2) {
            entity.setRotation(entity.getRotation() + 5);
        }

        double changeX = Math.sin(Math.toRadians(entity.getRotation()));
        double changeY = Math.cos(Math.toRadians(entity.getRotation()));

        if (randomInt > 14) {
            entity.setXCoordinate(entity.getXCoordinate() + (changeX * 2));
            entity.setYCoordinate(entity.getYCoordinate() - (changeY * 2));
        } else {
            entity.setXCoordinate(entity.getXCoordinate() + changeX);
            entity.setYCoordinate(entity.getYCoordinate() - changeY);
        }
    }

    @Override
    public void removeOutOfWindowEntity(Entity entity) {
        if (entity.getXCoordinate() < 30) {
            entity.setRotation(entity.getRotation() - 4);
            entity.setXCoordinate(30);
        } else if (entity.getXCoordinate() > gameData.getDisplayWidth() - 30) {
            entity.setRotation(entity.getRotation() - 4);
            entity.setXCoordinate(gameData.getDisplayWidth() - 30);
        }

        if (entity.getYCoordinate() < 30) {
            entity.setRotation(entity.getRotation() - 4);
            entity.setYCoordinate(30);
        } else if (entity.getYCoordinate() > gameData.getDisplayHeight() - 30) {
            entity.setRotation(entity.getRotation() - 4);
            entity.setYCoordinate(gameData.getDisplayHeight() - 30);
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
