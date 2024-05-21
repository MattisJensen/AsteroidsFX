package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.CustomColor;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.processing.IEntityProcessingService;

import java.util.Random;

public class AsteroidControlSystem implements IEntityProcessingService {
    private GameData gameData;
    private World world;
    private final double asteroidLifePoints = 275;

    @Override
    public void process(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;

        // Adds more asteroids if the current amount is less than the desired amount
        addAsteroidsIfNeeded(7);

        for (Entity asteroid : this.world.getEntities(Asteroid.class)) {
            splitAsteroidIfNeeded((Asteroid) asteroid);
            moveEntity((Asteroid) asteroid);
            windowBoundaryInteraction(asteroid);
        }
    }

    /**
     * Adds asteroids to the world if the current amount of asteroids is less than the desired amount.
     *
     * @param desiredAsteroidAmount the desired amount of asteroids
     */
    private void addAsteroidsIfNeeded(int desiredAsteroidAmount) {
        int currentAsteroidAmount = this.world.getEntities(Asteroid.class).size();
        if (currentAsteroidAmount < desiredAsteroidAmount) {
            for (int i = 0; i < desiredAsteroidAmount - currentAsteroidAmount; i++) {
                Asteroid asteroid = createAsteroidAtRandomWindowSide();
                this.world.addEntity(asteroid);
            }
        }
    }


    /**
     * Moves the entity's shape
     *
     * @param asteroid The entity to move
     */
    private void moveEntity(Asteroid asteroid) {
        double changeX = Math.sin(Math.toRadians(asteroid.getInitialRotation()));
        double changeY = Math.cos(Math.toRadians(asteroid.getInitialRotation()));

        asteroid.setXCoordinate(asteroid.getXCoordinate() + changeX * asteroid.getMovingSpeed() * this.gameData.getDeltaTime());
        asteroid.setYCoordinate(asteroid.getYCoordinate() - changeY * asteroid.getMovingSpeed() * this.gameData.getDeltaTime());

        asteroid.setRotation(asteroid.getRotation() + asteroid.getRotationSpeed());
    }

    /**
     * Handles the interaction between the entity and the window boundaries.
     *
     * @param entity The entity to check
     */
    private void windowBoundaryInteraction(Entity entity) {
        if (entity.getXCoordinate() + entity.getHeight() < -20
                || entity.getXCoordinate() - entity.getHeight() > this.gameData.getDisplayWidth() + 20
                || entity.getYCoordinate() + entity.getHeight() < -20
                || entity.getYCoordinate() - entity.getHeight() > this.gameData.getDisplayHeight() + 20) {
            this.world.removeEntity(entity);
        }
    }

