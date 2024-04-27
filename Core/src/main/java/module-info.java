import dk.sdu.mmmi.cbse.common.services.processing.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.processing.IPostEntityProcessingService;

module Core {
    requires Common;
    requires CommonBullet;    
    requires javafx.graphics;
//    requires spring.boot;
//    requires spring.boot.autoconfigure;
//    requires spring.context;
//    requires spring.beans;
//    exports dk.sdu.mmmi.cbse.main;
    opens dk.sdu.mmmi.cbse.main to javafx.graphics; //, spring.core;
    uses IGamePluginService;
    uses IEntityProcessingService;
    uses IPostEntityProcessingService;
}


