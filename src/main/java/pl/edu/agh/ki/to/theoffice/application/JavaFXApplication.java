package pl.edu.agh.ki.to.theoffice.application;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

@Slf4j
public class JavaFXApplication extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        log.info("Initializing JavaFX Application");
        final String[] args = getParameters().getRaw().toArray(new String[0]);

        final ApplicationContextInitializer<GenericApplicationContext> initializer = ac -> {
            ac.registerBean("contextApplication", Application.class, () -> JavaFXApplication.this);
            ac.registerBean(Parameters.class, this::getParameters);
            ac.registerBean(HostServices.class, this::getHostServices);
        };

        this.context = new SpringApplicationBuilder()
                .sources(pl.edu.agh.ki.to.theoffice.Application.class)
                .initializers(initializer)
                .run(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.context.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() throws Exception {
        this.context.close();
        Platform.exit();
    }

    static class StageReadyEvent extends ApplicationEvent {

        public Stage getStage() {
            return (Stage) super.getSource();
        }

        public StageReadyEvent(Stage source) {
            super(source);
        }

    }
}
