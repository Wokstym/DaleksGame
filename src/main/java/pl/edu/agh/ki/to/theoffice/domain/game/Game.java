package pl.edu.agh.ki.to.theoffice.domain.game;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.MovableEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.PlayerEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.pickable.PickableEntity;
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
        game.powerupsOnMap = mapResults.powerupsOnMap;

        return game;
    }

    private GameProperties gameProperties;
    private MapMoveStrategy mapMoveStrategy;

    private ObservableLinkedMultiValueMap<Location, Entity> entities;
    private Map<Location, PickableEntity> powerupsOnMap;
    private ObjectProperty<Location> playerLocation;
    private ObjectProperty<GameState> gameState;
    private PlayerEntity playerEntity;

    public void movePlayer(Location.Direction direction) {
        final Location oldLocation = this.playerLocation.getValue();
        final Location newPlayerLocation = this.mapMoveStrategy.move(oldLocation, direction);

        this.playerLocation.setValue(newPlayerLocation);

        this.moveEntities();
        this.solveEnemyCollisions();
        this.pickupPowerup();

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
        this.entities.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(MovableEntity.class::isInstance)
                .map(MovableEntity.class::cast)
                .peek(e -> e.move(newPlayerLocation))
                .map(e -> new AbstractMap.SimpleEntry<>(e.getLocation(), e))
                .forEach(e -> newMap.add(e.getKey(), e.getValue()));

        this.entities.clear();
        log.debug("Moved entities");
        this.entities.addAll(newMap);
        powerupsOnMap.forEach((key, value) -> entities.add(key, value));
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

            var newEntities = handleCollisionsAndSort(players, enemies, entities);

            log.debug("New entites: {}", newEntities);
            entities.clear();
            entities.addAll(newEntities);
            if (powerupsOnMap.containsKey(location)) {
                entities.add(powerupsOnMap.get(location));
            }
            this.entities.put(location, entities);
        }
    }

    private List<MovableEntity> handleCollisionsAndSort(long players, long enemies, List<Entity> oldEntities) {
        return oldEntities
                .stream()
                .filter(MovableEntity.class::isInstance)
                .map(MovableEntity.class::cast)
                .peek(entity -> entity.handleCollision(enemies, players))
                .sorted(Comparator.comparing(Entity::getMapPriority))
                .collect(Collectors.toList());
    }

    private void pickupPowerup() {
        Location location = playerLocation.get();
        List<Entity> entitiesAtPlayer = this.entities.get(location);
        Optional<Entity> entity = entitiesAtPlayer.stream()
                .filter(PickableEntity.class::isInstance)
                .findAny();

        if(entity.isPresent()){
            PickableEntity pickableEntity = powerupsOnMap.get(location);
            playerEntity.addPowerup(pickableEntity.getGamePowerup());
            powerupsOnMap.remove(location);

            entitiesAtPlayer.remove(entity.get());
            this.entities.remove(location);
            this.entities.put(location, entitiesAtPlayer);
        }
    }

    private long getEnemiesCount() {
        return this.entities.values()
                .stream()
                .flatMap(List::stream)
                .filter(e -> e.getType() == EntityType.ENEMY)
                .count();
    }

}
