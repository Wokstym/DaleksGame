package pl.edu.agh.ki.to.theoffice.controller;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.ki.to.theoffice.components.game.GameControlsComponent;
import pl.edu.agh.ki.to.theoffice.components.game.GameMapComponent;
import pl.edu.agh.ki.to.theoffice.domain.game.GameProperties;
import pl.edu.agh.ki.to.theoffice.domain.game.GameStorage;

import static pl.edu.agh.ki.to.theoffice.domain.map.Location.Direction;

@Slf4j
@Component
@FxmlView("/view/game/game.fxml")
public class GameController {

    private final GameStorage gameStorage;

    @Autowired
    public GameController(GameStorage gameStorage) {
        this.gameStorage = gameStorage;
        log.debug("GameController init");
    }

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
