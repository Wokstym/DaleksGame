package pl.edu.agh.ki.to.theoffice.domain.entity.movable;

import lombok.ToString;
import pl.edu.agh.ki.to.theoffice.domain.entity.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;


@ToString(callSuper = true)
public class EnemyEntity extends MovableEntity {

    public EnemyEntity(MapMoveStrategy mapMoveStrategy, Location location) {
        super(mapMoveStrategy, location, MovableEntityState.ALIVE);
    }

    @Override
    public void move(Location playerLocation) {
        if (state == MovableEntityState.ALIVE) {
            Location.Direction direction = this.mapMoveStrategy.getDirectionToApproachTarget(this.location, playerLocation);
            this.location = this.mapMoveStrategy.move(this.location, direction);
        }
    }

    @Override
    public void handleCollision(long enemiesCount, long playersCount) {
        boolean enemiesCollided = enemiesCount > 1;
        if (enemiesCollided) {
            this.state = MovableEntityState.DEAD;
        }
    }

    @Override
    public EntityType getType() {
        return state == MovableEntityState.ALIVE ?
                EntityType.ENEMY :
                EntityType.ENEMY_SCRAP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnemyEntity that = (EnemyEntity) o;
        return state == that.state && location.equals(that.location);
    }
}
