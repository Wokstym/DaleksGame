package pl.edu.agh.ki.to.theoffice.controller;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import pl.edu.agh.ki.to.theoffice.common.observer.Change;
import pl.edu.agh.ki.to.theoffice.components.game.GameControlsComponent;
import pl.edu.agh.ki.to.theoffice.components.game.GameMapComponent;
import pl.edu.agh.ki.to.theoffice.domain.game.GameProperties;
import pl.edu.agh.ki.to.theoffice.domain.game.GameStorage;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.game.GameState;

import static pl.edu.agh.ki.to.theoffice.domain.map.Location.Direction;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameStorage gameStorage;

    @FXML
    public GameControlsComponent controls;

    @FXML
    private GameMapComponent map;

    @FXML
    public void initialize() {
        GameProperties properties = GameProperties.builder()
                .enemies(1)
                .build();

        gameStorage.createNewGame(properties);

        controls.initArrows();

        setupListeners();
    }

    @FXML
    public void handleOnKeyPressed(KeyEvent keyEvent) {
        log.debug("Handled key: {}", keyEvent.getCode().name());
        Direction.fromKeyCode(keyEvent.getCode())
                .ifPresent(gameStorage::movePlayer);
    }

    private void setupListeners() {
//        game.addListener(this);
//        game.addListener(map);
        controls.setArrowListeners(gameStorage::movePlayer);
    }

//    public void onGameStateChanged(Change<GameState> change) {
//        if (change.getStateAfter() == GameState.LOST) {
//            game.removeListener(map);
//            controls.removeArrowListeners();
//        }
//    }
}
