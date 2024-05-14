package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.CustomColor;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.entityproperties.ICollidable;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IDestroyable;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IMoveable;

/**
 * Asteroid: An entity that can move and be destroyed
 */
public class Asteroid extends Entity implements ICollidable, IMoveable, IDestroyable {
    private int asteroidSize;
    private double movingSpeed;
    private double livePoints;
    private double rotationSpeed;
    private double initialRotation;
    private double livePointsSplittingThreshold;

    /**
     * Constructor for the Asteroid
     *
     * @param asteroidSize     the size of the asteroid
     * @param movingSpeed      the moving speed of the asteroid
     * @param livePoints       the live points of the asteroid
     * @param rotationSpeed    the speed at which the asteroid rotates around itself
     * @param color            the color of the asteroid
     * @param shapeCoordinates the shape coordinates of the asteroid
     */
    public Asteroid(int asteroidSize, double movingSpeed, double livePoints, double rotationSpeed, CustomColor color, double... shapeCoordinates) {
        super(color, shapeCoordinates);
        this.asteroidSize = asteroidSize;

        this.movingSpeed = movingSpeed;
        this.livePointsSplittingThreshold = livePoints / 2;
        this.livePoints = livePoints + this.livePointsSplittingThreshold;

        this.rotationSpeed = rotationSpeed;
        this.initialRotation = 0;
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

    public double getInitialRotation() {
        return this.initialRotation;
    }

    public void setInitialRotation(double initialRotation) {
        this.initialRotation = initialRotation;
    }

    public double getRotationSpeed() {
        return this.rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public int getAsteroidSize() {
        return this.asteroidSize;
    }

    public void setAsteroidSize(int asteroidSize) {
        this.asteroidSize = asteroidSize;
    }

    public double getLivePointsSplittingThreshold() {
        return livePointsSplittingThreshold;
    }

    public void setLivePointsSplittingThreshold(double livePointsSplittingThreshold) {
        this.livePointsSplittingThreshold = livePointsSplittingThreshold;
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
}
