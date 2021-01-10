package pl.edu.agh.ki.to.theoffice.domain.entity.pickable;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.EnemyEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.MovableEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.PlayerEntity;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.MapProperties;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.move.BoundedMapMoveStrategy;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class BombEntityTest {

    @Test
    void testUsePowerup() {
        // given
        MapProperties mapProperties = new MapProperties(5, 5);
        MapMoveStrategy mapMoveStrategy = new BoundedMapMoveStrategy(mapProperties.getWidth(), mapProperties.getHeight());
        ObservableLinkedMultiValueMap<Location, Entity> entities = new ObservableLinkedMultiValueMap(new LinkedMultiValueMap<>());

        ObjectProperty<Location> playerLocation = new SimpleObjectProperty<>(new Location(2, 2));
        PlayerEntity playerEntity = new PlayerEntity();
        entities.add(playerLocation.getValue(), playerEntity);

        Location enemyLocation = new Location(4, 4);
        entities.add(enemyLocation, new EnemyEntity(mapMoveStrategy, enemyLocation));

        List<Location> neighbouringLocations = Location.generateNeighbouringLocations(playerLocation.getValue());
        neighbouringLocations.remove(playerLocation.getValue());
        neighbouringLocations.forEach(location -> entities.add(location, new EnemyEntity(mapMoveStrategy, location)));

        Game game = mock(Game.class);
        given(game.getPlayerLocation()).willReturn(playerLocation);
        given(game.getEntities()).willReturn(entities);

        // when
        PickableEntityFactory
                .fromEntityType(GamePowerup.BOMB)
                .usePowerup(game);

        // then
        assertEquals(8 + 1 + 1, entities.values().size());
        neighbouringLocations.forEach(location ->
                entities.get(location)
                        .stream()
                        .filter(EnemyEntity.class::isInstance)
                        .map(EnemyEntity.class::cast)
                        .forEach(enemyEntity -> assertEquals(MovableEntity.MovableEntityState.DEAD, enemyEntity.getState())));
        assertTrue(entities.containsKey(new Location(4, 4)));
        assertTrue(entities.containsKey(playerLocation.getValue()));
    }
}