package pl.edu.agh.ki.to.theoffice.components.game;

import javafx.scene.layout.VBox;
import pl.edu.agh.ki.to.theoffice.common.component.FXMLUtils;

public class GameInfoComponent extends VBox implements FXMLComponent {

    public GameInfoComponent() {
        FXMLUtils.loadFXML(this);
    }

    @Override
    public String getFxmlResourceFile() {
        return "/view/game/game-info.fxml";
    }

}
