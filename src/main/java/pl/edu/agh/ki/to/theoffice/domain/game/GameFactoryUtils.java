package pl.edu.agh.ki.to.theoffice.domain.game;


import lombok.RequiredArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.EnemyEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.PlayerEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.pickable.PickableEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.pickable.PickableEntityFactory;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameFactoryUtils {

    public static MapResults fromGameProperties(GameProperties properties) {
        final var mapProperties = properties.getMapProperties();

        final LinkedMultiValueMap<Location, Entity> entities = generateEnemies(properties, mapProperties);
        PlayerEntity player = generatePlayer(mapProperties, entities, properties.getPlayerProperties());

        Map<Location, PickableEntity> powerupsOnMap = generatePowerups(mapProperties, entities, properties.getPlayerProperties());

        powerupsOnMap.forEach(entities::add);

        return new MapResults(entities, player, powerupsOnMap);
    }

    private static Map<Location, PickableEntity> generatePowerups(GameProperties.GameMapProperties mapProperties, LinkedMultiValueMap<Location, Entity> entities, GameProperties.GamePlayerProperties playerProperties) {

        return playerProperties.getPowerups().entrySet().stream()
                .flatMap(e -> IntStream.range(0, e.getValue())
                        .mapToObj(nr -> e.getKey()))
                .map(e -> {
                    Location powerupLocation;
                    do {
                        powerupLocation = Location.randomLocation(mapProperties.getWidth(), mapProperties.getHeight());
                    } while (entities.containsKey(powerupLocation));
                    return new AbstractMap.SimpleEntry<>(powerupLocation, PickableEntityFactory.fromEntityType(e));
                })
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

    }

    private static LinkedMultiValueMap<Location, Entity> generateEnemies(GameProperties properties, GameProperties.GameMapProperties mapProperties) {
        final var entities = new LinkedMultiValueMap<Location, Entity>();
        while (entities.size() < properties.getEnemies()) {
            Location location = Location.randomLocation(
                    mapProperties.getWidth(),
                    mapProperties.getHeight()
            );
            entities.addIfAbsent(
                    location,
                    new EnemyEntity(properties.getMapMoveStrategy(), location));
        }
        return entities;
    }

    private static PlayerEntity generatePlayer(GameProperties.GameMapProperties mapProperties, LinkedMultiValueMap<Location, Entity> entities, GameProperties.GamePlayerProperties playerProperties) {
        PlayerEntity player = PlayerEntity.fromProperties(playerProperties);
        Location playerLocation;
        do {
            playerLocation = Location.randomLocation(mapProperties.getWidth(), mapProperties.getHeight());
        } while (entities.containsKey(playerLocation));
        player.setLocation(playerLocation);

        entities.add(playerLocation, player);
        return player;
    }

    @RequiredArgsConstructor
    public static class MapResults {

        public final LinkedMultiValueMap<Location, Entity> entities;
        public final PlayerEntity playerEntity;
        public final Map<Location, PickableEntity> powerupsOnMap;

    }

}