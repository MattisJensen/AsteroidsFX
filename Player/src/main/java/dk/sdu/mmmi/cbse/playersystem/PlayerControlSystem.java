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
    private static long lastShotExecutionTime = 0;
    private int shotBlockTime = 400;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            if (gameData.getKeys().isDown(GameKeys.LEFT)) player.setRotation(player.getRotation() - 5);
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) player.setRotation(player.getRotation() + 5);

            long currentSystemTime = System.currentTimeMillis();
            if (currentSystemTime - lastShotExecutionTime > shotBlockTime && gameData.getKeys().isDown(GameKeys.SPACE)) {
                lastShotExecutionTime = currentSystemTime;
                world.addEntity(getBulletSPIs().stream().findFirst().get().createBullet(player, gameData));
            }

            double changeX = Math.cos(Math.toRadians(player.getRotation()));
            double changeY = Math.sin(Math.toRadians(player.getRotation()));

            if (gameData.getKeys().isDown(GameKeys.UP)) {
                player.setX(player.getX() + (changeX * 2));
                player.setY(player.getY() + (changeY * 2));
            } else {
                player.setX(player.getX() + changeX);
                player.setY(player.getY() + changeY);
            }

            if (player.getX() < 0) player.setX(1);
            if (player.getX() > gameData.getDisplayWidth()) player.setX(gameData.getDisplayWidth() - 1);
            if (player.getY() < 0) player.setY(1);
            if (player.getY() > gameData.getDisplayHeight()) player.setY(gameData.getDisplayHeight() - 1);
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
