package pl.edu.agh.ki.to.theoffice.domain.game;

import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.to.theoffice.domain.map.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Consumer;


@Slf4j
@Service
@RequiredArgsConstructor
public class GameStorage {

    private final List<GameChangeListener> listeners;
    private final GamePropertyChangeListener propertyChangeListener = new GamePropertyChangeListener();

    private Game currentGame;

    @PostConstruct
    private void postConstruct() {
        log.debug("Registered listeners: {}", listeners.size());
    }

    public void createNewGame(GameProperties gameProperties) {
        if(currentGame != null) {
            currentGame.removeListener(propertyChangeListener);
        }

        this.currentGame = GameFactory.fromProperties(gameProperties);
        this.currentGame.addListener(propertyChangeListener);

        var mapProperties = currentGame.getGameProperties().getMapProperties();
        callListeners(l -> l.onMapCreated(mapProperties.getWidth(), mapProperties.getHeight(), currentGame.getEntities()));
    }

    public void movePlayer(Location.Direction direction) {
        this.currentGame.movePlayer(direction);
    }

    private void callListeners(Consumer<GameChangeListener> action) {
        GameStorage.this.listeners.forEach(action);
    }

    private class GamePropertyChangeListener implements Game.PropertyChangeListener {

        @Override
        public void onMapChanged(MapChangeListener.Change<? extends Location, ? extends List<EntityType>> change) {
            callListeners(l -> l.onMapChanged(change));
        }

        @Override
        public void onGameStateChanged(ObservableValue<? extends GameState> val, GameState stateBefore, GameState stateAfter) {
            callListeners(l -> l.onGameStateChanged(stateBefore, stateAfter));
        }

        @Override
        public void onPlayerLocationChanged(ObservableValue<? extends Location> val, Location stateBefore, Location stateAfter) {
            callListeners(l -> l.onPlayerLocationChanged(stateBefore, stateAfter));
        }

        @Override
        public void onPlayerLivesChanged(ObservableValue<? extends Number> val, Number stateBefore, Number stateAfter) {
            callListeners(l -> l.onPlayerLivesChanged((int) stateBefore, (int) stateAfter));
        }

        @Override
        public void onPlayerPowerupsChanged(MapChangeListener.Change<? extends GamePowerup, ? extends Integer> change) {
            callListeners(l -> l.onPlayerPowerupsChanged(change));
        }
    }

}
