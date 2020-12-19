package pl.edu.agh.ki.to.theoffice.components.game;

import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import pl.edu.agh.ki.to.theoffice.common.component.FXMLUtils;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;

import java.util.List;

@Slf4j
public class GameMapComponent extends VBox implements FXMLComponent, MapChangeListener<Location, List<Entity>> {

    public static final String FXML_SOURCE = "/view/game/game-map.fxml";

    @FXML
    private BoardPane board;

    public GameMapComponent() {
        log.debug("GameMapComponent created: {}", this.hashCode());
        FXMLUtils.loadFXML(this);
    }

    @Override
    public String getFxmlResourceFile() {
        return FXML_SOURCE;
    }

    public void initMap(int width, int height, ObservableLinkedMultiValueMap<Location, Entity> entities) {
        board.prepareBoard(width, height);
        board.populateBoard(entities);
    }

    @Override
    public void onChanged(MapChangeListener.Change<? extends Location, ? extends List<Entity>> change) {
        this.board.onChanged(change);
    }

}
