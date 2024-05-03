package dk.sdu.mmmi.cbse.common.services.entityproperties;

/**
 * Interface for entities that can take damage
 */
public interface IDestroyable {
    /**
     * Remove live points from the entity
     *
     * @param damagePoints the amount of live points to remove
     */
    void removeLivePoints(double damagePoints);

    /**
     * Get the live points of the entity
     *
     * @return the live points of the entity
     */
    double getLivePoints();

    /**
     * Set the live points of the entity
     *
     * @param livePoints the live points of the entity
     */
    void setLivePoints(double livePoints);

}
