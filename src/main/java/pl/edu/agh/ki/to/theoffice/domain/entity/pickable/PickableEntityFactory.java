package pl.edu.agh.ki.to.theoffice.domain.entity.pickable;

import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;

public class PickableEntityFactory {

    public static PickableEntity fromEntityType(GamePowerup entityType) {
        return switch (entityType) {
            case TELEPORT -> new TeleportEntity();
            case BOMB -> new BombEntity();
            case REVERSE_TIME -> new ReverseTimeEntity();
        };
    }
}
