package pl.edu.agh.ki.to.theoffice.domain.map.move;

import pl.edu.agh.ki.to.theoffice.domain.map.Location;

public class WrappedMapMoveStrategy extends MapMoveStrategy {

    public WrappedMapMoveStrategy(int mapWidth, int mapHeight) {
        super(mapWidth, mapHeight);
    }

    @Override
    public Location move(Location location, Location.Direction direction) {
        final Location tmpLocation = location.add(direction);
        final Location newLocation = new Location(
                (tmpLocation.getX() + this.mapHeight) % this.mapWidth, //zeby zawijanie przez modulo dzialalo wartosic musza byc dodatnie
                (tmpLocation.getY() + this.mapHeight) % this.mapHeight
        );
        return newLocation;
    }

    @Override
    public Location.Direction getDirectionToApproachTarget(Location source, Location target) {
        int dx = target.getX() - source.getX();
        int dy = target.getY() - source.getY();

        //nie ma co dawać niesamowitych obliczeń tutaj
        //jeśli odległość dx lub dy jest większa niż połowa mapy tzn ze lepiej isc w drugą stronę
        dx = Math.abs(dx) > this.mapWidth / 2 ? -dx : dx;
        dy = Math.abs(dy) > this.mapHeight / 2 ? -dy : dy;

        return Location.Direction.fromCoordinatesChange(dx, dy);
    }
}
