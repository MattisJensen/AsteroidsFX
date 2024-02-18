package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {
    private GameData gameData;
    private World world;
    private static long lastShotExecutionTime = 0;
    private int shotBlockTime = 400;
    private int randomInt = 1;

    @Override
    public void process(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;

        Random random = new Random();
        randomInt = random.nextInt(1, 20);

        for (Entity enemy : world.getEntities(Enemy.class)) {
            setShape(enemy);
            setOutOfWindowShip(enemy);
        }
    }

    private void setShape(Entity entity) {
        if (randomInt <= 2) {
            entity.setRotation(entity.getRotation() - 5);
        }
        if (randomInt <= 4 && randomInt > 2) {
            entity.setRotation(entity.getRotation() + 5);
        }

        long currentSystemTime = System.currentTimeMillis();
        if (currentSystemTime - lastShotExecutionTime > shotBlockTime && randomInt > 18) {
            lastShotExecutionTime = currentSystemTime;
            world.addEntity(getBulletSPIs().stream().findFirst().get().createBullet(entity, gameData));
        }

        double changeX = Math.sin(Math.toRadians(entity.getRotation()));
        double changeY = Math.cos(Math.toRadians(entity.getRotation()));

        if (randomInt > 14) {
            entity.setX(entity.getX() + (changeX * 2));
            entity.setY(entity.getY() - (changeY * 2));
        } else {
            entity.setX(entity.getX() + changeX);
            entity.setY(entity.getY() - changeY);
        }
    }

    private void setOutOfWindowShip(Entity entity) {
        if (entity.getX() < 30) {
            entity.setRotation(entity.getRotation() - 4);
            entity.setX(30);
        } else if (entity.getX() > gameData.getDisplayWidth() - 30) {
            entity.setRotation(entity.getRotation() - 4);
            entity.setX(gameData.getDisplayWidth() - 30);
        }

        if (entity.getY() < 30) {
            entity.setRotation(entity.getRotation() - 4);
            entity.setY(30);
        } else if (entity.getY() > gameData.getDisplayHeight() - 30) {
            entity.setRotation(entity.getRotation() - 4);
            entity.setY(gameData.getDisplayHeight() - 30);
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
