package pl.edu.agh.ki.to.theoffice.domain.map.move;

import pl.edu.agh.ki.to.theoffice.domain.map.Location;

import java.lang.reflect.InaccessibleObjectException;

// todo: implement it
public class WrappedMapMoveStrategy extends MapMoveStrategy {

    public WrappedMapMoveStrategy(int mapWidth, int mapHeight) {
        super(mapWidth, mapHeight);
        throw new InaccessibleObjectException("Feature not implemented yet. Use BOUNDED strategy instead");
    }

    @Override
    public Location move(Location location, Location.Direction direction) {
        return null;
    }

    @Override
    public Location.Direction getDirectionToApproachTarget(Location source, Location target) {
        return null;
    }

}