    /**
     * Creates an asteroid with random attributes at a random location outside the visible screen area.
     *
     * @return the created asteroid
     */
    private Asteroid createAsteroidAtRandomWindowSide() {
        int asteroidSize = 4;
        double movingSpeed = getAsteroidBasedMovingSpeed(asteroidSize);
        double rotationSpeed = getAsteroidBasedRotationSpeed(asteroidSize, movingSpeed);
        CustomColor asteroidColor = new CustomColor(164, 81, 62);
        double[] shapeCoordinates = {25.95277976989746, 54.60778045654297, 24.076704025268555, 54.484710693359375, 22.210264205932617, 54.259220123291016, 20.363208770751953, 53.90932846069336, 18.549579620361328, 53.415225982666016, 16.785709381103516, 52.76577377319336, 15.088970184326172, 51.9571533203125, 13.476905822753906, 50.990753173828125, 11.965799331665039, 49.8730354309082, 10.568594932556152, 48.615718841552734, 9.273941040039062, 47.252567291259766, 8.064867496490479, 45.8128776550293, 6.936251163482666, 44.309207916259766, 5.8882904052734375, 42.74825668334961, 4.923859596252441, 41.134368896484375, 4.047136545181274, 39.471221923828125, 3.264026403427124, 37.762020111083984, 2.581497550010681, 36.01028060913086, 2.0085948705673218, 34.219749450683594, 1.5557429194450378, 32.39520454406738, 1.232670620083809, 30.543264389038086, 1.0463420301675797, 28.672653198242188, 1.0033808639273047, 26.793325424194336, 1.108684942126274, 24.916507720947266, 1.3650132417678833, 23.0543270111084, 1.772365391254425, 21.219247817993164, 2.3347491025924683, 19.425573348999023, 3.034224033355713, 17.68067169189453, 3.85624361038208, 15.989981651306152, 4.792212009429932, 14.359613418579102, 5.840313911437988, 12.799015045166016, 6.992167949676514, 11.313310623168945, 8.243553638458252, 9.910433769226074, 9.59060001373291, 8.599175930023193, 11.024672508239746, 7.383626461029053, 12.538655281066895, 6.26919412612915, 14.125936508178711, 5.261918544769287, 15.779000282287598, 4.3666768074035645, 17.489484786987305, 3.5867226123809814, 19.248579025268555, 2.9235782623291016, 21.047019958496094, 2.375818967819214, 22.870786666870117, 1.9188669323921204, 24.714811325073242, 1.5524458289146423, 26.574806213378906, 1.2781025767326355, 28.445451736450195, 1.0894234329462051, 30.323226928710938, 1.0022967343684286, 32.203521728515625, 1.005546388681978, 34.080814361572266, 1.1045540198683739, 35.95346450805664, 1.2738012671470642, 37.82060623168945, 1.4952882826328278, 39.67753219604492, 1.7898440957069397, 41.515560150146484, 2.1849430799484253, 43.32205581665039, 2.704989790916443, 45.07558822631836, 3.381563901901245, 46.742713928222656, 4.248624801635742, 48.284732818603516, 5.322225570678711, 49.64403533935547, 6.618307590484619, 50.8035774230957, 8.096060752868652, 51.772274017333984, 9.706380844116211, 52.5780143737793, 11.404561996459961, 53.24567794799805, 13.161561012268066, 53.79518127441406, 14.959488868713379, 54.25177764892578, 16.783320426940918, 54.630489349365234, 18.624975204467773, 54.94324493408203, 20.479019165039062, 55.19766616821289, 22.341955184936523, 55.3662109375, 24.214500427246094, 55.43815994262695, 26.093244552612305, 55.41263961791992, 27.973190307617188, 55.29022216796875, 29.84931755065918, 55.07012176513672, 31.71651268005371, 54.756351470947266, 33.56997299194336, 54.33517837524414, 35.40220260620117, 53.80915069580078, 37.20713424682617, 53.16020202636719, 38.97148513793945, 52.395755767822266, 40.689022064208984, 51.51451110839844, 42.34955978393555, 50.51637268066406, 43.94260787963867, 49.412811279296875, 45.46455764770508, 48.208282470703125, 46.90794372558594, 46.90726089477539, 48.264957427978516, 45.51115798950195, 49.523807525634766, 44.01026153564453, 50.65525436401367, 42.40789794921875, 51.63774108886719, 40.7195930480957, 52.46392822265625, 38.9639778137207, 53.135704040527344, 37.15937423706055, 53.6622200012207, 35.3214111328125, 54.057376861572266, 33.46223831176758, 54.33686828613281, 31.590652465820312, 54.515499114990234, 29.712934494018555, 54.61177062988281, 27.83285140991211, 54.6347541809082};

        Asteroid asteroid = new Asteroid(asteroidSize, movingSpeed, this.asteroidLifePoints, rotationSpeed, asteroidColor, shapeCoordinates);

        Random random = new Random();
        double aHeight = asteroid.getHeight();
        double aWidth = asteroid.getWidth();

        int asteroidSpawnSide = random.nextInt(0, 4);

        switch (asteroidSpawnSide) {
            case 3:
                // moves to the right
                asteroid.setXCoordinate(-aWidth);
                asteroid.setYCoordinate(Math.random() * this.gameData.getDisplayHeight());
                asteroid.setRotation(random.nextInt(40, 140));
                break;
            case 2:
                // moves to the left
                asteroid.setXCoordinate(this.gameData.getDisplayWidth() + aWidth);
                asteroid.setYCoordinate(Math.random() * this.gameData.getDisplayHeight());
                asteroid.setRotation(random.nextInt(200, 340));
                break;
            case 1:
                // moves down
                asteroid.setXCoordinate(Math.random() * this.gameData.getDisplayWidth());
                asteroid.setYCoordinate(-aHeight);
                asteroid.setRotation(random.nextInt(150, 210));
                break;
            case 0:
                // moves up
                asteroid.setXCoordinate(Math.random() * this.gameData.getDisplayWidth());
                asteroid.setYCoordinate(this.gameData.getDisplayHeight() + aHeight);
                asteroid.setRotation(random.nextInt(330, 390));
                break;
        }

        // Set asteroid's initial rotation
        asteroid.setInitialRotation(asteroid.getRotation());
        return asteroid;
    }

    /**
     * Splits an asteroid if it has no live points left and is larger than size 1.
     *
     * @param asteroid the asteroid to split
     */
    private void splitAsteroidIfNeeded(Asteroid asteroid) {
        if (asteroid.getAsteroidSize() > 1 && asteroid.getLivePoints() <= asteroid.getLivePointsSplittingThreshold()) {
            splitAsteroid(asteroid);
        }
    }

