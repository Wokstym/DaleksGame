package pl.edu.agh.ki.to.theoffice.domain.entity.pickable;

import pl.edu.agh.ki.to.theoffice.domain.entity.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;

public class TeleportEntity extends PickableEntity {
    @Override
    public EntityType getType() {
        return EntityType.TELEPORT;
    }

    @Override
    public GamePowerup getGamePowerup() {
        return GamePowerup.TELEPORT;
    }
}
