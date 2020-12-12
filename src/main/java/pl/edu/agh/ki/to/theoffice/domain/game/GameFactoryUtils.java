package pl.edu.agh.ki.to.theoffice.domain.game;

import lombok.RequiredArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;

class GameFactoryUtils {

    static MapResults fromGameProperties(GameProperties properties) {
        final var mapProperties = properties.getMapProperties();

        final var entities = new LinkedMultiValueMap<Location, EntityType>();
        while (entities.size() < properties.getEnemies()) {
            entities.addIfAbsent(
                    Location.randomLocation(
                            mapProperties.getWidth(),
                            mapProperties.getHeight()
                    ),
                    EntityType.ENEMY);
        }

        Location playerLocation;
        do {
            playerLocation = Location.randomLocation(mapProperties.getWidth(), mapProperties.getHeight());
        } while (entities.containsKey(playerLocation));

        entities.add(playerLocation, EntityType.PLAYER);

        return new MapResults(entities, playerLocation);
    }

    @RequiredArgsConstructor
    static class MapResults {

        public final LinkedMultiValueMap<Location, EntityType> entities;
        public final Location playerLocation;

    }

}
