package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Random;

public class AsteroidControlSystem implements IEntityProcessingService {
    private GameData gameData;
    private World world;

    private int asteroidAmount = 7;

    @Override
    public void process(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;

        // Add asteroids if there are less than the desired amount
        int currentAsteroidAmount = world.getEntities(Asteroid.class).size();
        if (currentAsteroidAmount < asteroidAmount) {
            for (int i = 0; i < asteroidAmount - currentAsteroidAmount; i++) {
                Entity asteroid = createRandomAsteroid();
                world.addEntity(asteroid);
            }
        }

        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            // Check if asteroid is destroyed and should split into smaller asteroids
            double asteroidSize = ((Asteroid) asteroid).getAsteroidSize();
            if (asteroidSize > 1 && asteroid.getLivePoints() < ((Asteroid) asteroid).getMinLivePoints()) {
                splitAsteroid((Asteroid) asteroid);
            } else {
                setShape((Asteroid) asteroid);
            }
        }
    }

    /**
     * Sets the new location and rotation of an asteroid based on its moving speed and rotation speed.
     *
     * @param asteroid the asteroid to set the shape of
     */
    private void setShape(Asteroid asteroid) {
        double changeX = Math.sin(Math.toRadians(asteroid.getInitialRotation()));
        double changeY = Math.cos(Math.toRadians(asteroid.getInitialRotation()));

        asteroid.setRotation(asteroid.getRotation() + asteroid.getRotationSpeed());

        asteroid.setX(asteroid.getX() + changeX * asteroid.getMovingSpeed());
        asteroid.setY(asteroid.getY() - changeY * asteroid.getMovingSpeed());

        removeOutOfWindowAsteroid(asteroid);
    }

    /**
     * Removes an asteroid from the world if it is outside the visible screen area.
     *
     * @param entity the asteroid to remove
     */
    private void removeOutOfWindowAsteroid(Entity entity) {
        if (entity.getX() + entity.getHeight() < -20 || entity.getX() - entity.getHeight() > gameData.getDisplayWidth() + 20 || entity.getY() + entity.getHeight() < -20 || entity.getY() - entity.getHeight() > gameData.getDisplayHeight() + 20) {
            world.removeEntity(entity);
        }
    }

    /**
     * Creates an asteroid with random attributes at a random location outside the visible screen area.
     *
     * @return the created asteroid
     */
    public Entity createRandomAsteroid() {
        Entity asteroid = new Asteroid(2);

        Random random = new Random();
        double aHeight = asteroid.getHeight();
        double aWidth = asteroid.getWidth();

        int asteroidSpawnSide = random.nextInt(0, 4);

        switch (asteroidSpawnSide) {
            case 3:
                // moves to the right
                asteroid.setX(-aWidth);
                asteroid.setY(Math.random() * gameData.getDisplayHeight());
                asteroid.setRotation(random.nextInt(40, 140));
                break;
            case 2:
                // moves to the left
                asteroid.setX(gameData.getDisplayWidth() + aWidth);
                asteroid.setY(Math.random() * gameData.getDisplayHeight());
                asteroid.setRotation(random.nextInt(200, 340));
                break;
            case 1:
                // moves down
                asteroid.setX(Math.random() * gameData.getDisplayWidth());
                asteroid.setY(-aHeight);
                asteroid.setRotation(random.nextInt(150, 210));
                break;
            case 0:
                // moves up
                asteroid.setX(Math.random() * gameData.getDisplayWidth());
                asteroid.setY(gameData.getDisplayHeight() + aHeight);
                asteroid.setRotation(random.nextInt(330, 390));
                break;
        }

        ((Asteroid) asteroid).setInitialRotation(asteroid.getRotation());
        return asteroid;
    }

    /**
     * Splits an asteroid into 2 smaller asteroids.
     *
     * @param oldAsteroid the asteroid to split
     */
    private void splitAsteroid(Asteroid oldAsteroid) {
        // New polygon based on the old asteroid's polygon
        double newSize = oldAsteroid.getAsteroidSize() - 1;
        double[] oldCoordinates = oldAsteroid.getPolygonCoordinates();
        double[] newCoordinates = getScaledPolygonCoordinates(oldCoordinates, 0.8);

        // Create 2 new asteroids
        Asteroid newAsteroid1 = new Asteroid(newSize);
        Asteroid newAsteroid2 = new Asteroid(newSize);
        newAsteroid1.setPolygonCoordinates(newCoordinates);
        newAsteroid2.setPolygonCoordinates(newCoordinates);

        // Calculate offset based on the old asteroid's rotation
        double offset = newAsteroid1.getWidth() / 1.9;
        double rotationRadians = Math.toRadians(oldAsteroid.getInitialRotation());
        double offsetX = offset * Math.cos(rotationRadians);
        double offsetY = offset * Math.sin(rotationRadians);

        // Offset the new asteroids' positions from the old asteroid's position
        newAsteroid1.setX(oldAsteroid.getCenterX() + offsetX);
        newAsteroid1.setY(oldAsteroid.getCenterY() + offsetY);
        newAsteroid2.setX(oldAsteroid.getCenterX() - offsetX);
        newAsteroid2.setY(oldAsteroid.getCenterY() - offsetY);

        // Change the new asteroids' rotation
        newAsteroid1.setInitialRotation(oldAsteroid.getInitialRotation() + 20);
        newAsteroid2.setInitialRotation(oldAsteroid.getInitialRotation() - 20);

        // Set the new asteroids' moving and rotation speed
//        newAsteroid1.setMovingSpeed(oldAsteroid.getMovingSpeed());
//        newAsteroid2.setMovingSpeed(oldAsteroid.getMovingSpeed());
//        newAsteroid1.setRotationSpeed(oldAsteroid.getRotationSpeed());
//        newAsteroid2.setRotationSpeed(oldAsteroid.getRotationSpeed());

        // Update world entities
        world.addEntity(newAsteroid1);
        world.addEntity(newAsteroid2);
        world.removeEntity(oldAsteroid);
    }

    /**
     * Scales a polygon to a new size.
     *
     * @param coordinates the original polygon coordinates
     * @param scale       the new size of the asteroid
     */
    private double[] getScaledPolygonCoordinates(double[] coordinates, double scale) {
        double[] scaledCoordinates = new double[coordinates.length];

        for (int i = 0; i < coordinates.length; i++) {
            scaledCoordinates[i] = coordinates[i] * scale;
        }

        return scaledCoordinates;
    }
}
