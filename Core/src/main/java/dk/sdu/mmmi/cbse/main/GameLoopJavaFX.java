package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.processing.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.processing.IPostEntityProcessingService;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * GameLoopJavaFX: The game loop for the JavaFX version of the game.
 */
public class GameLoopJavaFX {
    private final GameData gameData;
    private final World world;
    private final List<IGamePluginService> gamePluginServices;
    private final List<IEntityProcessingService> entityProcessingServices;
    private final List<IPostEntityProcessingService> postEntityProcessingServices;

    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();
    private double lastSystemTime = 0;

    /**
     * Constructor for the GameLoopJavaFX class.
     *
     * @param gameData The game data.
     * @param world The game world.
     * @param gamePluginServices The game plugin services.
     * @param entityProcessingServices The entity processing services.
     * @param postEntityProcessingServices The post entity processing services.
     */
    public GameLoopJavaFX(GameData gameData, World world, List<IGamePluginService> gamePluginServices, List<IEntityProcessingService> entityProcessingServices, List<IPostEntityProcessingService> postEntityProcessingServices) {
        this.gameData = gameData;
        this.world = world;
        this.gamePluginServices = gamePluginServices;
        this.entityProcessingServices = entityProcessingServices;
        this.postEntityProcessingServices = postEntityProcessingServices;
    }

    /**
     * Starts the game loop.
     *
     * @param window The stage to start the game loop on.
     */
    public void start(Stage window) {
        this.gameWindow.setPrefSize(this.gameData.getDisplayWidth(), this.gameData.getDisplayHeight());

        // Create background
        Background background = getBackground();
        this.gameWindow.setBackground(background);

        // Create game scene
        Scene scene = new Scene(this.gameWindow);
        setKeyEvents(scene);

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(this.gameData, this.world);
        }

        render();

        // Add scene to window
        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();
    }

    /**
     * Starts the game loop.
     */
    private void render() {
        new AnimationTimer() {
            private long then = 0;

            @Override
            public void handle(long now) {
                update();
                draw();
                gameData.getKeys().update();
            }
        }.start();
    }

    /**
     * Updates entities of the game.
     */
    private void update() {
        updateDeltaGameTime(System.currentTimeMillis());

        // Update all entities using the entity processing service
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(this.gameData, this.world);
        }

        // Update all entities using the post entity processing service
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(this.gameData, this.world);
        }

        removeDeletedEntities();
        addNewEntitiesToWindow();
    }

    /**
     * Draws the entities of the game.
     */
    private void draw() {
        for (Entity entity : this.world.getEntities()) {
            Polygon polygon = this.polygons.get(entity);
            polygon.setTranslateX(entity.getXCoordinate());
            polygon.setTranslateY(entity.getYCoordinate());
            polygon.setRotate(entity.getRotation());

        }
    }

    /**
     * Updates the delta game time.
     *
     * @param currentTimeInMilliseconds The current time in milliseconds.
     */
    private void updateDeltaGameTime(double currentTimeInMilliseconds) {
        if (lastSystemTime == 0) {
            lastSystemTime = currentTimeInMilliseconds;
        }
        this.gameData.setDeltaTime((currentTimeInMilliseconds - this.lastSystemTime) / 1000f);
        this.lastSystemTime = currentTimeInMilliseconds;
    }

    /**
     * Removes deleted entities from the game window and the polygons map.
     */
    private void removeDeletedEntities() {
        for (Entity entity : this.polygons.keySet()) {
            if (!this.world.getEntities().contains(entity)) {
                Polygon p = this.polygons.get(entity);
                if (this.gameWindow.getChildren().contains(p)) {
                    this.gameWindow.getChildren().remove(p);
                }
                this.polygons.remove(entity);
            }
        }
    }

    /**
     * Adds new entities to the game window and the corresponding polygon to the polygons map.
     */
    private void addNewEntitiesToWindow() {
        for (Entity entity : this.world.getEntities()) {
            if (!this.polygons.containsKey(entity)) {
                Polygon polygon = new Polygon(entity.getPolygonCoordinates());

                // Set the color of the polygon
                int red = entity.getColor().getRed();
                int green = entity.getColor().getGreen();
                int blue = entity.getColor().getBlue();
                polygon.setFill(Color.rgb(red, green, blue));

                // Add the polygon to the game window
                this.polygons.put(entity, polygon);
                this.gameWindow.getChildren().add(polygon);
            }
        }
    }

    /**
     * Creates the background of the game.
     *
     * @return The background of the game.
     */
    private static Background getBackground() {
        LinearGradient linearGradient = new LinearGradient(
                0, // start X
                0, // start Y
                1, // end X
                1, // end Y
                true, // proportional
                CycleMethod.NO_CYCLE, // cycle colors
                new Stop(0.1, Color.rgb(47, 0, 60)), // stops
                new Stop(1.0, Color.rgb(33, 0, 77))
        );
        BackgroundFill backgroundFill = new BackgroundFill(linearGradient, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        return background;
    }

    /**
     * Sets the key event for a scene.
     *
     * @param scene The scene to set the key check for.
     */
    private void setKeyEvents(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                this.gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                this.gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                this.gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                this.gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                this.gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                this.gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                this.gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                this.gameData.getKeys().setKey(GameKeys.SPACE, false);
            }
        });
    }

    /**
     * Gets all the game plugin services.
     *
     * @return A collection of all the game plugin services.
     */
    private Collection<? extends IGamePluginService> getPluginServices() {
        return this.gamePluginServices;
    }

    /**
     * Gets all the entity processing services.
     *
     * @return A collection of all the entity processing services.
     */
    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return this.entityProcessingServices;
    }

    /**
     * Gets all the post entity processing services.
     *
     * @return A collection of all the post entity processing services.
     */
    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return this.postEntityProcessingServices;
    }
}