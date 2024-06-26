package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.CustomColor;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.entityproperties.ICollidable;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IDestroyable;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IMoveable;

/**
 * Bullet: A bullet entity which can be shot
 */
public class Bullet extends Entity implements ICollidable, IMoveable, IDestroyable {
    private Entity shotBy;
    private double cooldown;
    private double movingSpeed;
    private double livePoints;
    private double damagePoints;

    /**
     * Constructor for the Bullet
     *
     * @param cooldown         the time in milliseconds that the bullet needs to wait before it can be fired again
     * @param movingSpeed      the speed at which the bullet moves
     * @param livePoints       the amount of health the bullet has
     * @param damagePoints     the amount of damage the bullet deals
     * @param color            the color of the bullet
     * @param shapeCoordinates the coordinates defining the shape of the bullet
     */
    public Bullet(Entity shotBy, double cooldown, double movingSpeed, double livePoints, double damagePoints, CustomColor color, double... shapeCoordinates) {
        super(color, shapeCoordinates);
        this.shotBy = shotBy;
        this.cooldown = cooldown;
        this.movingSpeed = movingSpeed;
        this.livePoints = livePoints;
        this.damagePoints = damagePoints;
    }

    @Override
    public void collision(World world, Entity entity) {
        if (entity instanceof IDestroyable destroyable) {
            destroyable.removeLivePoints(getCurrentDamage());

            if (destroyable.getLivePoints() <= 0) {
                collisionRemoval(world, entity);
            }
        }
    }

    @Override
    public void collisionRemoval(World world, Entity entity) {
        // collision removal with collision of two bullets doesn't need to be handled by the shooter
        if (entity instanceof Bullet) {
            world.removeEntity(entity);
            return;
        }

        // If bullet destroys the entity, the shooter should handle the collision removal of the destroyed entity
        if (this.shotBy instanceof ICollidable shooter) {
            shooter.collisionRemoval(world, entity);
            return;
        }

        // When removal not handled yet, handle removal
        world.removeEntity(entity);
    }

    /**
     * Get the current damage points of the entity, which depends on the entity's moving speed and raw damage points
     *
     * @return the current damage points of the entity
     */
    public double getCurrentDamage() {
        return this.damagePoints + this.movingSpeed * 1.2;
    }

    public Entity getShotBy() {
        return shotBy;
    }

    public void setShotBy(Entity shotBy) {
        this.shotBy = shotBy;
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

    public double getDamagePoints() {
        return this.damagePoints;
    }

    public void setDamagePoints(double damagePoints) {
        this.damagePoints = damagePoints;
    }

    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }

    public double getCooldown() {
        return this.cooldown;
    }
}
