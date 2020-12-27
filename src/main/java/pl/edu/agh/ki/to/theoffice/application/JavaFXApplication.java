package pl.edu.agh.ki.to.theoffice.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import pl.edu.agh.ki.to.theoffice.controller.GameSetupController;

@Slf4j
public class JavaFXApplication extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        log.info("Initializing JavaFX Application");
        final String[] args = getParameters().getRaw().toArray(new String[0]);

        this.context = new SpringApplicationBuilder()
                .sources(pl.edu.agh.ki.to.theoffice.Application.class)
                .run(args);
    }

    @Override
    public void start(Stage stage) {
        FxWeaver fxWeaver = context.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(GameSetupController.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);

        root.requestFocus();
        stage.show();
    }

    @Override
    public void stop() {
        this.context.close();
        Platform.exit();
    }

}
