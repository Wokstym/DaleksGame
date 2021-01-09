package pl.edu.agh.ki.to.theoffice.domain.game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.common.command.Command;
import pl.edu.agh.ki.to.theoffice.common.command.CommandExecutor;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.MovableEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.PlayerEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.pickable.PickableEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.pickable.PickableEntityFactory;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.MapProperties;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Game {

    private static final int GAME_WON_POINTS = 50;
    private static final int POWERUP_PICKED_POINTS = 10;
    private static final int ENEMY_DESTROYED_POINTS = 15;

    private MapProperties mapProperties;
    private MapMoveStrategy mapMoveStrategy;

    private ObservableLinkedMultiValueMap<Location, Entity> entities;
    private Map<Location, PickableEntity> powerupsOnMap;
    private ObjectProperty<Location> playerLocation;
    private ObjectProperty<GameState> gameState;
    private PlayerEntity playerEntity;

    private GameDifficulty difficulty;
    private IntegerProperty level;
    private IntegerProperty score;

    private CommandExecutor commandExecutor;

    public void execute(Command command) {
        commandExecutor.execute(command);
    }

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
            score.set(score.get() + GAME_WON_POINTS);
            gameState.setValue(GameState.WON);
        }
        log.debug("score: {}", score.getValue());
    }

    public void usePowerup(GamePowerup gamePowerup) {
        if (playerEntity.canUsePowerup(gamePowerup)) {

            boolean success = PickableEntityFactory
                    .fromEntityType(gamePowerup)
                    .usePowerup(this);

            if (success) {
                log.debug("Powerup was used successfully");
                playerEntity.removePowerup(gamePowerup);
                commandExecutor.clearPreviousCommands();
            }
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
        final long enemiesBefore = getEnemiesCount();

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

        final long enemiesAfter = getEnemiesCount();

        log.debug("points added: {}", (enemiesBefore - enemiesAfter) * ENEMY_DESTROYED_POINTS);
        score.set(score.get() + (int) (enemiesBefore - enemiesAfter) * ENEMY_DESTROYED_POINTS);
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

        if (entity.isPresent()) {
            PickableEntity pickableEntity = powerupsOnMap.get(location);
            playerEntity.addPowerup(pickableEntity.getGamePowerup());
            powerupsOnMap.remove(location);

            entitiesAtPlayer.remove(entity.get());
            this.entities.remove(location);
            this.entities.put(location, entitiesAtPlayer);
            score.set(score.get() + POWERUP_PICKED_POINTS);
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

