package pl.edu.agh.ki.to.theoffice.domain.map;

import javafx.scene.input.KeyCode;
import lombok.*;
import pl.edu.agh.ki.to.theoffice.common.formatter.UnityFormatter;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static pl.edu.agh.ki.to.theoffice.domain.utils.RandomProvider.randomNextInt;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Location {

    private final int x;
    private final int y;

    public static Location randomLocation(int maxX, int maxY) {
        return new Location(
                randomNextInt(maxX),
                randomNextInt(maxY)
        );
    }

    public static List<Location> generateNeighbouringLocations(Location location) {
        return Stream.of(Direction.values())
                .map(location::add)
                .collect(Collectors.toList());
    }

    public static List<Location> generateLocationsWithinBoundsWithRespectOfLeftBottomCorner(int fromX, int toX, int fromY, int toY) {
        return IntStream.range(fromY, toY)
                .map(i -> toY - i + fromY - 1)
                .mapToObj(y -> IntStream.range(fromX, toX)
                        .mapToObj(x -> new Location(x, y))
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public static boolean neighbouringLocations(Location firstLocation, Location secondLocation) {
        Location differenceLocation = firstLocation.subtract(secondLocation.x, secondLocation.y);
        return Stream.of(Direction.values())
                .anyMatch(d -> d.dx == differenceLocation.x && d.dy == differenceLocation.y);
    }

    public Location add(int x, int y) {
        return new Location(this.x + x, this.y + y);
    }

    public Location add(Direction direction) {
        return new Location(this.x + direction.dx, this.y + direction.dy);
    }

    public Location subtract(int x, int y) {
        return new Location(this.x - x, this.y - y);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Direction {

        NORTH(0, 1, KeyCode.NUMPAD8),
        SOUTH(0, -1, KeyCode.NUMPAD2),
        WEST(-1, 0, KeyCode.NUMPAD4),
        EAST(1, 0, KeyCode.NUMPAD6),
        NORTH_EAST(1, 1, KeyCode.NUMPAD9),
        NORTH_WEST(-1, 1, KeyCode.NUMPAD7),
        SOUTH_EAST(1, -1, KeyCode.NUMPAD3),
        SOUTH_WEST(-1, -1, KeyCode.NUMPAD1),
        NONE(0, 0, KeyCode.NUMPAD5);

        private final int dx;
        private final int dy;
        private final KeyCode keyCode;

        public static Direction fromCoordinatesChange(int dx, int dy) {
            final int uDx = UnityFormatter.format(dx);
            final int uDy = UnityFormatter.format(dy);

            return Stream.of(values())
                    .filter(d -> d.dx == uDx && d.dy == uDy)
                    .findAny()
                    .orElseThrow(IllegalStateException::new);
        }

        public static Optional<Direction> fromKeyCode(KeyCode keyCode) {
            return Stream.of(values())
                    .filter(d -> d.keyCode == keyCode)
                    .findAny();
        }

    }

}
