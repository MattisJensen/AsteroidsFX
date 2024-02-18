package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class PlayerControlSystem implements IEntityProcessingService {
    private GameData gameData;
    private World world;
    private static long lastShotExecutionTime = 0;
    private final int SHOT_COOLDOWN = 300;

    @Override
    public void process(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;

        for (Entity player : world.getEntities(Player.class)) {
            setShape(player);
            setOutOfWindowShip(player);
        }
    }

    private void setShape(Entity entity) {
        if (gameData.getKeys().isDown(GameKeys.LEFT)) {
            entity.setRotation(entity.getRotation() - 5);
        }

        if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
            entity.setRotation(entity.getRotation() + 5);
        }

        long currentSystemTime = System.currentTimeMillis();
        if (currentSystemTime - lastShotExecutionTime > SHOT_COOLDOWN && gameData.getKeys().isDown(GameKeys.SPACE)) {
            lastShotExecutionTime = currentSystemTime;
            world.addEntity(getBulletSPIs().stream().findFirst().get().createBullet(entity, gameData));
        }

        double changeX = Math.sin(Math.toRadians(entity.getRotation()));
        double changeY = Math.cos(Math.toRadians(entity.getRotation()));

        if (gameData.getKeys().isDown(GameKeys.UP)) {
            entity.setX(entity.getX() + (changeX * 2));
            entity.setY(entity.getY() - (changeY * 2));
        } else {
            entity.setX(entity.getX() + changeX);
            entity.setY(entity.getY() - changeY);
        }
    }

    private void setOutOfWindowShip(Entity entity) {
        if (entity.getX() < 0) {
            entity.setX(1);
        } else if (entity.getX() > gameData.getDisplayWidth()) {
            entity.setX(gameData.getDisplayWidth() - 1);
        }

        if (entity.getY() < 0) {
            entity.setY(1);
        } else if (entity.getY() > gameData.getDisplayHeight()) {
            entity.setY(gameData.getDisplayHeight() - 1);
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
