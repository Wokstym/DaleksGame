package pl.edu.agh.ki.to.theoffice.domain.map;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Location {

    private static final Random r = new Random();

    private final int x;
    private final int y;

    public Location add(int x, int y) {
        return new Location(this.x + x, this.y + y);
    }

    public Location add(Direction direction) {
        return new Location(this.x + direction.dx, this.y + direction.dy);
    }

    public static Location randomLocation(int maxX, int maxY) {
        return new Location(
                r.nextInt(maxX),
                r.nextInt(maxY)
        );
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Direction {

        NORTH(0, 1),
        SOUTH(0, -1),
        WEST(-1, 0),
        EAST(1, 0),
        NORTH_EAST(1, 1),
        NORTH_WEST(-1, 1),
        SOUTH_EAST(-1, 1),
        SOUTH_WEST(-1, -1);

        private final int dx;
        private final int dy;

    }

}
