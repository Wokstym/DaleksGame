package pl.edu.agh.ki.to.theoffice.controller;

import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pl.edu.agh.ki.to.theoffice.components.ControlsPane;
import pl.edu.agh.ki.to.theoffice.components.IconProvider;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.game.GameProperties;
import pl.edu.agh.ki.to.theoffice.domain.game.GameState;
import pl.edu.agh.ki.to.theoffice.domain.map.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;

import java.util.HashMap;
import java.util.List;

import static pl.edu.agh.ki.to.theoffice.components.ComponentsUtils.prepareImageView;

@Component
public class GameController {

    private static final int ELEMENT_SIZE = 40;

    @FXML
    public TilePane controls;
    @FXML
    private TilePane stageTile;

    private Game game;
    private HashMap<Location, ImageView> images;
    private ControlsPane controlsPane;

    @FXML
    public void initialize() {

        GameProperties properties = GameProperties.builder()
                .enemies(10)
                .build();
        game = Game.fromProperties(properties);

        controlsPane = new ControlsPane(controls);

        int rowsNr = properties.getMapProperties().getHeight();
        int columnsNr = properties.getMapProperties().getWidth();

        setBoardSize(columnsNr, rowsNr);

        images = new HashMap<>();
        for (Location location : Location.generateLocationsWithinBoundsWithRespectOfLeftBottomCorner(0, columnsNr, 0, rowsNr)) {
            List<EntityType> entityTypes = game.getEntities().get(location);

            Image image = CollectionUtils.isEmpty(entityTypes) ? IconProvider.EMPTY.getImage() : IconProvider.imageOf(entityTypes.get(0));
            ImageView element = prepareImageView(image, ELEMENT_SIZE);

            stageTile.getChildren().add(element);
            images.put(location, element);
        }

        setupListeners();
    }

    private void setBoardSize(int columnsNr, int rowsNr) {
        int pixelWidth = columnsNr * ELEMENT_SIZE;
        int pixelHeight = rowsNr * ELEMENT_SIZE;

        stageTile.setMaxWidth(pixelWidth);
        stageTile.setMaxHeight(pixelHeight);
        stageTile.setMinWidth(pixelWidth);
        stageTile.setMinHeight(pixelHeight);
    }

    private void setupListeners() {
        var mapChangeListener = getMapChangeListener();
        game.getEntities().addListener(mapChangeListener);

        controlsPane.setArrowListeners(direction -> {
            game.movePlayer(direction);
        });

        game.getGameState().addListener((observableValue, stateBefore, stateAfter) -> {
            if (stateAfter == GameState.LOST) {
                game.getEntities().removeListener(mapChangeListener);
                controlsPane.removeArrowListeners();

                ImageView imageAtChangedPosition = images.get(game.getPlayerLocation().get());
                imageAtChangedPosition.setImage(IconProvider.DEAD_PLAYER.getImage()); // TODO change so collisions also trigger listeners
            }
        });
    }

    private MapChangeListener<Location, List<EntityType>> getMapChangeListener() {
        return change -> {

            ImageView imageAtChangedPosition = images.get(change.getKey());

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
