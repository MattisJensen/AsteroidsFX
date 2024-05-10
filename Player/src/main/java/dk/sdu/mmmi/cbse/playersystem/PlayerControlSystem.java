package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.processing.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

/**
 * PlayerControlSystem: Controls the player entities
 */
public class PlayerControlSystem implements IEntityProcessingService {
    private GameData gameData;
    private World world;

    @Override
    public void process(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;

        for (Entity player : this.world.getEntities(Player.class)) {
            moveEntity((Player) player);
            shootIfPossible((Player) player);
            windowBoundaryInteraction(player);
        }
    }

    /**
     * Shoots a bullet if the player wants to shoot and is allowed to shoot
     *
     * @param player The player entity
     */
    public void shootIfPossible(Player player) {
        if (!this.gameData.getKeys().isDown(GameKeys.SPACE)) {
            return;
        }

        for (BulletSPI bulletSPI : getBulletSPIs()) {
            Entity bullet = bulletSPI.createBullet(this.gameData, player);
            if (player.isAllowedToShoot(400, System.currentTimeMillis())) {
                this.world.addEntity(bullet);
            }
        }
    }

    /**
     * Moves the entity's shape
     *
     * @param player The entity to move
     */
    public void moveEntity(Player player) {
        if (this.gameData.getKeys().isDown(GameKeys.LEFT)) {
            player.setRotation(player.getRotation() - 5);
        }

        if (this.gameData.getKeys().isDown(GameKeys.RIGHT)) {
            player.setRotation(player.getRotation() + 5);
        }

        double changeX = Math.sin(Math.toRadians(player.getRotation()));
        double changeY = Math.cos(Math.toRadians(player.getRotation()));

        if (this.gameData.getKeys().isDown(GameKeys.UP)) {
            player.setXCoordinate(player.getXCoordinate() + changeX * 2.3 * player.getMovingSpeed() * this.gameData.getDeltaTime());
            player.setYCoordinate(player.getYCoordinate() - changeY * 2.3 * player.getMovingSpeed() * this.gameData.getDeltaTime());
        } else {
            player.setXCoordinate(player.getXCoordinate() + changeX * player.getMovingSpeed() * this.gameData.getDeltaTime());
            player.setYCoordinate(player.getYCoordinate() - changeY * player.getMovingSpeed() * this.gameData.getDeltaTime());
        }
    }

    /**
     * Handles the interaction between the entity and the window boundaries.
     *
     * @param entity The entity to check
     */
    public void windowBoundaryInteraction(Entity entity) {
        if (entity.getXCoordinate() < 0) {
            entity.setXCoordinate(1);
        } else if (entity.getXCoordinate() > this.gameData.getDisplayWidth()) {
            entity.setXCoordinate(this.gameData.getDisplayWidth() - 1);
        }

        if (entity.getYCoordinate() < 0) {
            entity.setYCoordinate(1);
        } else if (entity.getYCoordinate() > this.gameData.getDisplayHeight()) {
            entity.setYCoordinate(this.gameData.getDisplayHeight() - 1);
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
