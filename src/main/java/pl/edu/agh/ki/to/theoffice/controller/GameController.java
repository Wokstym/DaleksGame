package pl.edu.agh.ki.to.theoffice.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import pl.edu.agh.ki.to.theoffice.components.game.GameControlsComponent;
import pl.edu.agh.ki.to.theoffice.components.game.GameInfoComponent;
import pl.edu.agh.ki.to.theoffice.components.game.GameMapComponent;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.game.GameManager;
import pl.edu.agh.ki.to.theoffice.domain.game.GameState;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.MapProperties;

import static pl.edu.agh.ki.to.theoffice.domain.map.Location.Direction;

@Slf4j
@Component
@FxmlView("/view/game/game.fxml")
@RequiredArgsConstructor
public class GameController {

    private final GameManager gameManager;
    @FXML
    public GameInfoComponent info;
    @FXML
    public GameControlsComponent controls;
    @FXML
    private GameMapComponent map;
    private Game game;

    @FXML
    public void initialize() {
        this.game = gameManager.getCurrentGame();
        final MapProperties mapProperties = this.game.getMapProperties();

        map.initMap(mapProperties.getWidth(), mapProperties.getHeight(), game.getEntities());
        setupListeners();
        controls.setEffects();
        info.setEffects();

        log.debug("game: {}", game);
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
        info.setPowerupsListeners(game::usePowerup);

        game.getGameState().addListener((val, oldState, newState) -> {
            if (newState == GameState.LOST) {
                controls.removeArrowListeners();
                log.debug("Game over - lost");
                showGameLostDialog();
            }

            if (newState == GameState.WON) {
                log.debug("Game over - won");
                showGameWinDialog();
            }
        });
    }

    private void showGameLostDialog() {
        Dialog<Boolean> dialog = new Dialog<>();

        dialog.setTitle("Przegrana!");
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("Zagraj ponownie", ButtonBar.ButtonData.OK_DONE));

        // todo: add checking result?
        dialog.showAndWait();

        this.game.getEntities().removeListener(map);
        this.game.getPlayerEntity().getPowerups().removeListener(info);

        this.game = this.gameManager.createNewGame(this.game.getDifficulty());
        initialize();
    }

    private void showGameWinDialog() {
        Dialog<Boolean> dialog = new Dialog<>();

        dialog.setTitle("Wygrana!");
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("Kolejny poziom", ButtonBar.ButtonData.OK_DONE));

        // todo: add checking result?
        dialog.showAndWait();

        this.game.getEntities().removeListener(map);
        this.game.getPlayerEntity().getPowerups().removeListener(info);

        this.game = this.gameManager.nextLevel();
        initialize();
    }

}
