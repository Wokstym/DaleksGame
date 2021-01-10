package pl.edu.agh.ki.to.theoffice.domain.entity.pickable;

import lombok.extern.slf4j.Slf4j;
import pl.edu.agh.ki.to.theoffice.domain.entity.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;

@Slf4j
public class ReverseTimeEntity extends PickableEntity {
    @Override
    public EntityType getType() {
        return EntityType.REVERSE_TIME;
    }

    @Override
    public GamePowerup getGamePowerup() {
        return GamePowerup.REVERSE_TIME;
    }

    @Override
    public boolean usePowerup(Game game) {
        log.debug("used {} powerup", getType());
        return game.getCommandExecutor().undo();
    }
}
