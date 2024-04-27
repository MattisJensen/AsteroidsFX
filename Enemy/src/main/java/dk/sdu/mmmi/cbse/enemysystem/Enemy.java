package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IDestroyable;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IMoveable;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IWeaponUser;

/**
 * Enemy: An enemy entity which can move, be destroyed, deal damage and use weapons
 */
public class Enemy extends Entity implements IMoveable, IDestroyable, IWeaponUser {
    private double movingSpeed;
    private double livePoints;
    private double lastShotTime;

    /**
     * Constructor for the Enemy
     *
     * @param movingSpeed the moving speed of the enemy
     * @param livePoints the live points of the enemy
     * @param polygonCoordinates the polygon coordinates of the enemy
     */
    public Enemy(double movingSpeed, double livePoints, double... polygonCoordinates) {
        super(polygonCoordinates);
        this.movingSpeed = movingSpeed;
        this.livePoints = livePoints;
        this.lastShotTime = 0;
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
    public boolean isAllowedToShoot(double cooldown, double currentTimeInMillis) {
        if (cooldown < currentTimeInMillis - this.lastShotTime) {
            this.lastShotTime = currentTimeInMillis;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setLastShotTime(double lastShotTime) {
        this.lastShotTime = lastShotTime;
    }

    @Override
    public double getLastShotTime() {
        return this.lastShotTime;
    }
}
