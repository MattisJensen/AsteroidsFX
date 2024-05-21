package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * The IGamePluginService interface provides a contract for classes that will manage the lifecycle of game plugins.
 * Implementations of this interface are expected to define the behavior of game plugins during the start and stop phases.
 */
public interface IGamePluginService {
    /**
     * The start method is responsible for initializing and starting the game plugin.
     * This method is expected to be called when the game plugin is loaded.
     * <p>
     * Pre-condition: gameData and world must not be null.
     * Post-condition: The game plugin is initialized and started.
     *
     * @param gameData The current state of the game.
     * @param world    The world object representing the game world, which contains a collection of all entities in the game.
     */
    void start(GameData gameData, World world);

    /**
     * The stop method is responsible for stopping and cleaning up the game plugin.
     * This method is expected to be called when the game plugin is unloaded.
     * <p>
     * Pre-condition: gameData and world must not be null.
     * Post-condition: The game plugin is stopped and cleaned up.
     *
     * @param gameData The current state of the game.
     * @param world    The world object representing the game world, which contains a collection of all entities in the game.
     */
    void stop(GameData gameData, World world);
}