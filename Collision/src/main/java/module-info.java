import dk.sdu.mmmi.cbse.collisionsystem.CollisionControlSystem;
import dk.sdu.mmmi.cbse.common.services.processing.IPostEntityProcessingService;

module Collision {
    requires Common;
    provides IPostEntityProcessingService with CollisionControlSystem;
}