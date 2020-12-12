package pl.edu.agh.ki.to.theoffice.domain.game;

import javafx.collections.MapChangeListener;
import pl.edu.agh.ki.to.theoffice.domain.map.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;

import java.util.List;

public interface GameChangeListener {

    default void onMapCreated(int width, int height, ObservableLinkedMultiValueMap<Location, EntityType> entities) {

    }

    default void onMapChanged(MapChangeListener.Change<? extends Location, ? extends List<EntityType>> change) {

    }

    default void onGameStateChanged(GameState stateBefore, GameState stateAfter) {

    }

    default void onPlayerLocationChanged(Location stateBefore, Location stateAfter) {

    }

    default void onPlayerLivesChanged(int stateBefore, int stateAfter) {

    }

    default void onPlayerPowerupsChanged(MapChangeListener.Change<? extends GamePowerup, ? extends Integer> change) {

    }

}