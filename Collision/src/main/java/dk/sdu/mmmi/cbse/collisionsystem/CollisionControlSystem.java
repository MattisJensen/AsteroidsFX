package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IDestroyable;
import dk.sdu.mmmi.cbse.common.services.entityproperties.IWeapon;
import dk.sdu.mmmi.cbse.common.services.processing.IPostEntityProcessingService;

import java.util.ArrayList;
import java.util.Collection;

/**
 * CollisionControlSystem: Detects collisions between entities and removes them if they are able to deal damage and take damage.
 */
public class CollisionControlSystem implements IPostEntityProcessingService {
    Collection<Entity> entities;
    ArrayList<Entity> entitiesToRemove;

    @Override
    public void process(GameData gameData, World world) {
        this.entities = world.getEntities();
        this.entitiesToRemove = new ArrayList<>();

        for (Entity entity1 : this.entities) {
            for (Entity entity2 : this.entities) {
                if (entity1 != entity2
                        && !this.entitiesToRemove.contains(entity1)
                        && !this.entitiesToRemove.contains(entity2)
                        && isCollision(entity1, entity2)) {
                    damageEntities(entity1, entity2);
                }
            }
        }

        for (Entity entity : entitiesToRemove) {
            world.removeEntity(entity);
        }
    }

    /**
     * Damages two entities if they are able to deal damage and take damage.
     * An entity doesn't have to be a weapon to deal damage.
     *
     * @param entity1 The first entity.
     * @param entity2 The second entity.
     */
    public void damageEntities(Entity entity1, Entity entity2) {
        double damageOfEntity1 = calculateDamage(entity1);
        double damageOfEntity2 = calculateDamage(entity2);

        applyDamage(entity1, damageOfEntity2);
        applyDamage(entity2, damageOfEntity1);

        checkSurvival(entity1);
        checkSurvival(entity2);
    }

    /**
     * Calculates the damage an entity can deal.
     *
     * @param entity The entity to calculate the damage of.
     * @return The damage the entity can deal.
     */
    private double calculateDamage(Entity entity) {
        if (entity instanceof IWeapon) {
            return ((IWeapon) entity).getCurrentDamage();
        } else if (entity instanceof IDestroyable) {
            return ((IDestroyable) entity).getLivePoints();
        }
        return 0;
    }

    /**
     * Applies damage to an entity.
     *
     * @param entity The entity to apply the damage to.
     * @param damage The amount of damage to apply.
     */
    private void applyDamage(Entity entity, double damage) {
        if (entity instanceof IDestroyable) {
            ((IDestroyable) entity).removeLivePoints(damage);
        }
    }

    /**
     * Checks if an entity is still alive.
     * If the entity is not alive, it will be added to the list of entities to remove.
     *
     * @param entity The entity to check.
     */
    private void checkSurvival(Entity entity) {
        if (entity instanceof IDestroyable) {
            if (((IDestroyable) entity).getLivePoints() <= 0) {
                this.entitiesToRemove.add(entity);
            }
        }
    }

    /**
     * Checks if two entities are colliding.
     *
     * @param e1 The first entity.
     * @param e2 The second entity.
     * @return True if the entities are colliding, false otherwise.
     */
    public boolean isCollision(Entity e1, Entity e2) {
        // distance between the two entities
        double distance = Math.sqrt(Math.pow(e2.getCenterXCoordinate() - e1.getCenterXCoordinate(), 2) + Math.pow(e2.getCenterYCoordinate() - e1.getCenterYCoordinate(), 2));

        return distance < (e1.getWidth() / 2) + (e2.getWidth() / 2);
    }
}