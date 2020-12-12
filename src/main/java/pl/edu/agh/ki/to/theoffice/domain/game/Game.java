package pl.edu.agh.ki.to.theoffice.domain.game;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategyFactory;

import java.util.*;
import java.util.stream.Collectors;

import static pl.edu.agh.ki.to.theoffice.domain.map.Location.Direction;

@Slf4j
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Game {

    public static Game fromProperties(GameProperties properties) {
        final var mapProperties = properties.getMapProperties();

        Game game = new Game();
        game.gameProperties = properties;
        game.mapMoveStrategy = MapMoveStrategyFactory.fromProperties(mapProperties);

        game.gameState = new SimpleObjectProperty<>(GameState.IN_PROGRESS);
        game.gamePlayer = GamePlayer.fromProperties(properties.getPlayerProperties());

        final var mapResults = GameFactoryUtils.fromGameProperties(properties);
        game.entities = new ObservableLinkedMultiValueMap<>(mapResults.entities);
        game.playerLocation = new SimpleObjectProperty<>(mapResults.playerLocation);

        return game;
    }

    private GameProperties gameProperties;
    private MapMoveStrategy mapMoveStrategy;

    private ObservableLinkedMultiValueMap<Location, EntityType> entities;
    private ObjectProperty<Location> playerLocation;
    private ObjectProperty<GameState> gameState;
    private GamePlayer gamePlayer;

    public void movePlayer(Location.Direction direction) {
        final Location oldLocation = this.playerLocation.getValue();
        final Location newLocation = this.mapMoveStrategy.move(oldLocation, direction);

        this.playerLocation.setValue(newLocation);

        this.entities.remove(oldLocation);
        this.entities.add(newLocation, EntityType.PLAYER);

        this.moveEnemies();
        this.solveEnemyCollisions();

        if (this.entities.get(newLocation).contains(EntityType.DEAD_PLAYER)) {
            gameState.setValue(GameState.LOST);
        }

        if(getEnemiesCount() == 0) {
            gameState.setValue(GameState.WON);
        }
    }

    private void moveEnemies() {
        final var newMap = new LinkedMultiValueMap<Location, EntityType>();

        final Location playerLocation = this.playerLocation.getValue();
        this.entities.entrySet()
                .stream()
                .flatMap(e -> e.getValue()
                        .stream()
                        .map(entity -> new AbstractMap.SimpleEntry<>(e.getKey(), entity))
                )
                .map(e -> {
                    Location newLocation = e.getKey();

                    if (e.getValue() == EntityType.ENEMY) {
                        Direction direction = this.mapMoveStrategy.getDirectionToApproachTarget(e.getKey(), playerLocation);
                        newLocation = this.mapMoveStrategy.move(e.getKey(), direction);
                    }

                    return new AbstractMap.SimpleEntry<>(newLocation, e.getValue());
                })
                .filter(e -> e.getValue() != EntityType.PLAYER)
                .forEach(e -> newMap.add(e.getKey(), e.getValue()));

        this.entities.clear();
        log.debug("Moved entities");
        this.entities.addAll(newMap);

        this.entities.add(playerLocation, EntityType.PLAYER);
    }

    private void solveEnemyCollisions() {
        for (var entry : this.entities.entrySet()) {
            final var location = entry.getKey();
            final var entities = entry.getValue();

            Map<EntityType, Long> entitiesCount = entities
                    .stream()
                    .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

            long players = entitiesCount.getOrDefault(EntityType.PLAYER, 0L);
            long enemies = entitiesCount.getOrDefault(EntityType.ENEMY, 0L);
            long scraps = entitiesCount.getOrDefault(EntityType.ENEMY_SCRAP, 0L);

            boolean playerColliedWithEnemy = (players > 0 && (enemies + scraps > 0));
            boolean enemiesCollided = (enemies + scraps > 1);

            var oldEntities = new ArrayList<>(entities);

            entities.clear();
            var newEntities = oldEntities
                    .stream()
                    .map(e -> switch (e) {
                        case PLAYER, DEAD_PLAYER -> playerColliedWithEnemy ? EntityType.DEAD_PLAYER : e;
                        case ENEMY, ENEMY_SCRAP -> enemiesCollided ? EntityType.ENEMY_SCRAP : e;
                    })
                    .sorted(Comparator.comparing(EntityType::getMapPriority))
                    .collect(Collectors.toList());

            log.debug("New entites: {}", newEntities);
            entities.addAll(newEntities);
            this.entities.put(location, entities);
        }
    }

    private long getEnemiesCount() {
        return this.entities.values()
                .stream()
                .flatMap(List::stream)
                .filter(e -> e == EntityType.ENEMY)
                .count();
    }

    private static class GameFactoryUtils {

        private static MapResults fromGameProperties(GameProperties properties) {
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
        private static class MapResults {

            public final LinkedMultiValueMap<Location, EntityType> entities;
            public final Location playerLocation;

        }

    }

}
