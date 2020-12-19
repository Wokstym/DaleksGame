package pl.edu.agh.ki.to.theoffice.domain.entity.pickable;

import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;

// todo implement powerups usage
public abstract class PickableEntity implements Entity {

    private Location location;

    public abstract GamePowerup getGamePowerup();

}
