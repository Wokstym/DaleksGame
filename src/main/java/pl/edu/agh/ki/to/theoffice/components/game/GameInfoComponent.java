package pl.edu.agh.ki.to.theoffice.components.game;

import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;
import pl.edu.agh.ki.to.theoffice.common.component.FXMLUtils;
import pl.edu.agh.ki.to.theoffice.common.component.ImageUtils;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;

import java.util.Map;
import java.util.function.Consumer;

@Component
public class GameInfoComponent extends VBox implements FXMLComponent, MapChangeListener<GamePowerup, Integer> {

    public static final String FXML_SOURCE = "/view/game/game-info.fxml";

    @FXML
    public Text reverseTimeCount;

    @FXML
    private Text bombCount;

    @FXML
    private Text teleportCount;

    @FXML
    public TilePane reverseTimeButton;

    @FXML
    private TilePane bombButton;

    @FXML
    private TilePane teleportButton;

    @FXML
    private Label levelInfo;

    @FXML
    private Label scoreInfo;

    public GameInfoComponent() {
        FXMLUtils.loadFXML(this);
    }

    @Override
    public String getFxmlResourceFile() {
        return FXML_SOURCE;
    }

    @Override
    @SuppressWarnings("RedundantCast")
    public void onChanged(Change<? extends GamePowerup, ? extends Integer> change) {

        Text whichToUpdate = switch ((GamePowerup) change.getKey()) {
            case BOMB -> bombCount;
            case TELEPORT -> teleportCount;
            case REVERSE_TIME -> reverseTimeCount;
        };

        whichToUpdate.setText(Integer.toString(change.getValueAdded()));
    }

    public void setPowerupsListeners(Consumer<GamePowerup> lambda) {
        bombButton.setOnMouseClicked(mouseEvent -> lambda.accept(GamePowerup.BOMB));
        teleportButton.setOnMouseClicked(mouseEvent -> lambda.accept(GamePowerup.TELEPORT));
        reverseTimeButton.setOnMouseClicked(mouseEvent -> lambda.accept(GamePowerup.REVERSE_TIME));
    }

    public void removePowerupsListeners() {
        bombButton.setOnMouseClicked(null);
        teleportButton.setOnMouseClicked(null);
        reverseTimeButton.setOnMouseClicked(null);
    }

    public void setPowerups(Map<GamePowerup, Integer> powerups) {
        bombCount.setText(String.valueOf(powerups.getOrDefault(GamePowerup.BOMB, 0)));
        teleportCount.setText(String.valueOf(powerups.getOrDefault(GamePowerup.TELEPORT, 0)));
        reverseTimeCount.setText(String.valueOf(powerups.getOrDefault(GamePowerup.REVERSE_TIME, 0)));
    }

    public void setLevel(int level) {
        this.levelInfo.setText(String.valueOf(level));
    }

    public void setScore(int score) {
        this.scoreInfo.setText(String.valueOf(score));
    }

    public void setEffects() {
        ImageUtils.setShadowEffect(bombButton, Color.GREY);
        ImageUtils.setShadowEffect(teleportButton, Color.GREY);
        ImageUtils.setShadowEffect(reverseTimeButton, Color.GREY);
    }
}
