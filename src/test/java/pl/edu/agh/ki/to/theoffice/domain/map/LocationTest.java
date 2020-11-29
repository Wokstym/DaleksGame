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
    public void testFromCoordinatesChange() {
        // given
        int dx = 1;
        int dy = 0;

        // when
        Location.Direction direction = Location.Direction.fromCoordinatesChange(dx, dy);

        // then
        assertEquals(Location.Direction.EAST, direction);

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