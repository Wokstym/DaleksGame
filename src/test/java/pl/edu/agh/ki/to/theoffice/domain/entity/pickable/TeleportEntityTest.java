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

import static org.junit.jupiter.api.Assertions.*;

class TeleportEntityTest {

    @Test
    void testUsePowerup() {
        // given
        ObservableLinkedMultiValueMap<Location, Entity> entities = new ObservableLinkedMultiValueMap(new LinkedMultiValueMap<>());
        BoundedMapMoveStrategy boundedMapMoveStrategy = new BoundedMapMoveStrategy(10, 10);

        ObjectProperty<Location> playerLocation = new SimpleObjectProperty<>(new Location(2,2));
        PlayerEntity playerEntity = new PlayerEntity();
        entities.add(playerLocation.getValue(), playerEntity);

        Location enemyLocation = new Location(1, 2);
        entities.add(enemyLocation, new EnemyEntity(boundedMapMoveStrategy, enemyLocation));

        Location enemy2Location = new Location(9, 9);
        entities.add(enemyLocation, new EnemyEntity(boundedMapMoveStrategy, enemy2Location));

        // when
        PickableEntityFactory.fromEntityType(GamePowerup.TELEPORT).usePowerup(boundedMapMoveStrategy, entities, playerLocation);

        // then
        Location.generateNeighbouringLocations(new Location(2, 2))
                .forEach(location -> assertNotEquals(location, playerLocation.getValue()));

        Location.generateNeighbouringLocations(enemyLocation)
                .forEach(location -> assertNotEquals(location, playerLocation.getValue()));

        Location.generateNeighbouringLocations(enemy2Location)
                .forEach(location -> assertNotEquals(location, playerLocation.getValue()));
    }
}