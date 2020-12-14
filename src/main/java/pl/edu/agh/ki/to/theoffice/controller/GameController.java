package pl.edu.agh.ki.to.theoffice.controller;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import pl.edu.agh.ki.to.theoffice.components.game.GameControlsComponent;
import pl.edu.agh.ki.to.theoffice.components.game.GameInfoComponent;
import pl.edu.agh.ki.to.theoffice.components.game.GameMapComponent;
import pl.edu.agh.ki.to.theoffice.domain.game.*;

import static pl.edu.agh.ki.to.theoffice.domain.map.Location.Direction;

@Slf4j
@Component
@FxmlView("/view/game/game.fxml")
public class GameController {

    private Game game;

    @FXML
    public GameInfoComponent info;

    @FXML
    public GameControlsComponent controls;

    @FXML
    private GameMapComponent map;

    @FXML
    public void initialize() {
        GameProperties properties = GameProperties.builder()
                .enemies(1)
                .playerProperties(GameProperties.GamePlayerProperties.builder()
                        .powerup(GamePowerup.BOMB, 1)
                        .powerup(GamePowerup.TELEPORT, 1)
                        .build())
                .build();

        game = GameFactory.fromProperties(properties);

        var mapProperties = game.getGameProperties().getMapProperties();
        map.initMap(mapProperties.getWidth(), mapProperties.getHeight(), game.getEntities());

        setupListeners();
    }

    @FXML
    public void handleOnKeyPressed(KeyEvent keyEvent) {
        log.debug("Handled key: {}", keyEvent.getCode().name());
        if(game.getGameState().getValue() != GameState.IN_PROGRESS) {
            return;
        }

        Direction.fromKeyCode(keyEvent.getCode())
                .ifPresent(game::movePlayer);
    }

    private void setupListeners() {
        game.getEntities().addListener(map);
        controls.setArrowListeners(game::movePlayer);

        game.getGameState().addListener((val, oldState, newState) -> {
            if(newState == GameState.LOST) {
                controls.removeArrowListeners();
            }
        });
    }

}
