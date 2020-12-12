package pl.edu.agh.ki.to.theoffice.controller;

import javafx.fxml.FXML;
import org.springframework.stereotype.Component;
import pl.edu.agh.ki.to.theoffice.components.game.GameControlsComponent;
import pl.edu.agh.ki.to.theoffice.components.game.GameMapComponent;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.game.GameProperties;
import pl.edu.agh.ki.to.theoffice.domain.game.GameState;

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
                .enemies(10)
                .build();
        game = Game.fromProperties(properties);

        controls.initArrows();
        map.initBoard(game);

//        board.setBoardSize(columnsNr, rowsNr);
//        board.populateBoard(game.getEntities());

        setupListeners();
    }

    private void setupListeners() {
        game.getEntities().addListener(map);

        controls.setArrowListeners(direction -> game.movePlayer(direction));

        game.getGameState().addListener((observableValue, stateBefore, stateAfter) -> {
            if (stateAfter == GameState.LOST) {
                game.getEntities().removeListener(map);
                controls.removeArrowListeners();

//                ImageView imageAtChangedPosition = board.getImageViewAt(game.getPlayerLocation().get());
//                imageAtChangedPosition.setImage(IconProvider.DEAD_PLAYER.getImage()); // TODO change so collisions also trigger listeners
            }
        });
    }

}
