package pl.edu.agh.ki.to.theoffice.domain.game;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.EnemyEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.PlayerEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.pickable.PickableEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.pickable.PickableEntityFactory;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GameProperties;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GamePropertiesConfiguration;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.MapProperties;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class GameRepository {

    private final MapMoveStrategy mapMoveStrategy;
    private final MapProperties mapProperties;

    private final GamePropertiesConfiguration gameConfigurations;

    public Game createNewGame(GameDifficulty gameDifficulty) {
        final GameProperties gameProperties = gameConfigurations.getConfiguration(gameDifficulty);

        var entities = generateEnemies(gameProperties, 1);

        var player = generatePlayer(entities, gameProperties);
        entities.add(player.getLocation(), player);

        var powerupsOnMap = generatePowerups(entities, gameProperties);
        powerupsOnMap.forEach(entities::add);

        return Game.builder()
                .mapProperties(mapProperties)
                .mapMoveStrategy(mapMoveStrategy)
                .gameState(new SimpleObjectProperty<>(GameState.IN_PROGRESS))
                .entities(new ObservableLinkedMultiValueMap<>(entities))
                .playerEntity(player)
                .playerLocation(new SimpleObjectProperty<>(player.getLocation()))
                .powerupsOnMap(powerupsOnMap)
                .difficulty(gameDifficulty)
                .level(new SimpleIntegerProperty(1))
                .score(new SimpleIntegerProperty(0))
                .build();
    }

    public Game createNextLevel(Game game) {
        final GameProperties gameProperties = gameConfigurations.getConfiguration(game.getDifficulty());

        var entities = generateEnemies(gameProperties, game.getLevel().get());

        var player = generatePlayer(entities, gameProperties);
        player.setLives(game.getPlayerEntity().getLives());
        player.setPowerups(game.getPlayerEntity().getPowerups());

        entities.add(player.getLocation(), player);

        var powerupsOnMap = generatePowerups(entities, gameProperties);
        powerupsOnMap.forEach(entities::add);

        return Game.builder()
                .mapProperties(mapProperties)
                .mapMoveStrategy(mapMoveStrategy)
                .gameState(new SimpleObjectProperty<>(GameState.IN_PROGRESS))
                .entities(new ObservableLinkedMultiValueMap<>(entities))
                .playerEntity(player)
                .playerLocation(new SimpleObjectProperty<>(player.getLocation()))
                .powerupsOnMap(powerupsOnMap)
                .difficulty(game.getDifficulty())
                .level(new SimpleIntegerProperty(game.getLevel().get() + 1))
                .score(new SimpleIntegerProperty(game.getScore().get()))
                .build();
    }

    private LinkedMultiValueMap<Location, Entity> generateEnemies(GameProperties gameProperties, int level) {
        final int width = mapProperties.getWidth();
        final int height = mapProperties.getHeight();

        final var entities = new LinkedMultiValueMap<Location, Entity>();
        while (entities.size() < gameProperties.getEnemies() * level) {
            Location location = Location.randomLocation(width, height);
            entities.addIfAbsent(location, new EnemyEntity(mapMoveStrategy, location));
        }

        return entities;
    }

    private PlayerEntity generatePlayer(LinkedMultiValueMap<Location, Entity> entities, GameProperties gameProperties) {
        final int width = mapProperties.getWidth();
        final int height = mapProperties.getHeight();

        PlayerEntity player = PlayerEntity.fromProperties(gameProperties);
        Location playerLocation;
        do {
            playerLocation = Location.randomLocation(width, height);
        } while (entities.containsKey(playerLocation));
        player.setLocation(playerLocation);
        return player;
    }

    private Map<Location, PickableEntity> generatePowerups(LinkedMultiValueMap<Location, Entity> entities, GameProperties gameProperties) {
        final int width = mapProperties.getWidth();
        final int height = mapProperties.getHeight();

        return gameProperties
                .getPowerups()
                .entrySet()
                .stream()
                .flatMap(e -> IntStream.range(0, e.getValue())
                        .mapToObj(nr -> e.getKey()))
                .map(e -> {
                    Location powerupLocation;
                    do {
                        powerupLocation = Location.randomLocation(width, height);
                    } while (entities.containsKey(powerupLocation));
                    return new AbstractMap.SimpleEntry<>(powerupLocation, PickableEntityFactory.fromEntityType(e));
                })
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

}
