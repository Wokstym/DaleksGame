package pl.edu.agh.ki.to.theoffice.domain.entity.pickable;

import lombok.extern.slf4j.Slf4j;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.EnemyEntity;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;

import java.util.List;
import java.util.stream.Collectors;

import static pl.edu.agh.ki.to.theoffice.domain.utils.RandomProvider.randomNextInt;

@Slf4j
public class TeleportEntity extends PickableEntity {
    @Override
    public EntityType getType() {
        return EntityType.TELEPORT;
    }

    @Override
    public GamePowerup getGamePowerup() {
        return GamePowerup.TELEPORT;
    }

    @Override
    public boolean usePowerup(Game game) {
        log.debug("used {} powerup", getType());
        MapMoveStrategy mapMoveStrategy = game.getMapMoveStrategy();
        var entities = game.getEntities();
        var playerLocation = game.getPlayerLocation();

        int x = mapMoveStrategy.getMapWidth();
        int y = mapMoveStrategy.getMapHeight();
        Location newPlayerLocation;

        List<Location> locationCandidates = Location.generateLocationsWithinBoundsWithRespectOfLeftBottomCorner(0, x, 0, y)
                .stream()
                .filter(location -> isValidLocation(entities, playerLocation, location))
                .collect(Collectors.toList());

        if (locationCandidates.isEmpty()) {
            return false;
        }

        newPlayerLocation = getRandomLocationFrom(locationCandidates);
        playerLocation.setValue(newPlayerLocation);
        game.movePlayer(Location.Direction.NONE);
        return true;
    }

    private boolean isValidLocation(ObservableLinkedMultiValueMap<Location, Entity> entities, javafx.beans.property.ObjectProperty<Location> playerLocation, Location location) {
        return !locationIsNearToEnemy(entities, location) && !Location.neighbouringLocations(playerLocation.getValue(), location);
    }

    private Location getRandomLocationFrom(List<Location> locationCandidates) {
        return locationCandidates.get(randomNextInt(locationCandidates.size()));
    }

    private boolean locationIsNearToEnemy(ObservableLinkedMultiValueMap<Location, Entity> entities, Location newPlayerLocation) {
        for (Location location : Location.generateNeighbouringLocations(newPlayerLocation)) {
            if (enemyIsAtLocation(entities, location)) {
                return true;
            }
        }


        return false;
    }

    private boolean enemyIsAtLocation(ObservableLinkedMultiValueMap<Location, Entity> entities, Location location) {
        return entities.containsKey(location) &&
                entities.get(location)
                        .stream()
                        .anyMatch(EnemyEntity.class::isInstance);
    }
}
