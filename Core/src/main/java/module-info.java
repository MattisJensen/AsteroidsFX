import dk.sdu.mmmi.cbse.common.services.processing.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.processing.IPostEntityProcessingService;

module Core {
    requires Common;
    requires javafx.graphics;
    requires spring.core;
    requires spring.context;
    requires spring.beans;
    requires java.net.http;
    exports dk.sdu.mmmi.cbse.main;
    opens dk.sdu.mmmi.cbse.main to javafx.graphics, spring.core;
    uses IGamePluginService;
    uses IEntityProcessingService;
    uses IPostEntityProcessingService;
}