package pl.edu.agh.ki.to.theoffice.components.game;

import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;
import pl.edu.agh.ki.to.theoffice.common.component.FXMLUtils;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;

@Component
public class GameInfoComponent extends VBox implements FXMLComponent, MapChangeListener<GamePowerup, Integer> {

    public static final String FXML_SOURCE = "/view/game/game-info.fxml";

    @FXML
    public Text bombCount;

    @FXML
    public Text teleportCount;

    public GameInfoComponent() {
        FXMLUtils.loadFXML(this);
    }

    @Override
    public String getFxmlResourceFile() {
        return FXML_SOURCE;
    }

    @Override
    public void onChanged(Change<? extends GamePowerup, ? extends Integer> change) {

        Text whichToUpdate = switch ((GamePowerup)change.getKey()){
            case BOMB -> bombCount;
            case TELEPORT -> teleportCount;
        };

        whichToUpdate.setText(Integer.toString(change.getValueAdded()));
    }
}
