package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.entityproperties.ICollidable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * CollisionControlSystemTest: Tests the CollisionControlSystem class.
 */
class CollisionControlSystemTest {
    private GameData gameData;
    private World world;
    private Entity entity1;
    private Entity entity2;
    private CollisionControlSystem collisionControlSystem;

    /**
     * Setup method to create the initial mock environment for each test.
     */
    @BeforeEach
    void setup() {
        // Creates new entities, mocks the game data and world.
        gameData = mock(GameData.class);
        world = mock(World.class);
        collisionControlSystem = new CollisionControlSystem();

        // Mocks the world to add entities
        doAnswer(invocation -> {
            Entity entity = invocation.getArgument(0);
            world.getEntities().add(entity);
            return null;
        }).when(world).addEntity(any(Entity.class));

        // Mocks the world to retrieve the list of entities
        when(world.getEntities()).thenReturn(new ArrayList<>());
    }

    /**
     * Tests if the CollisionControlSystem detects that there is no collision between two entities.
     */
    @Test
    void testNoCollision() {
        // Setup of entities (with no collision)
        entity1 = mock(Entity.class);
        entity2 = mock(Entity.class);

        when(entity1.getCenterXCoordinate()).thenReturn(0.0);
        when(entity1.getCenterYCoordinate()).thenReturn(0.0);
        when(entity1.getWidth()).thenReturn(4.0);

        when(entity2.getCenterXCoordinate()).thenReturn(15.0);
        when(entity2.getCenterYCoordinate()).thenReturn(15.0);
        when(entity2.getWidth()).thenReturn(4.0);

        // Adds the entities to the world
        world.addEntity(entity1);
        world.addEntity(entity2);

        // Checks if the entities are in the world
        assertTrue(world.getEntities().contains(entity1));
        assertTrue(world.getEntities().contains(entity2));

        // Checks if the collision control system detects no collision between the entities
        assertFalse(collisionControlSystem.isCollision(entity1, entity2));
    }

    /**
     * Tests if the CollisionControlSystem detects that there is a collision between two entities.
     */
    @Test
    void testCollision() {
        // Setup of entities (with collision)
        entity1 = mock(Entity.class);
        entity2 = mock(Entity.class);

        when(entity1.getCenterXCoordinate()).thenReturn(0.0);
        when(entity1.getCenterYCoordinate()).thenReturn(0.0);
        when(entity1.getWidth()).thenReturn(4.0);

        when(entity2.getCenterXCoordinate()).thenReturn(2.0);
        when(entity2.getCenterYCoordinate()).thenReturn(2.0);
        when(entity2.getWidth()).thenReturn(4.0);

        // Adds the entities to the world
        world.addEntity(entity1);
        world.addEntity(entity2);

        // Checks if the entities are in the world
        assertTrue(world.getEntities().contains(entity1));
        assertTrue(world.getEntities().contains(entity2));

        // Checks if the collision control system detects no collision between the entities
        assertTrue(collisionControlSystem.isCollision(entity1, entity2));
    }

    /**
     * Tests if the CollisionControlSystem calls collision properly on colliding entities.
     */
    @Test
    void testCollisionCall() {
        // Entities with ICollidable
        entity1 = mock(Entity.class, withSettings().extraInterfaces(ICollidable.class));
        entity2 = mock(Entity.class, withSettings().extraInterfaces(ICollidable.class));

        // Entities to ICollidable
        ICollidable collidable1 = (ICollidable) entity1;
        ICollidable collidable2 = (ICollidable) entity2;

        // Setup of entities (with collision)
        when(entity1.getCenterXCoordinate()).thenReturn(0.0);
        when(entity1.getCenterYCoordinate()).thenReturn(0.0);
        when(entity1.getWidth()).thenReturn(4.0);

        when(entity2.getCenterXCoordinate()).thenReturn(2.0);
        when(entity2.getCenterYCoordinate()).thenReturn(2.0);
        when(entity2.getWidth()).thenReturn(4.0);

        // Adds the entities to the world
        world.addEntity(entity1);
        world.addEntity(entity2);

        // Checks if the entities are in the world
        assertTrue(world.getEntities().contains(entity1));
        assertTrue(world.getEntities().contains(entity2));

        // Process CollisionControlSystem
        collisionControlSystem.process(gameData, world);

        // Verify that collision was called on each ICollidable mock by CollisionControlSystem
        // Verify that the collision got parsed the correct entity
        // Verify that the collision only was called once on each entity
        verify(collidable1).collision(world, entity2);
        verify(collidable2).collision(world, entity1);
    }
}