package dk.sdu.mmmi.cbse.common.services.entityproperties;

/**
 * Interface for entities that can move
 */
public interface IMoveable {
    /**
     * Get the moving speed of the entity
     *
     * @return the moving speed of the entity
     */
    double getMovingSpeed();

    /**
     * Set the moving speed of the entity
     *
     * @param movingSpeed the moving speed of the entity
     */
    void setMovingSpeed(double movingSpeed);
}
