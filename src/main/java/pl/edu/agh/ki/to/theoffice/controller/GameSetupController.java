package pl.edu.agh.ki.to.theoffice.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import pl.edu.agh.ki.to.theoffice.domain.game.GameDifficulty;
import pl.edu.agh.ki.to.theoffice.domain.game.GameManager;

@Slf4j
@Component
@FxmlView("/view/game-setup/game-setup.fxml")
@RequiredArgsConstructor
public class GameSetupController {

    private final FxWeaver fxWeaver;
    private final GameManager gameManager;

    @FXML
    private Button startGameButton;

    @FXML
    private ComboBox<GameDifficulty> difficulty;

    @FXML
    public void initialize() {
        startGameButton.setOnAction(this::startGame);
    }

    @SneakyThrows
    private void startGame(ActionEvent event) {
        log.debug("Attempt to start game with difficulty: {}", difficulty.getValue());
        if (difficulty.getValue() == null) {
            return;
        }

        this.gameManager.createNewGame(difficulty.getValue());

        final Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        final Parent root = fxWeaver.loadView(GameController.class);
        final Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();

        root.requestFocus();
        stage.show();
    }

}
