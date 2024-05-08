import dk.sdu.mmmi.cbse.common.services.processing.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.processing.IPostEntityProcessingService;

module Core {
    requires Common;
    requires javafx.graphics;
    opens dk.sdu.mmmi.cbse.main to javafx.graphics;
    uses IGamePluginService;
    uses IEntityProcessingService;
    uses IPostEntityProcessingService;
}