package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * The IEntityProcessingService interface provides a contract for classes that will process entities in the game.
 * Implementations of this interface are expected to define the behavior of entities in the game world.
 */
public interface IEntityProcessingService {

    /**
     * Process method is responsible for updating the state of entities in the game world.
     * This method is expected to be called in each game loop iteration.
     *
     * Pre-condition: gameData and world must not be null.
     * Post-condition: The state of entities in the world may be updated based on the gameData.
     *
     * @param gameData The current state of the game.
     * @param world The world object representing the game world, which contains a collection of all entities in the game.
     */
    void process(GameData gameData, World world);
}