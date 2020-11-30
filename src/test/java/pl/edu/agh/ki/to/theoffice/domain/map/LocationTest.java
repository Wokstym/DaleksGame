package pl.edu.agh.ki.to.theoffice.domain.map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LocationTest {

    @Test
    public void testAddTwoLocations() {
        // given
        Location location = new Location(2, 5);

        // when
        Location resultLocation = location.add(3, -1);

        // then
        assertEquals(new Location(5, 4), resultLocation);
    }

    @Test
    public void testAddDirectionToLocation() {
        // given
        Location location = new Location(2, 5);
        Location.Direction direction = Location.Direction.NORTH;

        // when
        Location resultLocation = location.add(direction);

        // then
        assertEquals(new Location(2, 6), resultLocation);
    }

    @Test
    public void testFromCoordinatesChangeNorth() {
        // given
        int dx = 0;
        int dy = 6;

        // when
        Location.Direction direction = Location.Direction.fromCoordinatesChange(dx, dy);

        // then
        assertEquals(Location.Direction.NORTH, direction);

    }

    @Test
    public void testFromCoordinatesChangeSouth() {
        // given
        int dx = 0;
        int dy = -19;

        // when
        Location.Direction direction = Location.Direction.fromCoordinatesChange(dx, dy);

        // then
        assertEquals(Location.Direction.SOUTH, direction);

    }

    @Test
    public void testFromCoordinatesChangeWest() {
        // given
        int dx = -18;
        int dy = 0;

        // when
        Location.Direction direction = Location.Direction.fromCoordinatesChange(dx, dy);

        // then
        assertEquals(Location.Direction.WEST, direction);

    }

    @Test
    public void testFromCoordinatesChangeEast() {
        // given
        int dx = 16;
        int dy = 0;

        // when
        Location.Direction direction = Location.Direction.fromCoordinatesChange(dx, dy);

        // then
        assertEquals(Location.Direction.EAST, direction);

    }

    @Test
    public void testFromCoordinatesChangeNorthEast() {
        // given
        int dx = 10;
        int dy = 12;

        // when
        Location.Direction direction = Location.Direction.fromCoordinatesChange(dx, dy);

        // then
        assertEquals(Location.Direction.NORTH_EAST, direction);

    }

    @Test
    public void testFromCoordinatesChangeNorthWest() {
        // given
        int dx = -5;
        int dy = 5;

        // when
        Location.Direction direction = Location.Direction.fromCoordinatesChange(dx, dy);

        // then
        assertEquals(Location.Direction.NORTH_WEST, direction);

    }

    @Test
    public void testFromCoordinatesChangeSouthEast() {
        // given
        int dx = 20;
        int dy = -6;

        // when
        Location.Direction direction = Location.Direction.fromCoordinatesChange(dx, dy);

        // then
        assertEquals(Location.Direction.SOUTH_EAST, direction);

    }

    @Test
    public void testFromCoordinatesChangeSouthWest() {
        // given
        int dx = -10;
        int dy = -5;

        // when
        Location.Direction direction = Location.Direction.fromCoordinatesChange(dx, dy);

        // then
        assertEquals(Location.Direction.SOUTH_WEST, direction);

    }

    @Test
    public void testRandomLocationInBoundaries() {
        // given
        int maxX = 5;
        int maxY = 10;

        // when
        Location location = Location.randomLocation(maxX, maxY);

        // then
        assertTrue(location.getX() <= maxX);
        assertTrue(location.getY() <= maxY);
    }

}