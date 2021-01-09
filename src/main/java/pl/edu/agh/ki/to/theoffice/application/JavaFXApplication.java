package pl.edu.agh.ki.to.theoffice.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import pl.edu.agh.ki.to.theoffice.controller.GameSetupController;

@Slf4j
public class JavaFXApplication extends Application {

    private ConfigurableApplicationContext context;

    private JavaFXSceneLoader sceneLoader;

    @Override
    public void init() {
        log.debug("Initializing JavaFX Application");
        final String[] args = getParameters().getRaw().toArray(new String[0]);

        this.context = new SpringApplicationBuilder()
                .sources(pl.edu.agh.ki.to.theoffice.Application.class)
                .run(args);

        this.sceneLoader = this.context.getBean(JavaFXSceneLoader.class);
    }

    @Override
    public void start(Stage stage) {
        sceneLoader.switchScene(stage, GameSetupController.class);
    }

    @Override
    public void stop() {
        log.debug("Stopping JavaFX Application");
        this.context.close();
        Platform.exit();
    }

}
