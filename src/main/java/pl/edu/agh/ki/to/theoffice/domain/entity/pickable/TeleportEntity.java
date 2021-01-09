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

        int maxNumberOfAttempts = 100; // because loop could be infinite due to the small number of free places

        do {
            newPlayerLocation = Location.randomLocation(x, y);
            maxNumberOfAttempts--;
        } while ((locationIsNearToEnemy(entities, newPlayerLocation) || Location.neighbouringLocations(playerLocation.getValue(), newPlayerLocation))
                && maxNumberOfAttempts > 0);

        playerLocation.setValue(newPlayerLocation);
        game.movePlayer(Location.Direction.NONE);
        return true;
    }

    private boolean locationIsNearToEnemy(ObservableLinkedMultiValueMap<Location, Entity> entities, Location newPlayerLocation) {
        for (Location location : Location.generateNeighbouringLocations(newPlayerLocation))
            if (enemyIsAtLocation(entities, location))
                return true;

        return false;
    }

    private boolean enemyIsAtLocation(ObservableLinkedMultiValueMap<Location, Entity> entities, Location location) {
        return entities.containsKey(location) &&
                entities.get(location)
                        .stream()
                        .filter(EnemyEntity.class::isInstance)
                        .map(EnemyEntity.class::cast)
                        .count() > 0;
    }
}
