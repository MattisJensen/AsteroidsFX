import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.processing.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.enemysystem.EnemyControlSystem;
import dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;

module Enemy {
    requires Common;
    requires CommonBullet;
    uses BulletSPI;
    provides IGamePluginService with EnemyPlugin;
    provides IEntityProcessingService with EnemyControlSystem;
}