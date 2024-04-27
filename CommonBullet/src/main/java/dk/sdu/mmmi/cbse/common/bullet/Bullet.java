package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IDestroyable;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IMoveable;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IWeapon;

/**
 * Bullet: A bullet entity which can be shot
 */
public class Bullet extends Entity implements IWeapon, IMoveable, IDestroyable {
    private double cooldown;
    private double movingSpeed;
    private double livePoints;
    private double damagePoints;

    /**
     * Constructor for the Bullet
     *
     * @param cooldown the time in milliseconds that the bullet needs to wait before it can be fired again
     * @param movingSpeed the speed at which the bullet moves
     * @param livePoints the amount of health the bullet has
     * @param damagePoints the amount of damage the bullet deals
     * @param shapeCoordinates the coordinates defining the shape of the bullet
     */
    public Bullet(double cooldown, double movingSpeed, double livePoints, double damagePoints, double... shapeCoordinates) {
        super(shapeCoordinates);
        this.cooldown = cooldown;
        this.movingSpeed = movingSpeed;
        this.livePoints = livePoints;
        this.damagePoints = damagePoints;
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

    @Override
    public double getCurrentDamage() {
        return this.damagePoints + this.movingSpeed * 1.2;
    }

    @Override
    public double getDamagePoints() {
        return this.damagePoints;
    }

    @Override
    public void setDamagePoints(double damagePoints) {
        this.damagePoints = damagePoints;
    }

    @Override
    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public double getCooldown() {
        return this.cooldown;
    }
}
