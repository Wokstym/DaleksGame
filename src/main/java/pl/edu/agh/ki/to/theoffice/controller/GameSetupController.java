package pl.edu.agh.ki.to.theoffice.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FxmlView("/view/game-setup/game-setup.fxml")
@RequiredArgsConstructor
public class GameSetupController {

    private final FxWeaver fxWeaver;

    @FXML
    private Button startGameButton;

    @FXML
    public void initialize() {
        startGameButton.setOnAction(this::startGame);
    }

    private void startGame(ActionEvent event) {
        final Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        final Parent root = fxWeaver.loadView(GameController.class);
        final Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.centerOnScreen();

        root.requestFocus();
        stage.show();
    }

}
