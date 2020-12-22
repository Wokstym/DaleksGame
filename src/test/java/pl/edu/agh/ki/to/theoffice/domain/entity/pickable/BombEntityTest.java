package pl.edu.agh.ki.to.theoffice.domain.entity.pickable;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.EnemyEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.PlayerEntity;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.move.BoundedMapMoveStrategy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BombEntityTest {

    @Test
    void testUsePowerup() {
        // given
        ObservableLinkedMultiValueMap<Location, Entity> entities = new ObservableLinkedMultiValueMap(new LinkedMultiValueMap<>());
        BoundedMapMoveStrategy boundedMapMoveStrategy = new BoundedMapMoveStrategy(5, 5);

        ObjectProperty<Location> playerLocation = new SimpleObjectProperty<>(new Location(2,2));
        PlayerEntity playerEntity = new PlayerEntity();
        entities.add(playerLocation.getValue(), playerEntity);

        Location enemyLocation = new Location(4, 4);
        entities.add(enemyLocation, new EnemyEntity(boundedMapMoveStrategy, enemyLocation));

        List<Location> neighbouringLocations = Location.generateNeighbouringLocations(playerLocation.getValue());
        neighbouringLocations.remove(playerLocation.getValue());
        neighbouringLocations.forEach(location -> entities.add(enemyLocation, new EnemyEntity(boundedMapMoveStrategy, location)));

        // when
        PickableEntityFactory
                .fromEntityType(GamePowerup.BOMB)
                .usePowerup(boundedMapMoveStrategy, entities, playerLocation);

        // then
        assertEquals(2, entities.values().size());
        neighbouringLocations.forEach(location -> {
            assertFalse(entities.containsKey(location));
        });
        assertTrue(entities.containsKey(new Location(4, 4)));
        assertTrue(entities.containsKey(playerLocation.getValue()));
    }
}