package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IDestroyable;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IMoveable;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IWeaponUser;

/**
 * Player: A player entity which can be controlled by the user
 */
public class Player extends Entity implements IMoveable, IDestroyable, IWeaponUser {
    private double movingSpeed;
    private double livePoints;
    private double lastShotTime;

    /**
     * Constructor for the Player
     *
     * @param movingSpeed the moving speed of the player
     * @param livePoints the live points of the player
     * @param shapeCoordinates the shape coordinates of the player
     */
    Player(double movingSpeed, double livePoints, double... shapeCoordinates) {
        super(shapeCoordinates);
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