    /**
     * Splits an asteroid into 2 smaller asteroids.
     *
     * @param oldAsteroid the asteroid to split
     */
    private void splitAsteroid(Asteroid oldAsteroid) {
        // New polygon based on the old asteroid's polygon
        int newSize = oldAsteroid.getAsteroidSize() - 1;
        double movingSpeed = getAsteroidBasedMovingSpeed(newSize);
        double rotationSpeed = getAsteroidBasedRotationSpeed(newSize, movingSpeed);

        // Scale the old asteroid's coordinates for the new asteroids
        double[] oldCoordinates = oldAsteroid.getPolygonCoordinates();
        double[] newCoordinates = getScaledPolygonCoordinates(oldCoordinates, 0.8);

        // Create 2 new asteroids
        Asteroid newAsteroid1 = createNewSplitAsteroid(oldAsteroid, newSize, movingSpeed, this.asteroidLifePoints, rotationSpeed, 1, newCoordinates);
        Asteroid newAsteroid2 = createNewSplitAsteroid(oldAsteroid, newSize, movingSpeed, this.asteroidLifePoints, rotationSpeed, -1, newCoordinates);

        // Update world entities
        this.world.addEntity(newAsteroid1);
        this.world.addEntity(newAsteroid2);
        this.world.removeEntity(oldAsteroid);
    }

    /**
     * Creates a new asteroid based on an old asteroid.
     *
     * @param oldAsteroid     the old asteroid
     * @param newSize         the size of the new asteroid
     * @param movingSpeed     the moving speed of the new asteroid
     * @param lifePoints      the life points of the new asteroid
     * @param rotationSpeed   the rotation speed of the new asteroid
     * @param offsetDirection the direction of the offset
     * @param newCoordinates  the new coordinates of the new asteroid
     * @return the new asteroid
     */
    private Asteroid createNewSplitAsteroid(Asteroid oldAsteroid, int newSize, double movingSpeed, double lifePoints, double rotationSpeed, int offsetDirection, double[] newCoordinates) {
        // Create new asteroid
        CustomColor asteroidColor = new CustomColor(oldAsteroid.getColor().getRed() + 5 / newSize, oldAsteroid.getColor().getGreen(), oldAsteroid.getColor().getBlue());
        Asteroid newAsteroid = new Asteroid(newSize, movingSpeed, lifePoints, rotationSpeed, asteroidColor, newCoordinates);
        newAsteroid.setRotationSpeed(rotationSpeed);

        // Calculate offset based on the old asteroid's rotation
        double offset = newAsteroid.getWidth() / (1.9 * offsetDirection);
        double rotationRadians = Math.toRadians(oldAsteroid.getInitialRotation());
        double offsetX = offset * Math.cos(rotationRadians);
        double offsetY = offset * Math.sin(rotationRadians);

        // Offset the new asteroid's position from the old asteroid's position
        newAsteroid.setXCoordinate(oldAsteroid.getCenterXCoordinate() + offsetX);
        newAsteroid.setYCoordinate(oldAsteroid.getCenterYCoordinate() + offsetY);

        // Change the new asteroid's rotation
        newAsteroid.setInitialRotation(oldAsteroid.getInitialRotation() + (20 * offsetDirection));

        return newAsteroid;
    }

    /**
     * Scales a polygon to a new size.
     *
     * @param coordinates the original polygon coordinates
     * @param scale       the new size of the asteroid
     * @return the scaled polygon coordinates
     */
    private double[] getScaledPolygonCoordinates(double[] coordinates, double scale) {
        double[] scaledCoordinates = new double[coordinates.length];

        for (int i = 0; i < coordinates.length; i++) {
            scaledCoordinates[i] = coordinates[i] * scale;
        }

        return scaledCoordinates;
    }

    /**
     * Returns the moving speed of an asteroid based on its size.
     *
     * @param asteroidSize the size of the asteroid
     * @return the moving speed of the asteroid
     */
    private double getAsteroidBasedMovingSpeed(int asteroidSize) {
        return (Math.random() * (55.5 / asteroidSize)) + (11.1 / asteroidSize);
    }

    /**
     * Returns the rotation speed based on the given asteroid size and moving speed.
     *
     * @param asteroidSize        the size of the asteroid
     * @param asteroidMovingSpeed the moving speed of the asteroid
     * @return the rotation speed of the asteroid
     */
    private double getAsteroidBasedRotationSpeed(int asteroidSize, double asteroidMovingSpeed) {
        double movingSpeed = (asteroidMovingSpeed * 0.26) / asteroidSize;
        return movingSpeed > 7 ? 7 : movingSpeed;
    }
}
