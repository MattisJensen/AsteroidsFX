package dk.sdu.mmmi.cbse.common.services.entityproperties;

/**
 * Interface for entities that can use weapons
 */
public interface IWeaponUser {
    /**
     * Determine if the entity is allowed to shoot, depending on the entity's weapon cooldown
     * Set the last time the entity shot if the entity is allowed to shoot
     *
     * @param currentTimeInMillis the current time in milliseconds
     * @return true if the entity is allowed to shoot, false otherwise
     */
    boolean isAllowedToShoot(double cooldown, double currentTimeInMillis);

    /**
     * Set the last time the entity shot
     *
     * @param lastShotTime the last time the entity shot in milliseconds
     */
    void setLastShotTime(double lastShotTime);

    /**
     * Get the last time the entity shot
     *
     * @return the last time the entity shot in milliseconds
     */
    double getLastShotTime();
}
