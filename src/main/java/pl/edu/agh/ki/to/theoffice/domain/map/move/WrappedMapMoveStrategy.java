package pl.edu.agh.ki.to.theoffice.domain.map.move;

import pl.edu.agh.ki.to.theoffice.domain.map.Location;

// todo: implement it
class WrappedMapMoveStrategy extends MapMoveStrategy {

    protected WrappedMapMoveStrategy(int mapWidth, int mapHeight) {
        super(mapWidth, mapHeight);
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
