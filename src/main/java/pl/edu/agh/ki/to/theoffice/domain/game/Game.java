package pl.edu.agh.ki.to.theoffice.domain.game;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.EnemyEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.MovableEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.PlayerEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.pickable.PickableEntity;
import pl.edu.agh.ki.to.theoffice.domain.map.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Game {

    public static Game fromProperties(GameProperties properties) {
        Game game = new Game();
        game.gameProperties = properties;
        game.mapMoveStrategy = properties.getMapMoveStrategy();

        game.gameState = new SimpleObjectProperty<>(GameState.IN_PROGRESS);

        final var mapResults = GameFactoryUtils.fromGameProperties(properties);
        game.entities = new ObservableLinkedMultiValueMap<>(mapResults.entities);
        game.playerEntity = mapResults.playerEntity;
        game.playerLocation = new SimpleObjectProperty<>(game.playerEntity.getLocation());

        return game;
    }

    private GameProperties gameProperties;
    private MapMoveStrategy mapMoveStrategy;

    private ObservableLinkedMultiValueMap<Location, Entity> entities;
    private HashMap<Location, PickableEntity> powerupsOnMap;
    private ObjectProperty<Location> playerLocation;
    private ObjectProperty<GameState> gameState;
    private PlayerEntity playerEntity;

    public void movePlayer(Location.Direction direction) {
        final Location oldLocation = this.playerLocation.getValue();
        final Location newPlayerLocation = this.mapMoveStrategy.move(oldLocation, direction);

        this.playerLocation.setValue(newPlayerLocation);

        this.moveEntities();
        this.solveEnemyCollisions();

        if (playerEntity.getState() == MovableEntity.MovableEntityState.DEAD) {
            gameState.setValue(GameState.LOST);
        }

        if (getEnemiesCount() == 0) {
            gameState.setValue(GameState.WON);
        }
    }

    private void moveEntities() {
        final var newMap = new LinkedMultiValueMap<Location, Entity>();

        final Location newPlayerLocation = this.playerLocation.getValue();
        this.entities.entrySet()
                .stream()
                .flatMap(e -> e.getValue()
                        .stream()
                        .map(entity -> new AbstractMap.SimpleEntry<>(e.getKey(), entity))
                )
                .filter(e -> e.getValue().isMovable())
                .map(e -> {
                    Location newLocation = ((MovableEntity) e.getValue()).move(newPlayerLocation);
                    return new AbstractMap.SimpleEntry<>(newLocation, e.getValue());
                })
                .forEach(e -> newMap.add(e.getKey(), e.getValue()));

        this.entities.clear();
        log.debug("Moved entities");
        this.entities.addAll(newMap);
    }

    private void solveEnemyCollisions() {
        for (var entry : this.entities.entrySet()) {
            final var location = entry.getKey();
            final var entities = entry.getValue();

            Map<EntityType, Long> entitiesCount = entities
                    .stream()
                    .map(Entity::getType)
                    .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

            long players = entitiesCount.getOrDefault(EntityType.PLAYER, 0L);
            long enemies = entitiesCount.getOrDefault(EntityType.ENEMY, 0L) +
                    entitiesCount.getOrDefault(EntityType.ENEMY_SCRAP, 0L);

            var oldEntities = new ArrayList<>(entities);

            entities.clear();
            var newEntities = oldEntities
                    .stream()
                    .filter(Entity::isMovable)
                    .map(e -> (MovableEntity) e)
                    .peek(entity -> entity.handleCollision(enemies, players))
                    .sorted(Comparator.comparing(Entity::getMapPriority))
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
                .filter(e -> e.getType() == EntityType.ENEMY)
                .count();
    }

    private static class GameFactoryUtils {

        private static MapResults fromGameProperties(GameProperties properties) {
            final var mapProperties = properties.getMapProperties();

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

            PlayerEntity player = PlayerEntity.fromProperties(properties.getPlayerProperties());
            Location playerLocation;
            do {
                playerLocation = Location.randomLocation(mapProperties.getWidth(), mapProperties.getHeight());
            } while (entities.containsKey(playerLocation));
            player.setLocation(playerLocation);

            entities.add(playerLocation, player);

            return new MapResults(entities, player);
        }

        @RequiredArgsConstructor
        private static class MapResults {

            public final LinkedMultiValueMap<Location, Entity> entities;
            public final PlayerEntity playerEntity;

        }

    }

}
