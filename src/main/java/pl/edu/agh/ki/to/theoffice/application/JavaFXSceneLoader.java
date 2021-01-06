package pl.edu.agh.ki.to.theoffice.application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JavaFXSceneLoader {

    private final String appTitle;
    private final FxWeaver fxWeaver;

    public JavaFXSceneLoader(@Value("${spring.application.ui.title:Application}") String appTitle,
                             FxWeaver fxWeaver) {
        this.appTitle = appTitle;
        this.fxWeaver = fxWeaver;
    }

    public void switchScene(Stage stage, Class<?> controllerClazz) {
        Parent root = fxWeaver.loadView(controllerClazz);
        Scene scene = new Scene(root);

        stage.setTitle(appTitle);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();

        root.requestFocus();
        stage.show();
    }

}
