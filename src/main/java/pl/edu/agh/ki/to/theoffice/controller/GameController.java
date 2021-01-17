package pl.edu.agh.ki.to.theoffice.controller;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import pl.edu.agh.ki.to.theoffice.common.command.MovePlayerCommand;
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

    private static final String LOST_MESSAGE = "Przegrana!";
    private static final String PLAY_AGAIN_CAPTION = "Zagraj ponownie";
    private static final String WON_MESSAGE = "Wygrana!";
    private static final String NEXT_STAGE_CAPTION = "Kolejny poziom";

    private final GameManager gameManager;
    private Game game;

    @FXML
    private GameInfoComponent info;

    @FXML
    private GameControlsComponent controls;

    @FXML
    private GameMapComponent map;

    private final ChangeListener<? super Number> scoreListener = (obs, oldVal, newVal) -> {
        log.debug("score changed: {} -> {}", oldVal, newVal);
        this.info.setScore(newVal.intValue());
    };
    private final ChangeListener<? super Number> levelListener = (obs, oldVal, newVal) -> this.info.setLevel(newVal.intValue());

    private final ChangeListener<GameState> gameStateListener = (obs, oldVal, newVal) -> {
        log.debug("gameState changed: {}, {}, {}", obs, oldVal, newVal);

        switch (newVal) {
            case LOST -> handleGameLost();
            case WON -> handleGameWon();
        }
    };

    private void handleGameWon() {
        log.debug("Game over - won");
        showGameWinDialog();
    }

    private void handleGameLost() {
        controls.removeArrowListeners();
        log.debug("Game over - lost");
        showGameLostDialog();
    }


    @FXML
    public void initialize() {
        log.debug("controller initialized");
        this.game = gameManager.getCurrentGame();
        final MapProperties mapProperties = this.game.getMapProperties();

        this.map.initMap(mapProperties.getWidth(), mapProperties.getHeight(), game.getEntities());

        this.info.setPowerups(this.game.getPlayerEntity().getPowerups());
        this.info.setLevel(this.game.getLevel().get());
        this.info.setScore(this.game.getScore().get());

        setupEffects();
        setupListeners();

        log.debug("game: {}", game);
    }

    @FXML
    public void handleOnKeyPressed(KeyEvent keyEvent) {
        log.debug("Handled key: {}", keyEvent.getCode().name());
        if (game.getGameState().getValue() != GameState.IN_PROGRESS) {
            return;
        }

        Direction.fromKeyCode(keyEvent.getCode())
                .ifPresent(this::movePLayer);
    }

    private void setupEffects() {
        controls.setEffects();
        info.setEffects();
    }


    private void setupListeners() {
        this.game.getEntities().addListener(map);
        this.game.getPlayerEntity().getPowerups().addListener(info);

        this.controls.setArrowListeners(this::movePLayer);
        this.info.setPowerupsListeners(game::usePowerup);

        this.game.getLevel().addListener(levelListener);
        this.game.getScore().addListener(scoreListener);
        this.game.getGameState().addListener(gameStateListener);
    }

    private void movePLayer(Direction direction) {
        MovePlayerCommand command = new MovePlayerCommand(game, direction);
        game.execute(command);
    }

    private void removeListeners() {
        this.game.getEntities().removeListener(map);
        this.game.getPlayerEntity().getPowerups().removeListener(info);

        this.controls.removeArrowListeners();
        this.info.removePowerupsListeners();

        this.game.getLevel().removeListener(levelListener);
        this.game.getScore().removeListener(scoreListener);
        this.game.getGameState().removeListener(gameStateListener);
    }

    private void showGameLostDialog() {
        Dialog<Boolean> dialog = new Dialog<>();


        dialog.setTitle(LOST_MESSAGE);
        dialog.getDialogPane().getButtonTypes().add(new ButtonType(PLAY_AGAIN_CAPTION, ButtonBar.ButtonData.OK_DONE));

        dialog.showAndWait();

        removeListeners();

        this.game = this.gameManager.createNewGame(this.game.getDifficulty());
        initialize();
    }

    private void showGameWinDialog() {
        Dialog<Boolean> dialog = new Dialog<>();

        dialog.setTitle(WON_MESSAGE);
        dialog.getDialogPane().getButtonTypes().add(new ButtonType(NEXT_STAGE_CAPTION, ButtonBar.ButtonData.OK_DONE));

        dialog.showAndWait();

        removeListeners();

        this.game = this.gameManager.nextLevel();
        initialize();
    }


}
