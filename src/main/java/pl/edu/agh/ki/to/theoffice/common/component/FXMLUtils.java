package pl.edu.agh.ki.to.theoffice.common.component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.extern.slf4j.Slf4j;
import pl.edu.agh.ki.to.theoffice.components.game.FXMLComponent;

import java.io.IOException;

@Slf4j
public class FXMLUtils {

    public static <T extends Parent & FXMLComponent> void loadFXML(T component) {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(component);
        loader.setControllerFactory(theClass -> component);

        log.debug("Loading FXML file: {}", component.getFxmlResourceFile());
        try {
            loader.load(component.getClass().getResourceAsStream(component.getFxmlResourceFile()));
        } catch (Exception e) {
            log.error("Could not load FXML file: {}", component.getFxmlResourceFile());
            throw new RuntimeException(e);
        }
    }

}
