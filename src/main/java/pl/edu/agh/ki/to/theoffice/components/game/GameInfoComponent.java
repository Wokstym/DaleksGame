package pl.edu.agh.ki.to.theoffice.components.game;

import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;
import pl.edu.agh.ki.to.theoffice.common.component.FXMLUtils;
import pl.edu.agh.ki.to.theoffice.common.component.ImageUtils;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;

@Component
public class GameInfoComponent extends VBox implements FXMLComponent, MapChangeListener<GamePowerup, Integer> {

    public static final String FXML_SOURCE = "/view/game/game-info.fxml";

    @FXML
    public Text bombCount;

    @FXML
    public Text teleportCount;

    @FXML
    public TilePane bombButton;

    @FXML
    public TilePane teleportButton;

    public GameInfoComponent() {
        FXMLUtils.loadFXML(this);
    }

    @Override
    public String getFxmlResourceFile() {
        return FXML_SOURCE;
    }

    @Override
    public void onChanged(Change<? extends GamePowerup, ? extends Integer> change) {

        Text whichToUpdate = switch ((GamePowerup) change.getKey()) {
            case BOMB -> bombCount;
            case TELEPORT -> teleportCount;
        };

        whichToUpdate.setText(Integer.toString(change.getValueAdded()));
    }

    public void setPowerupsListeners(PowerupClicked lambda) {
        bombButton.setOnMouseClicked(mouseEvent -> lambda.powerupClicked(GamePowerup.BOMB));
        teleportButton.setOnMouseClicked(mouseEvent -> lambda.powerupClicked(GamePowerup.TELEPORT));
    }

    public void setEffects() {
        ImageUtils.setShadowEffect(bombButton, Color.GREY);
        ImageUtils.setShadowEffect(teleportButton, Color.GREY);
    }

    @FunctionalInterface
    public interface PowerupClicked {
        void powerupClicked(GamePowerup gamePowerup);
    }
}
