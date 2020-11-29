package pl.edu.agh.ki.to.theoffice.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class StageListener implements ApplicationListener<JavaFXApplication.StageReadyEvent> {

    private final String appTitle;
    private final Resource resource;
    private final ApplicationContext context;

    public StageListener(@Value("${spring.application.ui.title}") String appTitle,
                         @Value("classpath:view/game.fxml") Resource resource,
                         ApplicationContext context) {
        this.appTitle = appTitle;
        this.resource = resource;
        this.context = context;
    }

    @Override
    @SneakyThrows
    public void onApplicationEvent(JavaFXApplication.StageReadyEvent event) {
        Stage stage = event.getStage();
        URL url = this.resource.getURL();
        
        FXMLLoader loader = new FXMLLoader(url);
        loader.setControllerFactory(context::getBean);

        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.setTitle(appTitle);

        stage.show();
    }

}
