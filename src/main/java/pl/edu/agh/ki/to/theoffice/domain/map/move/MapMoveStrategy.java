package pl.edu.agh.ki.to.theoffice.domain.map.move;

import pl.edu.agh.ki.to.theoffice.domain.map.Location;

public interface MapMoveStrategy {

    Location move(Location location, Location.Direction direction);
    Location.Direction getDirectionToApproachTarget(Location source, Location target);

    public enum Type {

        BOUNDED,
        WRAPPED;

    }

}
