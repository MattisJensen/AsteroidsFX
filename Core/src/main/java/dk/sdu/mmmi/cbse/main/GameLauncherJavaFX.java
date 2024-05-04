package dk.sdu.mmmi.cbse.main;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * GameLauncherJavaFX: Launches the java fx game loop
 */
public class GameLauncherJavaFX extends Application {
    AnnotationConfigApplicationContext configApplicationContext;

    @Override
    public void start(Stage window) throws Exception {
        this.configApplicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);

        GameLoopJavaFX gameLoopJavaFX = this.configApplicationContext.getBean(GameLoopJavaFX.class);
        gameLoopJavaFX.start(window);
    }
}
