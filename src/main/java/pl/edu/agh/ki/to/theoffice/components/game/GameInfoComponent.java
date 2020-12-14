package pl.edu.agh.ki.to.theoffice.components.game;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;
import pl.edu.agh.ki.to.theoffice.common.component.FXMLUtils;

@Component
public class GameInfoComponent extends VBox implements FXMLComponent  {

    @FXML
    public Text bombCount;

    @FXML
    public Text teleportCount;

    public GameInfoComponent() {
        FXMLUtils.loadFXML(this);
    }

    @Override
    public String getFxmlResourceFile() {
        return "/view/game/game-info.fxml";
    }

}
