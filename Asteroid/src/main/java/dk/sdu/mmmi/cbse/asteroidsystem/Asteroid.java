package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IDestroyable;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IMoveable;

/**
 * Asteroid: An entity that can move and be destroyed
 */
public class Asteroid extends Entity implements IMoveable, IDestroyable {
    private int asteroidSize;

    private double initialRotation;
    private double rotationSpeed;
    private double movingSpeed;
    private double livePoints;

    /**
     * Constructor for the Asteroid
     *
     * @param asteroidSize     the size of the asteroid
     * @param movingSpeed      the moving speed of the asteroid
     * @param livePoints       the live points of the asteroid
     * @param shapeCoordinates the shape coordinates of the asteroid
     */
    public Asteroid(int asteroidSize, double movingSpeed, double livePoints, double... shapeCoordinates) {
        setPolygonCoordinates(shapeCoordinates);
        this.asteroidSize = asteroidSize;

        this.movingSpeed = movingSpeed;
        this.livePoints = livePoints;

        this.initialRotation = 0;
        this.rotationSpeed = 4;
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
