package pl.edu.agh.ki.to.theoffice.components.game;

import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import pl.edu.agh.ki.to.theoffice.common.component.FXMLUtils;
import pl.edu.agh.ki.to.theoffice.domain.game.GameChangeListener;

@Component
public class GameInfoComponent extends VBox implements FXMLComponent, GameChangeListener {

    public GameInfoComponent() {
        FXMLUtils.loadFXML(this);
    }

    @Override
    public String getFxmlResourceFile() {
        return "/view/game/game-info.fxml";
    }

}
