package pl.edu.agh.ki.to.theoffice.domain.entity.pickable;

import javafx.beans.property.ObjectProperty;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;

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
    public void usePowerup(MapMoveStrategy mapMoveStrategy, ObservableLinkedMultiValueMap<Location, Entity> entities, ObjectProperty<Location> playerLocation) {
        int x = mapMoveStrategy.getMapWidth();
        int y = mapMoveStrategy.getMapHeight();
        Location newPlayerLocation;

        do {
            newPlayerLocation = Location.randomLocation(x, y);
        } while (locationIsNearToEnemy(entities, newPlayerLocation) || Location.neighbouringLocations(playerLocation.getValue(), newPlayerLocation));

        playerLocation.setValue(newPlayerLocation);
    }

    private boolean locationIsNearToEnemy(ObservableLinkedMultiValueMap<Location, Entity> entities, Location newPlayerLocation) {
        for (Location location : Location.generateNeighbouringLocations(newPlayerLocation))
            if (entities.containsKey(location))
                return true;

        return false;
    }
}
