import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidControlSystem;
import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidPlugin;
import dk.sdu.mmmi.cbse.common.services.processing.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Asteroid {
    requires Common;
    provides IGamePluginService with AsteroidPlugin;
    provides IEntityProcessingService with AsteroidControlSystem;
}