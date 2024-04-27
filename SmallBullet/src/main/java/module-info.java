import dk.sdu.mmmi.cbse.bulletsystem.SmallBulletControlSystem;
import dk.sdu.mmmi.cbse.bulletsystem.SmallBulletPlugin;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.processing.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module SmallBullet {
    requires Common;
    requires CommonBullet;
    provides IGamePluginService with SmallBulletPlugin;
    provides IEntityProcessingService with SmallBulletControlSystem;
    provides BulletSPI with SmallBulletControlSystem;

}