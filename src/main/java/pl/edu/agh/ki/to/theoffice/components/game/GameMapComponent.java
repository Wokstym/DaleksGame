package pl.edu.agh.ki.to.theoffice.components.game;

import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import pl.edu.agh.ki.to.theoffice.common.component.FXMLUtils;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.game.GameState;
import pl.edu.agh.ki.to.theoffice.domain.map.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;

import java.util.List;

public class GameMapComponent extends VBox implements FXMLComponent, MapChangeListener<Location, List<EntityType>> {

    @FXML
    private BoardPane board;

    public GameMapComponent() {
        FXMLUtils.loadFXML(this);
    }

    @Override
    public String getFxmlResourceFile() {
        return "/view/game/game-map.fxml";
    }

    @Override
    public void onChanged(Change<? extends Location, ? extends List<EntityType>> change) {
        this.board.onChanged(change);
    }

    public void initBoard(Game game) {
        var mapProperties = game.getGameProperties().getMapProperties();

        board.setBoardSize(mapProperties.getWidth(), mapProperties.getHeight());
        board.populateBoard(game.getEntities());
    }

}
