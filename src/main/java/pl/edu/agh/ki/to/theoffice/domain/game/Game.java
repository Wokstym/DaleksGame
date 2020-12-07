package pl.edu.agh.ki.to.theoffice.domain.game;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.LinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.PlayerMoveResponse;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategyFactory;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.edu.agh.ki.to.theoffice.domain.map.Location.Direction;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Game {

    private ObservableLinkedMultiValueMap<Location, EntityType> entities;
    private ObjectProperty<Location> playerLocation;
    private MapMoveStrategy mapMoveStrategy;
    private ObjectProperty<GameState> gameState;

    public static Game fromProperties(GameProperties properties) {
        final var mapProperties = properties.getMapProperties();

        Game game = new Game();
        game.mapMoveStrategy = MapMoveStrategyFactory.fromProperties(mapProperties);
        game.gameState = new SimpleObjectProperty<>(GameState.IN_PROGRESS);

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

        game.entities = new ObservableLinkedMultiValueMap<>(entities);
        game.playerLocation = new SimpleObjectProperty<>(playerLocation);

        return game;
    }

    public PlayerMoveResponse movePlayer(Location.Direction direction) {
        final Location oldLocation = this.playerLocation.getValue();
        final Location newLocation = this.mapMoveStrategy.move(oldLocation, direction);

        this.playerLocation.setValue(newLocation);

        this.entities.remove(oldLocation);
        this.entities.add(newLocation, EntityType.PLAYER);

        this.moveEnemies();
        this.solveEnemyCollisions();

        if (this.entities.get(newLocation).contains(EntityType.DEAD_PLAYER)) {
            gameState.setValue(GameState.LOST);
            return PlayerMoveResponse.PLAYER_COLLIDED_WITH_ENEMY;
        }


        if (newLocation.equals(oldLocation)) {
            return PlayerMoveResponse.PLAYER_NOT_MOVED;
        }

        return PlayerMoveResponse.PLAYER_MOVED;
    }

    private void moveEnemies() {
        final Location playerLocation = this.playerLocation.getValue();

        final var newMap = new LinkedMultiValueMap<Location, EntityType>();

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
        this.entities.addAll(newMap);

        this.entities.add(playerLocation, EntityType.PLAYER);
    }

    // TODO change so collisions also trigger listeners
    private void solveEnemyCollisions() {

        for (var entities : this.entities.values()) {
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
            entities.addAll(oldEntities
                    .stream()
                    .map(e -> switch (e) {
                        case PLAYER, DEAD_PLAYER -> playerColliedWithEnemy ? EntityType.DEAD_PLAYER : e;
                        case ENEMY, ENEMY_SCRAP -> enemiesCollided ? EntityType.ENEMY_SCRAP : e;
                    })
                    .collect(Collectors.toList())
            );
        }
    }

}
