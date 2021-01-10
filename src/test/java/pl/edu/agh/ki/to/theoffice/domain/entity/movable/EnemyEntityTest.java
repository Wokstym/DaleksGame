package pl.edu.agh.ki.to.theoffice.domain.entity.movable;

import org.junit.jupiter.api.Test;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.move.BoundedMapMoveStrategy;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class EnemyEntityTest {

    @Test
    void testMove() {
        // given
        Location playerLocation = new Location(2, 0);
        Location enemyLocation = new Location(2, 2);
        EnemyEntity enemyEntity = new EnemyEntity(new BoundedMapMoveStrategy(5, 5), enemyLocation);

        // when
        enemyEntity.move(playerLocation);

        // then
        assertThat(enemyEntity.location).isEqualTo(new Location(2, 1));
    }

    @Test
    void testHandleCollision() {
        // given
        Location enemyLocation = new Location(2, 2);
        EnemyEntity enemyEntity = new EnemyEntity(new BoundedMapMoveStrategy(5, 5), enemyLocation);

        // when
        enemyEntity.handleCollision(2, 0);

        // then
        assertThat(enemyEntity.state).isEqualTo(MovableEntity.MovableEntityState.DEAD);
    }

    @Test
    void testNoCollision() {
        // given
        Location enemyLocation = new Location(2, 2);
        EnemyEntity enemyEntity = new EnemyEntity(new BoundedMapMoveStrategy(5, 5), enemyLocation);

        // when
        enemyEntity.handleCollision(1, 0);

        // then
        assertThat(enemyEntity.state).isEqualTo(MovableEntity.MovableEntityState.ALIVE);
    }
}