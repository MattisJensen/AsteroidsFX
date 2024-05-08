import dk.sdu.mmmi.cbse.common.services.processing.IEntityProcessingService;
import dk.sdu.mmmi.cbse.split.SplitProcessingService;

module ModuleSplitB {
    requires Common;
    provides IEntityProcessingService with SplitProcessingService;
}