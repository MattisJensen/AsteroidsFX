package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.processing.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.processing.IPostEntityProcessingService;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

public class GameLoopJavaFX extends Application {
    private GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();
    private double lastSystemTime = 0;

    @Override
    public void start(Stage window) throws Exception {
        Text text = new Text(10, 20, "Destroyed asteroids: 0");
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().add(text);

        Scene scene = new Scene(gameWindow);
        setKeyEvents(scene);

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(gameData, world);
        }

        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }

        render();

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

        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }

        removeDeletedEntities();
        addNewEntities();
    }

    /**
     * Updates the delta game time.
     *
     * @param currentTimeInMilliseconds The current time in milliseconds.
     */
    public void updateDeltaGameTime(double currentTimeInMilliseconds) {
        gameData.setDeltaTime((currentTimeInMilliseconds - lastSystemTime) / 1000f);
        lastSystemTime = currentTimeInMilliseconds;
    }

    /**
     * Removes deleted entities from the game window and the polygons map.
     */
    public void removeDeletedEntities() {
        for (Entity entity : polygons.keySet()) {
            if (!world.getEntities().contains(entity)) {
                Polygon p = polygons.get(entity);
                if (gameWindow.getChildren().contains(p)) {
                    gameWindow.getChildren().remove(p);
                }
                polygons.remove(entity);
            }
        }
    }

    /**
     * Adds new entities to the game window and the polygons map.
     */
    public void addNewEntities() {
        for (Entity entity : world.getEntities()) {
            if (!polygons.containsKey(entity)) {
                Polygon polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }
        }
    }

    /**
     * Draws the entities of the game.
     */
    private void draw() {
        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            polygon.setTranslateX(entity.getXCoordinate());
            polygon.setTranslateY(entity.getYCoordinate());
            polygon.setRotate(entity.getRotation());
        }
    }

    /**
     * Sets the key event for a scene.
     *
     * @param scene The scene to set the key check for.
     */
    public void setKeyEvents(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }
        });
    }

    /**
     * Gets all the game plugin services.
     *
     * @return A collection of all the game plugin services.
     */
    private Collection<? extends IGamePluginService> getPluginServices() {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    /**
     * Gets all the entity processing services.
     *
     * @return A collection of all the entity processing services.
     */
    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    /**
     * Gets all the post entity processing services.
     *
     * @return A collection of all the post entity processing services.
     */
    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}