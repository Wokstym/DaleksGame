package pl.edu.agh.ki.to.theoffice.controller;

import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Component;
import pl.edu.agh.ki.to.theoffice.components.BoardPane;
import pl.edu.agh.ki.to.theoffice.components.ControlsPane;
import pl.edu.agh.ki.to.theoffice.components.IconProvider;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.game.GameProperties;
import pl.edu.agh.ki.to.theoffice.domain.game.GameState;
import pl.edu.agh.ki.to.theoffice.domain.map.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;

import java.util.List;

@Component
public class GameController {


    @FXML
    public ControlsPane controls;
    @FXML
    private BoardPane board;

    private Game game;

    @FXML
    public void initialize() {

        GameProperties properties = GameProperties.builder()
                .enemies(10)
                .build();
        game = Game.fromProperties(properties);

        int rowsNr = properties.getMapProperties().getHeight();
        int columnsNr = properties.getMapProperties().getWidth();

        controls.initArrows();

        board.setBoardSize(columnsNr, rowsNr);
        board.populateBoard(game.getEntities());

        setupListeners();
    }

    private void setupListeners() {
        var mapChangeListener = getMapChangeListener();
        game.getEntities().addListener(mapChangeListener);

        controls.setArrowListeners(direction -> game.movePlayer(direction));

        game.getGameState().addListener((observableValue, stateBefore, stateAfter) -> {
            if (stateAfter == GameState.LOST) {
                game.getEntities().removeListener(mapChangeListener);
                controls.removeArrowListeners();

                ImageView imageAtChangedPosition = board.getImageViewAt(game.getPlayerLocation().get());
                imageAtChangedPosition.setImage(IconProvider.DEAD_PLAYER.getImage()); // TODO change so collisions also trigger listeners
            }
        });
    }

    private MapChangeListener<Location, List<EntityType>> getMapChangeListener() {
        return change -> {

            ImageView imageAtChangedPosition = board.getImageViewAt(change.getKey());

            if (change.wasRemoved()) {
                Image image = IconProvider.EMPTY.getImage();
                imageAtChangedPosition.setImage(image);
            }

            if (change.wasAdded()) {
                Image image = IconProvider.imageOf(change.getValueAdded().get(0));
                imageAtChangedPosition.setImage(image);
            }
        };
    }
}
