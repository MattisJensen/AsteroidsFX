package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

/**
 * AsteroidPlugin: Starts and stops the asteroid entities
 */
public class AsteroidPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            if (asteroid != null) {
                world.removeEntity(asteroid);
            }
        }
    }
}
