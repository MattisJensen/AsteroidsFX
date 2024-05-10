package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.CustomColor;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.entityproperties.ICollidable;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IDestroyable;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IMoveable;

/**
 * Enemy: An enemy entity which can move, be destroyed, deal damage and use weapons
 */
public class Enemy extends Entity implements ICollidable, IMoveable, IDestroyable {
    private double chanceToShoot;
    private double movingSpeed;
    private double livePoints;
    private double lastShotTime;

    /**
     * Constructor for the Enemy
     *
     * @param chanceToShoot      the chance to shoot of the enemy (0.0 to 1.0)
     * @param movingSpeed        the moving speed of the enemy
     * @param livePoints         the live points of the enemy
     * @param color              the color of the enemy
     * @param polygonCoordinates the polygon coordinates of the enemy
     */
    public Enemy(double chanceToShoot, double movingSpeed, double livePoints, CustomColor color, double... polygonCoordinates) {
        super(color, polygonCoordinates);
        this.chanceToShoot = chanceToShoot;
        this.movingSpeed = movingSpeed;
        this.livePoints = livePoints;
        this.lastShotTime = 0;
    }

    /**
     * Determine if the entity is allowed to shoot, depending on the entity's weapon cooldown
     * Set the last time the entity shot if the entity is allowed to shoot
     *
     * @param cooldown            the interval in milliseconds that the entity needs to wait before it can shoot again
     * @param currentTimeInMillis the current time in milliseconds
     * @return true if the entity is allowed to shoot, false otherwise
     */
    public boolean isAllowedToShoot(double cooldown, double currentTimeInMillis) {
        if (cooldown < currentTimeInMillis - this.lastShotTime) {
            this.lastShotTime = currentTimeInMillis;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void collision(World world, Entity entity) {
        if (entity instanceof IDestroyable destroyable) {
            destroyable.removeLivePoints(this.livePoints);

            if (destroyable.getLivePoints() <= 0) {
                world.removeEntity(entity);
            }
        }
    }

    public double getChanceToShoot() {
        return chanceToShoot;
    }

    public void setChanceToShoot(double chanceToShoot) {
        this.chanceToShoot = chanceToShoot;
    }

    @Override
    public double getMovingSpeed() {
        return this.movingSpeed;
    }

    @Override
    public void setMovingSpeed(double movingSpeed) {
        this.movingSpeed = movingSpeed;
    }

    @Override
    public void removeLivePoints(double damagePoints) {
        this.livePoints -= damagePoints;
    }

    @Override
    public double getLivePoints() {
        return this.livePoints;
    }

    @Override
    public void setLivePoints(double livePoints) {
        this.livePoints = livePoints;
    }

    public void setLastShotTime(double lastShotTime) {
        this.lastShotTime = lastShotTime;
    }

    public double getLastShotTime() {
        return this.lastShotTime;
    }
}
