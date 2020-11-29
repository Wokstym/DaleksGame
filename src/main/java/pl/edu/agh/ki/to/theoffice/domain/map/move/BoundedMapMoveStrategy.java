package pl.edu.agh.ki.to.theoffice.domain.map.move;

import pl.edu.agh.ki.to.theoffice.domain.map.Location;

class BoundedMapMoveStrategy extends BaseGameMapMoveStrategy {

    BoundedMapMoveStrategy(int mapWidth, int mapHeight) {
        super(mapWidth, mapHeight);
    }

    @Override
    public Location move(Location location, Location.Direction direction) {
        final Location newLocation = location.add(direction);

        return isWithinBounds(newLocation) ? newLocation : location;
    }

    @Override
    public Location.Direction getDirectionToApproachTarget(Location source, Location target) {
        return null;
    }

    private boolean isWithinBounds(Location loc) {
        return loc.getX() >= 0 &&
                loc.getY() >= 0 &&
                loc.getX() < this.mapWidth &&
                loc.getY() < this.mapHeight;
    }

}
