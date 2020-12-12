package pl.edu.agh.ki.to.theoffice.controller;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.edu.agh.ki.to.theoffice.components.game.GameControlsComponent;
import pl.edu.agh.ki.to.theoffice.components.game.GameMapComponent;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.game.GameProperties;
import pl.edu.agh.ki.to.theoffice.domain.game.GameState;

import static pl.edu.agh.ki.to.theoffice.domain.map.Location.Direction;

@Slf4j
@Component
public class GameController {

    @FXML
    public GameControlsComponent controls;

    @FXML
    private GameMapComponent map;

    private Game game;

    @FXML
    public void initialize() {

        GameProperties properties = GameProperties.builder()
                .enemies(1)
                .build();
        game = Game.fromProperties(properties);

        controls.initArrows();
        map.initBoard(game);

        setupListeners();
    }

    @FXML
    public void handleOnKeyPressed(KeyEvent keyEvent) {
        log.debug("Handled key: {}", keyEvent.getCode().name());
        Direction.fromKeyCode(keyEvent.getCode())
                .ifPresent(direction -> game.movePlayer(direction));
    }

    private void setupListeners() {
        game.getEntities().addListener(map);

        controls.setArrowListeners(direction -> game.movePlayer(direction));

        game.getGameState().addListener((observableValue, stateBefore, stateAfter) -> {
            if (stateAfter == GameState.LOST) {
                game.getEntities().removeListener(map);
                controls.removeArrowListeners();
            }
        });


    }

}
