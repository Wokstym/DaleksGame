package pl.edu.agh.ki.to.theoffice.domain.map.move;

import org.junit.jupiter.api.Test;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;

import static org.junit.jupiter.api.Assertions.*;

public class WrappedMapMoveStrategyTest {
    WrappedMapMoveStrategy wrappedMapMoveStrategy = new WrappedMapMoveStrategy(10, 10);

    @Test
    public void testDirectionToApproachTargetSouthEast() {
        // given
        Location source = new Location(1, 3);
        Location target = new Location(2, 2);

        // when
        Location.Direction direction = wrappedMapMoveStrategy.getDirectionToApproachTarget(source, target);

        // then
        assertEquals(Location.Direction.SOUTH_EAST, direction);
    }

    @Test
    public void testDirectionToApproachTargetSouthEastWrapped() {
        // given
        Location source = new Location(9, 3);
        Location target = new Location(2, 2);

        // when
        Location.Direction direction = wrappedMapMoveStrategy.getDirectionToApproachTarget(source, target);

        // then
        assertEquals(Location.Direction.SOUTH_EAST, direction);
    }

    @Test
    public void testDirectionToApproachTargetSouthWestWrapped() {
        // given
        Location source = new Location(1, 3);
        Location target = new Location(9, 9);

        // when
        Location.Direction direction = wrappedMapMoveStrategy.getDirectionToApproachTarget(source, target);

        // then
        assertEquals(Location.Direction.SOUTH_WEST, direction);
    }

    @Test
    public void testDirectionToApproachTargetNorthWestWrapped() {
        // given
        Location source = new Location(2, 9);
        Location target = new Location(9, 3);

        // when
        Location.Direction direction = wrappedMapMoveStrategy.getDirectionToApproachTarget(source, target);

        // then
        assertEquals(Location.Direction.NORTH_WEST, direction);
    }

    @Test
    public void testDirectionToApproachTargetNorthWrapped() {
        // given
        Location source = new Location(1, 9);
        Location target = new Location(1, 3);

        // when
        Location.Direction direction = wrappedMapMoveStrategy.getDirectionToApproachTarget(source, target);

        // then
        assertEquals(Location.Direction.NORTH, direction);
    }

    @Test
    public void testDirectionToApproachTargetSouthWrapped() {
        // given
        Location source = new Location(1, 2);
        Location target = new Location(1, 9);

        // when
        Location.Direction direction = wrappedMapMoveStrategy.getDirectionToApproachTarget(source, target);

        // then
        assertEquals(Location.Direction.SOUTH, direction);
    }

    @Test
    public void testDirectionToApproachTargetWestWrapped() {
        // given
        Location source = new Location(2, 3);
        Location target = new Location(9, 3);

        // when
        Location.Direction direction = wrappedMapMoveStrategy.getDirectionToApproachTarget(source, target);

        // then
        assertEquals(Location.Direction.WEST, direction);
    }

    @Test
    public void testDirectionToApproachTargetEastWrapped() {
        // given
        Location source = new Location(9, 3);
        Location target = new Location(2, 3);

        // when
        Location.Direction direction = wrappedMapMoveStrategy.getDirectionToApproachTarget(source, target);

        // then
        assertEquals(Location.Direction.EAST, direction);
    }

    @Test
    public void testDirectionToApproachTargetNorth() {
        // given
        Location source = new Location(2, 3);
        Location target = new Location(2, 5);

        // when
        Location.Direction direction = wrappedMapMoveStrategy.getDirectionToApproachTarget(source, target);

        // then
        assertEquals(Location.Direction.NORTH, direction);
    }

    @Test
    public void testDirectionToApproachTargetSouth() {
        // given
        Location source = new Location(9, 6);
        Location target = new Location(9, 3);

        // when
        Location.Direction direction = wrappedMapMoveStrategy.getDirectionToApproachTarget(source, target);

        // then
        assertEquals(Location.Direction.SOUTH, direction);
    }

    @Test
    public void testDirectionToApproachTargetWest() {
        // given
        Location source = new Location(7, 3);
        Location target = new Location(2, 3);

        // when
        Location.Direction direction = wrappedMapMoveStrategy.getDirectionToApproachTarget(source, target);

        // then
        assertEquals(Location.Direction.WEST, direction);
    }

    @Test
    public void testDirectionToApproachTargetEast() {
        // given
        Location source = new Location(2, 3);
        Location target = new Location(5, 3);

        // when
        Location.Direction direction = wrappedMapMoveStrategy.getDirectionToApproachTarget(source, target);

        // then
        assertEquals(Location.Direction.EAST, direction);
    }

    @Test
    public void testDirectionToApproachTargetNone() {
        // given
        Location source = new Location(9, 3);
        Location target = new Location(9, 3);

        // when
        Location.Direction direction = wrappedMapMoveStrategy.getDirectionToApproachTarget(source, target);

        // then
        assertEquals(Location.Direction.NONE, direction);
    }

    @Test
    public void testMoveToAllowedPosition() {
        // given
        Location location = new Location(2, 2);
        Location.Direction direction = Location.Direction.EAST;

        // when then
        assertEquals(new Location(3, 2), wrappedMapMoveStrategy.move(location, direction));
    }

    @Test
    public void testMoveToAllowedPositionEast() {
        // given
        Location location = new Location(9, 2);
        Location.Direction direction = Location.Direction.EAST;

        // when then
        assertEquals(new Location(0, 2), wrappedMapMoveStrategy.move(location, direction));
    }

    @Test
    public void testMoveToWrappedPositionWest() {
        // given
        Location location = new Location(0, 2);
        Location.Direction direction = Location.Direction.WEST;

        // when then
        assertEquals(new Location(9, 2), wrappedMapMoveStrategy.move(location, direction));
    }

    @Test
    public void testMoveToWrappedPositionSouthWest() {
        // given
        Location location = new Location(0, 0);
        Location.Direction direction = Location.Direction.SOUTH_WEST;

        // when then
        assertEquals(new Location(9, 9), wrappedMapMoveStrategy.move(location, direction));
    }

    @Test
    public void testMoveToWrappedPositionSouth() {
        // given
        Location location = new Location(2, 0);
        Location.Direction direction = Location.Direction.SOUTH;

        // when then
        assertEquals(new Location(2, 9), wrappedMapMoveStrategy.move(location, direction));
    }

    @Test
    public void testMoveToWrappedPositionNorth() {
        // given
        Location location = new Location(9, 9);
        Location.Direction direction = Location.Direction.NORTH;

        // when then
        assertEquals(new Location(9, 0), wrappedMapMoveStrategy.move(location, direction));
    }
}
