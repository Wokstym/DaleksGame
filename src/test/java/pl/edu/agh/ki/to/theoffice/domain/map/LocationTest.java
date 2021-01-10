package pl.edu.agh.ki.to.theoffice.domain.map;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
    public void testSubtractTwoLocations() {
        // given
        Location location = new Location(6, 7);

        // when
        Location resultLocation = location.subtract(2, 1);

        // then
        assertEquals(new Location(4, 6), resultLocation);
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
    public void testFromCoordinatesChangeNone() {
        // given
        int dx = 0;
        int dy = 0;

        // when
        Location.Direction direction = Location.Direction.fromCoordinatesChange(dx, dy);

        // then
        assertEquals(Location.Direction.NONE, direction);

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

    @Test
    public void testLocationGeneration() {
        // given
        int fromX = 0;
        int toX = 20;
        int fromY = 0;
        int toY = 20;

        // when
        var locations = Location.generateLocationsWithinBoundsWithRespectOfLeftBottomCorner(fromX, toX, fromY, toY);

        // then
        assertThat(locations).isNotNull();
        assertThat(locations.size()).isEqualTo(400);
        assertThat(locations).contains(new Location(0, 0));
        assertThat(locations).doesNotContain(new Location(20, 20));
        assertThat(locations.get(0)).isEqualTo(new Location(0, 19));
        assertThat(locations).doesNotContain(new Location(21, 0));
        assertThat(locations).doesNotContain(new Location(0, 21));
    }

    @Test
    public void testGenerationOfNeighbouringLocations() {
        // given
        Location location = new Location(2, 2);
        Location[] expectedNeighbours = {
                new Location(3, 2),
                new Location(3, 3),
                new Location(2, 3),
                new Location(1, 3),
                new Location(1, 2),
                new Location(1, 1),
                new Location(2, 1),
                new Location(3, 1),
                new Location(2, 2)
        };

        // when
        var result = Location.generateNeighbouringLocations(location);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(9);
        assertThat(result).containsExactlyInAnyOrder(expectedNeighbours);
    }

    @Test
    public void testFromKeyCodeNorth() {
        // given
        KeyCode keyCode = KeyCode.NUMPAD8;

        // when
        Optional<Location.Direction> direction = Location.Direction.fromKeyCode(keyCode);

        // then
        assertEquals(Location.Direction.NORTH, direction.get());
    }

    @Test
    public void testFromKeyCodeSouth() {
        // given
        KeyCode keyCode = KeyCode.NUMPAD2;

        // when
        Optional<Location.Direction> direction = Location.Direction.fromKeyCode(keyCode);

        // then
        assertEquals(Location.Direction.SOUTH, direction.get());
    }

    @Test
    public void testFromKeyCodeWest() {
        // given
        KeyCode keyCode = KeyCode.NUMPAD4;

        // when
        Optional<Location.Direction> direction = Location.Direction.fromKeyCode(keyCode);

        // then
        assertEquals(Location.Direction.WEST, direction.get());
    }

    @Test
    public void testFromKeyCodeEast() {
        // given
        KeyCode keyCode = KeyCode.NUMPAD6;

        // when
        Optional<Location.Direction> direction = Location.Direction.fromKeyCode(keyCode);

        // then
        assertEquals(Location.Direction.EAST, direction.get());
    }

    @Test
    public void testFromKeyCodeNorthEast() {
        // given
        KeyCode keyCode = KeyCode.NUMPAD9;

        // when
        Optional<Location.Direction> direction = Location.Direction.fromKeyCode(keyCode);

        // then
        assertEquals(Location.Direction.NORTH_EAST, direction.get());
    }

    @Test
    public void testFromKeyCodeNorthWest() {
        // given
        KeyCode keyCode = KeyCode.NUMPAD7;

        // when
        Optional<Location.Direction> direction = Location.Direction.fromKeyCode(keyCode);

        // then
        assertEquals(Location.Direction.NORTH_WEST, direction.get());
    }

    @Test
    public void testFromKeyCodeSouthEast() {
        // given
        KeyCode keyCode = KeyCode.NUMPAD3;

        // when
        Optional<Location.Direction> direction = Location.Direction.fromKeyCode(keyCode);

        // then
        assertEquals(Location.Direction.SOUTH_EAST, direction.get());
    }

    @Test
    public void testFromKeyCodeSouthWest() {
        // given
        KeyCode keyCode = KeyCode.NUMPAD1;

        // when
        Optional<Location.Direction> direction = Location.Direction.fromKeyCode(keyCode);

        // then
        assertEquals(Location.Direction.SOUTH_WEST, direction.get());
    }

    @Test
    public void testFromKeyCodeNone() {
        // given
        KeyCode keyCode = KeyCode.NUMPAD5;

        // when
        Optional<Location.Direction> direction = Location.Direction.fromKeyCode(keyCode);

        // then
        assertEquals(Location.Direction.NONE, direction.get());
    }

    @Test
    public void testFromKeyCodeWhenIllegal() {
        // given
        KeyCode keyCode = KeyCode.BACK_SPACE;

        // when
        Optional<Location.Direction> direction = Location.Direction.fromKeyCode(keyCode);

        // then
        assertEquals(Optional.empty(), direction);
    }

    @Test
    public void testNeighbouringLocations() {
        // given
        Location location = new Location(4, 5);
        List<Location> neighbouringLocations = Location.generateNeighbouringLocations(location);

        // when then
        neighbouringLocations.forEach(neighbouringLocation -> assertTrue(Location.neighbouringLocations(location, neighbouringLocation)));
    }

    @Test
    public void testNotNeighbouringLocations() {
        // given
        Location location = new Location(4, 5);
        Location notNeighbouringLocation = new Location(6, 8);

        // when then
        assertFalse(Location.neighbouringLocations(location, notNeighbouringLocation));
    }

}