package pl.edu.agh.ki.to.theoffice.domain.entity.pickable;

import lombok.Getter;
import lombok.Setter;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;

public abstract class PickableEntity implements Entity {

    @Getter
    @Setter
    private Location location;

    public abstract GamePowerup getGamePowerup();

    public abstract boolean usePowerup(Game game);

}
