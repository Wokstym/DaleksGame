package pl.edu.agh.ki.to.theoffice.domain.map.move;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BoundedMapMoveStrategyTest {

    BoundedMapMoveStrategy boundedMapMoveStrategy = new BoundedMapMoveStrategy(10, 10);

    @Test
    public void testDirectionToApproachTarget() {
        // given
        Location source = new Location(1, 3);
        Location target = new Location(2, 2);

        // when
        Location.Direction direction = boundedMapMoveStrategy.getDirectionToApproachTarget(source, target);

        // then
        assertEquals(Location.Direction.SOUTH_EAST, direction);
    }

    @Test
    public void testMoveToAllowedPosition() {
        // given
        Location location = new Location(2, 2);
        Location.Direction direction = Location.Direction.EAST;

        // when then
        assertEquals(new Location(3, 2), boundedMapMoveStrategy.move(location, direction));
    }

    @Test
    public void testMoveToForbiddenPosition() {
        // given
        Location location = new Location(0, 2);
        Location.Direction direction = Location.Direction.WEST;

        // when then
        assertEquals(location, boundedMapMoveStrategy.move(location, direction));
    }

}