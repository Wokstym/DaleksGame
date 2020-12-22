package pl.edu.agh.ki.to.theoffice.domain.entity.pickable;

import javafx.beans.property.ObjectProperty;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;

public abstract class PickableEntity implements Entity {

    private Location location;

    public abstract GamePowerup getGamePowerup();

    public abstract void usePowerup(MapMoveStrategy mapMoveStrategy, ObservableLinkedMultiValueMap<Location, Entity> entities, ObjectProperty<Location> playerLocation);

}
