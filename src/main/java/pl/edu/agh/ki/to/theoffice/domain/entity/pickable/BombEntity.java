package pl.edu.agh.ki.to.theoffice.domain.entity.pickable;

import lombok.extern.slf4j.Slf4j;
import pl.edu.agh.ki.to.theoffice.domain.entity.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.EnemyEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.MovableEntity;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;

import java.util.List;

@Slf4j
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
    public boolean usePowerup(Game game) {
        log.debug("used {} powerup", getType());
        var playerLocation = game.getPlayerLocation().getValue();
        var entities = game.getEntities();
        List<Location> neighbouringLocations = Location.generateNeighbouringLocations(playerLocation);
        neighbouringLocations.remove(playerLocation);
        neighbouringLocations.forEach(location -> {
            if (entities.containsKey(location)) {
                entities.get(location)
                        .stream()
                        .filter(EnemyEntity.class::isInstance)
                        .map(EnemyEntity.class::cast)
                        .forEach(enemyEntity -> enemyEntity.setState(MovableEntity.MovableEntityState.DEAD));
            }
        });
        game.movePlayer(Location.Direction.NONE);
        return true;
    }

}
