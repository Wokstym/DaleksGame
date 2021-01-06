package pl.edu.agh.ki.to.theoffice.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import pl.edu.agh.ki.to.theoffice.application.JavaFXSceneLoader;
import pl.edu.agh.ki.to.theoffice.domain.game.GameDifficulty;
import pl.edu.agh.ki.to.theoffice.domain.game.GameManager;

@Slf4j
@Component
@FxmlView("/view/game-setup/game-setup.fxml")
@RequiredArgsConstructor
public class GameSetupController {

    private final JavaFXSceneLoader sceneLoader;
    private final GameManager gameManager;

    @FXML
    private Button startGameButton;

    @FXML
    private ComboBox<GameDifficulty> difficulty;

    @FXML
    public void initialize() {
        startGameButton.setOnAction(this::startGame);

        difficulty.setValue(GameDifficulty.EASY);
    }

    @SneakyThrows
    private void startGame(ActionEvent event) {
        log.debug("Attempt to start game with difficulty: {}", difficulty.getValue());
        if (difficulty.getValue() == null) {
            return;
        }

        this.gameManager.createNewGame(difficulty.getValue());

        final Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        sceneLoader.switchScene(stage, GameController.class);
    }

}
