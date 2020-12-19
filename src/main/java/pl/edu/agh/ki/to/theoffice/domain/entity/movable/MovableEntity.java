package pl.edu.agh.ki.to.theoffice.domain.entity.movable;

import lombok.*;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;

@Setter
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class MovableEntity implements Entity {

    @ToString.Exclude
    protected MapMoveStrategy mapMoveStrategy;
    protected Location location;
    protected MovableEntityState state = MovableEntityState.ALIVE;

    public abstract void move(Location location);

    public abstract void handleCollision(long enemiesCount, long playersCount);

    public enum MovableEntityState {
        ALIVE,
        DEAD;
    }
}
