package pl.edu.agh.ki.to.theoffice.domain.entity.pickable;

import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;

public abstract class PickableEntity implements Entity {

    private Location location;

    @Override
    public boolean isMovable() {
        return false;
    }

    public abstract GamePowerup getGamePowerup();

}
