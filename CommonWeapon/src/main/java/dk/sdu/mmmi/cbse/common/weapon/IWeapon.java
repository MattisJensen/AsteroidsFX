package dk.sdu.mmmi.cbse.common.weapon;

/**
 * Interface for entities that are weapons
 */
public interface IWeapon {
    /**
     * Get the current damage points of the entity, which depends on the entity's moving speed and raw damage points
     *
     * @return the current damage points of the entity
     */
    double getCurrentDamage();

    /**
     * Get the damage points of the entity
     *
     * @return the damage points of the entity
     */
    double getDamagePoints();

    /**
     * Set the damage points of the entity
     *
     * @param damagePoints the damage points of the entity
     */
    void setDamagePoints(double damagePoints);
    
    /**
     * Set the cooldown of the weapon
     *
     * @param cooldown the cooldown of the weapon in milliseconds
     */
    void setCooldown(double cooldown);


    /**
     * Get the cooldown of the weapon
     *
     * @return the cooldown of the weapon in milliseconds
     */
    double getCooldown();
}
