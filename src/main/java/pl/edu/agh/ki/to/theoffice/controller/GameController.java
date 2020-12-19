package pl.edu.agh.ki.to.theoffice.controller;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.ki.to.theoffice.components.game.GameControlsComponent;
import pl.edu.agh.ki.to.theoffice.components.game.GameInfoComponent;
import pl.edu.agh.ki.to.theoffice.components.game.GameMapComponent;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.game.GameState;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GameMapProperties;

import static pl.edu.agh.ki.to.theoffice.domain.map.Location.Direction;

@Slf4j
@Component
@FxmlView("/view/game/game.fxml")
public class GameController {

    @FXML
    public GameInfoComponent info;

    @FXML
    public GameControlsComponent controls;

    @FXML
    private GameMapComponent map;

    private final GameMapProperties gameMapProperties;
    private final Game game;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") // podejżane, ale działa normalnie, todo zweryfikować
    public GameController(GameMapProperties gameMapProperties, Game game) {
        this.gameMapProperties = gameMapProperties;
        this.game = game;
    }

    @FXML
    public void initialize() {
        map.initMap(gameMapProperties.getWidth(), gameMapProperties.getHeight(), game.getEntities());
        setupListeners();
    }

    @FXML
    public void handleOnKeyPressed(KeyEvent keyEvent) {
        log.debug("Handled key: {}", keyEvent.getCode().name());
        if (game.getGameState().getValue() != GameState.IN_PROGRESS) {
            return;
        }

        Direction.fromKeyCode(keyEvent.getCode())
                .ifPresent(game::movePlayer);
    }

    private void setupListeners() {
        game.getEntities().addListener(map);
        game.getPlayerEntity().getPowerups().addListener(info);
        controls.setArrowListeners(game::movePlayer);

        game.getGameState().addListener((val, oldState, newState) -> {
            if (newState == GameState.LOST) {
                controls.removeArrowListeners();
            }
        });
    }

}
