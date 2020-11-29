package pl.edu.agh.ki.to.theoffice.domain.game;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategyFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Game {

    static Game fromProperties(GameProperties properties) {
        final var mapProperties = properties.getMapProperties();

        Game game = new Game();

        game.mapMoveStrategy = MapMoveStrategyFactory.fromProperties(mapProperties);

        final var entities = new LinkedMultiValueMap<Location, EntityType>();
        while (entities.size() < properties.getEnemies()) {
            entities.addIfAbsent(
                    Location.randomLocation(
                            mapProperties.getWidth(),
                            mapProperties.getHeight()
                    ),
                    EntityType.ENEMY);
        }

        Location playerLocation;
        do {
            playerLocation = Location.randomLocation(mapProperties.getWidth(), mapProperties.getHeight());
        } while(entities.containsKey(playerLocation));

        entities.add(playerLocation, EntityType.PLAYER);
        
        game.entities = new SimpleObjectProperty<>(entities);
        game.playerLocation = new SimpleObjectProperty<>(playerLocation);
        
        return game;
    }

    private ObjectProperty<MultiValueMap<Location, EntityType>> entities;
    private ObjectProperty<Location> playerLocation;

    private MapMoveStrategy mapMoveStrategy;

    public void movePlayer(Location.Direction direction) {
        final var entities = this.entities.getValue();
        final Location oldLocation = this.playerLocation.getValue();
        final Location newLocation = this.mapMoveStrategy.move(oldLocation, direction);

        this.playerLocation.setValue(newLocation);
        
        entities.remove(oldLocation);
        entities.add(newLocation, EntityType.PLAYER);

        this.moveEnemies();
    }

    private void moveEnemies() {

    }


}
