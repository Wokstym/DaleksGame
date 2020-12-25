package pl.edu.agh.ki.to.theoffice.domain.entity.pickable;

import javafx.beans.property.ObjectProperty;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.EnemyEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.MovableEntity;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;

import java.util.List;

public class BombEntity extends PickableEntity {
    @Override
    public EntityType getType() {
        return EntityType.BOMB;
    }

    @Override
    public GamePowerup getGamePowerup() {
        return GamePowerup.BOMB;
    }

    @Override
    public void usePowerup(MapMoveStrategy mapMoveStrategy, ObservableLinkedMultiValueMap<Location, Entity> entities, ObjectProperty<Location> playerLocation) {
        List<Location> neighbouringLocations = Location.generateNeighbouringLocations(playerLocation.getValue());
        neighbouringLocations.remove(playerLocation.getValue());
        neighbouringLocations.forEach(location -> {
            if (entities.containsKey(location)) {
                entities.get(location)
                        .stream()
                        .filter(EnemyEntity.class::isInstance)
                        .map(EnemyEntity.class::cast)
                        .forEach(enemyEntity -> enemyEntity.setState(MovableEntity.MovableEntityState.DEAD));
            }
        });
    }

}
