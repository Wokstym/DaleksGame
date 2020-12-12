package pl.edu.agh.ki.to.theoffice.components.game;

import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.common.component.FXMLUtils;
import pl.edu.agh.ki.to.theoffice.domain.game.GameChangeListener;
import pl.edu.agh.ki.to.theoffice.domain.map.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;

import java.util.List;

@Slf4j
@Component
public class GameMapComponent extends VBox implements FXMLComponent, GameChangeListener {

    @FXML
    private BoardPane board;

    public GameMapComponent() {
        log.debug("GameMapComponent created: {}", this.hashCode());
        FXMLUtils.loadFXML(this);
    }

    @Override
    public String getFxmlResourceFile() {
        return "/view/game/game-map.fxml";
    }

    @Override
    public void onMapCreated(int width, int height, ObservableLinkedMultiValueMap<Location, EntityType> entities) {
        log.info("onMapCreated: {}, {}, {}, [{}]", width, height, entities.size(), this.hashCode());
        board.setBoardSize(width, height);
        board.populateBoard(entities);
    }

    @Override
    public void onMapChanged(MapChangeListener.Change<? extends Location, ? extends List<EntityType>> change) {
        this.board.onChanged(change);
    }

}
