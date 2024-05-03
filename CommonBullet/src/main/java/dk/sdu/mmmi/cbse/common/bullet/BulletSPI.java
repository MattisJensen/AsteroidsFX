package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 * Interface for creating bullets
 */
public interface BulletSPI {
    /**
     * Creates a bullet entity based on the position of the given entity.
     *
     * @param gameData The game data
     * @param entity The entity which creates the bullet
     * @return The created bullet entity
     */
    Entity createBullet(GameData gameData, Entity entity);
}
