package dk.sdu.mmmi.cbse.common.services.processing;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * The IPostEntityProcessingService interface provides a contract for classes that will process entities in the game after all IEntityProcessingServices have been executed.
 * Implementations of this interface are expected to define the behavior of entities in the game world after all other processing has been completed.
 */
public interface IPostEntityProcessingService {
    /**
     * The process method is responsible for updating the state of entities in the game world after all IEntityProcessingServices have been executed.
     * This method is expected to be called in each game loop iteration, after all IEntityProcessingServices.
     * <p>
     * Pre-condition: gameData and world must not be null.
     * Post-condition: The state of entities in the world may be updated based on the gameData and the results of all IEntityProcessingServices.
     *
     * @param gameData The current state of the game.
     * @param world    The world object representing the game world, which contains a collection of all entities in the game.
     */
    void process(GameData gameData, World world);
}