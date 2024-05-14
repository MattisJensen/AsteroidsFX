package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.CustomColor;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.entityproperties.ICollidable;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IDestroyable;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IMoveable;

/**
 * Player: A player entity which can be controlled by the user
 */
public class Player extends Entity implements ICollidable, IMoveable, IDestroyable {
    private double movingSpeed;
    private double livePoints;
    private double lastShotTime;

    /**
     * Constructor for the Player
     *
     * @param movingSpeed      the moving speed of the player
     * @param livePoints       the live points of the player
     * @param color            the color of the player
     * @param shapeCoordinates the shape coordinates of the player
     */
    Player(double movingSpeed, double livePoints, CustomColor color, double... shapeCoordinates) {
        super(color, shapeCoordinates);
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
                collisionRemoval(world, entity);
            }
        }
    }

    @Override
    public void collisionRemoval(World world, Entity entity) {
        world.removeEntity(entity);
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
