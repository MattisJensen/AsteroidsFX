package dk.sdu.mmmi.cbse.split;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.processing.IEntityProcessingService;

public class SplitProcessingService implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        System.out.println("This is split package message A");
    }
}
