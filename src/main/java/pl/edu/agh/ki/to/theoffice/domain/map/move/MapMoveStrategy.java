package pl.edu.agh.ki.to.theoffice.domain.map.move;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;

import java.util.function.BiFunction;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MapMoveStrategy {

    protected final int mapWidth;
    protected final int mapHeight;

    public abstract Location move(Location location, Location.Direction direction);

    public abstract Location.Direction getDirectionToApproachTarget(Location source, Location target);

    @RequiredArgsConstructor
    public enum Type {

        BOUNDED(BoundedMapMoveStrategy::new),
        WRAPPED(WrappedMapMoveStrategy::new);

        private final BiFunction<Integer, Integer, MapMoveStrategy> generator;

        public MapMoveStrategy createStrategy(int width, int height) {
            return generator.apply(width, height);
        }

    }

}
