package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Random;

public class AsteroidControlSystem implements IEntityProcessingService {
    private GameData gameData;
    private World world;

    private int asteroidAmount = 6;

    @Override
    public void process(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;

        int currentAsteroidAmount = world.getEntities(Asteroid.class).size();

        if (currentAsteroidAmount < asteroidAmount) {
            for (int i = 0; i < asteroidAmount - currentAsteroidAmount; i++) {
                Entity asteroid = createAsteroid();
                world.addEntity(asteroid);
            }
        }

        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            setShape((Asteroid) asteroid);
        }
    }

    public Entity createAsteroid() {
        Entity asteroid = new Asteroid();

        Random random = new Random();

        double aHeight = asteroid.getHeight();
        double aWidth = asteroid.getWidth();

        int asteroidSpawnSide = random.nextInt(0, 4);

        switch (asteroidSpawnSide) {
            case 3:
                asteroid.setX(-aWidth);
                asteroid.setY(Math.random() * gameData.getDisplayHeight());
                asteroid.setRotation(random.nextInt(20, 160));
                ((Asteroid) asteroid).setInitialRotation(asteroid.getRotation());
                break;
            case 2:
                asteroid.setX(gameData.getDisplayWidth() + aWidth);
                asteroid.setY(Math.random() * gameData.getDisplayHeight());
                asteroid.setRotation(random.nextInt(200, 340));
                ((Asteroid) asteroid).setInitialRotation(asteroid.getRotation());
                break;
            case 1:
                asteroid.setX(Math.random() * gameData.getDisplayWidth());
                asteroid.setY(-aHeight);
                asteroid.setRotation(random.nextInt(110, 250));
                ((Asteroid) asteroid).setInitialRotation(asteroid.getRotation());
                break;
            case 0:
                asteroid.setX(Math.random() * gameData.getDisplayWidth());
                asteroid.setY(gameData.getDisplayHeight() + aHeight);
                asteroid.setRotation(random.nextInt(-70, 70));
                ((Asteroid) asteroid).setInitialRotation(asteroid.getRotation());
                break;
        }

        return asteroid;
    }

    private void setShape(Asteroid asteroid) {
        double changeX = Math.sin(Math.toRadians(asteroid.getInitialRotation()));
        double changeY = Math.cos(Math.toRadians(asteroid.getInitialRotation()));

        asteroid.setRotation(asteroid.getRotation() + Math.random() * asteroid.getRotationSpeed());

        asteroid.setX(asteroid.getX() + changeX * Math.random() * asteroid.getMovingSpeed());
        asteroid.setY(asteroid.getY() - changeY * Math.random() * asteroid.getMovingSpeed());

        removeOutOfWindowAsteroid(asteroid);
    }

    private void removeOutOfWindowAsteroid(Entity entity) {
        if (entity.getX() + entity.getHeight() < -20 || entity.getX() - entity.getHeight() > gameData.getDisplayWidth() + 20 || entity.getY() + entity.getHeight() < -20 || entity.getY() - entity.getHeight() > gameData.getDisplayHeight() + 20) {
            world.removeEntity(entity);
        }
    }
}
