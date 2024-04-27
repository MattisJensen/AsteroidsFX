package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IWeapon;
import dk.sdu.mmmi.cbse.common.services.processing.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.processing.IMovableProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

/**
 * PlayerControlSystem: Controls the player entities
 */
public class PlayerControlSystem implements IEntityProcessingService, IMovableProcessingService {
    private GameData gameData;
    private World world;

    @Override
    public void process(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;

        for (Entity player : this.world.getEntities(Player.class)) {
            moveEntity(player);
            removeOutOfWindowEntity(player);
            shootIfPossible((Player) player);
        }
    }

    /**
     * Shoots a bullet if the player wants to shoot and is allowed to shoot
     *
     * @param player The player entity
     */
    public void shootIfPossible(Player player) {
        if (this.gameData.getKeys().isDown(GameKeys.SPACE)) {
                /*for (BulletSPI bulletSPI : getBulletSPIs()) {
                    Entity bullet = bulletSPI.createBullet(gameData, player);
                    if (bullet instanceof IWeapon && player.isAllowedToShoot(((IWeapon) bullet).getCooldown(), System.currentTimeMillis())) {
                        world.addEntity(bullet);
                    }
                    break; // Ensures that only one bullet is created per key press regardless of how many bulletSPIs are available
                }*/
            // TODO: Check this is workaround for the above by removing bullet module
            BulletSPI bulletSPI = getBulletSPIs().stream().findFirst().orElse(null);
            if (bulletSPI != null) {
                Entity bullet = bulletSPI.createBullet(this.gameData, player);
                if (bullet instanceof IWeapon && player.isAllowedToShoot(((IWeapon) bullet).getCooldown(), System.currentTimeMillis())) {
                    this.world.addEntity(bullet);
                }
            }
        }
    }

    @Override
    public void moveEntity(Entity entity) {
        if (this.gameData.getKeys().isDown(GameKeys.LEFT)) {
            entity.setRotation(entity.getRotation() - 5);
        }

        if (this.gameData.getKeys().isDown(GameKeys.RIGHT)) {
            entity.setRotation(entity.getRotation() + 5);
        }

        double changeX = Math.sin(Math.toRadians(entity.getRotation()));
        double changeY = Math.cos(Math.toRadians(entity.getRotation()));

        if (this.gameData.getKeys().isDown(GameKeys.UP)) {
            entity.setXCoordinate(entity.getXCoordinate() + (changeX * 2));
            entity.setYCoordinate(entity.getYCoordinate() - (changeY * 2));
        } else {
            entity.setXCoordinate(entity.getXCoordinate() + changeX / 2);
            entity.setYCoordinate(entity.getYCoordinate() - changeY / 2);
        }
    }

    @Override
    public void removeOutOfWindowEntity(Entity entity) {
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
