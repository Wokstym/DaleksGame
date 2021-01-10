package pl.edu.agh.ki.to.theoffice.domain.game;

import javafx.beans.property.SimpleObjectProperty;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.common.command.MovePlayerCommand;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.EnemyEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.PlayerEntity;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GameProperties;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GamePropertiesConfiguration;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.MapProperties;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.move.BoundedMapMoveStrategy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameTest {

    @Test
    public void testPlayerNotMoved() {
        // given
        GameProperties gameProperties = GameProperties.builder()
                .enemies(0)
                .build();

        GamePropertiesConfiguration configuration = mock(GamePropertiesConfiguration.class);
        when(configuration.getConfiguration(GameDifficulty.EASY)).thenReturn(gameProperties);

        GameFactory gameFactory = new GameFactory(
                new BoundedMapMoveStrategy(10, 1),
                new MapProperties(10, 1),
                configuration
        );

        Game game = gameFactory.createNewGame(GameDifficulty.EASY);

        Location.Direction direction = Location.Direction.SOUTH;
        Location playerOldLocation = game.getPlayerLocation().getValue();

        // when
        game.movePlayer(direction);

        // then
        assertEquals(playerOldLocation, game.getPlayerLocation().getValue());
    }

    @Test
    public void testPlayerMoved() {
        // given
        GameProperties gameProperties = GameProperties.builder()
                .enemies(0)
                .build();

        GamePropertiesConfiguration configuration = mock(GamePropertiesConfiguration.class);
        when(configuration.getConfiguration(GameDifficulty.EASY)).thenReturn(gameProperties);

        GameFactory gameFactory = new GameFactory(
                new BoundedMapMoveStrategy(5, 5),
                new MapProperties(5, 5),
                configuration
        );

        Game game = gameFactory.createNewGame(GameDifficulty.EASY);

        LinkedMultiValueMap<Location, Entity> entities = new LinkedMultiValueMap<>();
        Location playerLocation = new Location(2, 2);
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gameProperties);
        playerEntity.setLocation(playerLocation);
        entities.add(playerLocation, playerEntity);
        game.setEntities(new ObservableLinkedMultiValueMap(entities));
        game.setPlayerLocation(new SimpleObjectProperty<>(playerLocation));
        game.setPlayerEntity(playerEntity);
        Location.Direction direction = Location.Direction.SOUTH;

        // when
        game.movePlayer(direction);

        // then
        assertEquals(playerLocation.add(direction), game.getPlayerLocation().getValue());
    }

    @Test
    public void testPlayerCollidedWithEnemiesAndLost() {
        // given
        GameProperties gameProperties = GameProperties.builder()
                .enemies(0)
                .build();

        GamePropertiesConfiguration configuration = mock(GamePropertiesConfiguration.class);
        when(configuration.getConfiguration(GameDifficulty.EASY)).thenReturn(gameProperties);

        GameFactory gameFactory = new GameFactory(
                new BoundedMapMoveStrategy(5, 5),
                new MapProperties(5, 5),
                configuration
        );

        Game game = gameFactory.createNewGame(GameDifficulty.EASY);

        LinkedMultiValueMap<Location, Entity> entities = new LinkedMultiValueMap<>();
        Location playerLocation = new Location(2, 2);
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gameProperties);
        playerEntity.setLocation(playerLocation);
        entities.add(playerLocation, playerEntity);

        Location enemyLocation = new Location(2, 1);
        entities.add(enemyLocation, new EnemyEntity(new BoundedMapMoveStrategy(5, 5), enemyLocation));
        game.setEntities(new ObservableLinkedMultiValueMap(entities));
        game.setPlayerLocation(new SimpleObjectProperty<>(playerLocation));
        game.setPlayerEntity(playerEntity);
        Location.Direction direction = Location.Direction.SOUTH;

        // when
        game.movePlayer(direction);

        // then
        assertEquals(GameState.LOST, game.getGameState().getValue());
    }

    @Test
    public void testPlayerWon() {
        // given
        GameProperties gameProperties = GameProperties.builder()
                .enemies(0)
                .build();

        GamePropertiesConfiguration configuration = mock(GamePropertiesConfiguration.class);
        when(configuration.getConfiguration(GameDifficulty.EASY)).thenReturn(gameProperties);

        GameFactory gameFactory = new GameFactory(
                new BoundedMapMoveStrategy(5, 5),
                new MapProperties(5, 5),
                configuration
        );

        Game game = gameFactory.createNewGame(GameDifficulty.EASY);

        LinkedMultiValueMap<Location, Entity> entities = new LinkedMultiValueMap<>();
        Location playerLocation = new Location(2, 2);
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gameProperties);
        playerEntity.setLocation(playerLocation);
        entities.add(playerLocation, playerEntity);

        Location enemyLocation = new Location(1, 3);
        entities.add(enemyLocation, new EnemyEntity(new BoundedMapMoveStrategy(5, 5), enemyLocation));
        Location enemyLocation2 = new Location(3, 3);
        entities.add(enemyLocation2, new EnemyEntity(new BoundedMapMoveStrategy(5, 5), enemyLocation2));
        game.setEntities(new ObservableLinkedMultiValueMap(entities));
        game.setPlayerLocation(new SimpleObjectProperty<>(playerLocation));
        game.setPlayerEntity(playerEntity);
        Location.Direction direction = Location.Direction.NORTH;

        // when
        game.movePlayer(direction);

        // then
        assertEquals(GameState.WON, game.getGameState().getValue());
    }

    @Test
    public void testUsePowerupBomb() {
        // given
        GameProperties gameProperties = GameProperties.builder()
                .enemies(0)
                .build();

        GamePropertiesConfiguration configuration = mock(GamePropertiesConfiguration.class);
        when(configuration.getConfiguration(GameDifficulty.EASY)).thenReturn(gameProperties);

        GameFactory gameFactory = new GameFactory(
                new BoundedMapMoveStrategy(5, 5),
                new MapProperties(5, 5),
                configuration
        );

        Game game = gameFactory.createNewGame(GameDifficulty.EASY);

        LinkedMultiValueMap<Location, Entity> entities = new LinkedMultiValueMap<>();
        Location playerLocation = new Location(2, 2);
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gameProperties);
        playerEntity.addPowerup(GamePowerup.BOMB);
        playerEntity.setLocation(playerLocation);
        entities.add(playerLocation, playerEntity);

        Location enemyLocation = new Location(2, 3);
        entities.add(enemyLocation, new EnemyEntity(new BoundedMapMoveStrategy(5, 5), enemyLocation));
        Location enemyLocation2 = new Location(3, 2);
        entities.add(enemyLocation2, new EnemyEntity(new BoundedMapMoveStrategy(5, 5), enemyLocation2));
        game.setEntities(new ObservableLinkedMultiValueMap(entities));
        game.setPlayerLocation(new SimpleObjectProperty<>(playerLocation));
        game.setPlayerEntity(playerEntity);


        // when
        game.usePowerup(GamePowerup.BOMB);

        // then
        assertEquals(3, game.getEntities().values().size());
        assertEquals(GameState.WON, game.getGameState().getValue());
        assertEquals(playerLocation, game.getPlayerLocation().getValue());
    }

    @Test
    public void testUsePowerupTeleport() {
        // given
        GameProperties gameProperties = GameProperties.builder()
                .enemies(0)
                .build();

        GamePropertiesConfiguration configuration = mock(GamePropertiesConfiguration.class);
        when(configuration.getConfiguration(GameDifficulty.EASY)).thenReturn(gameProperties);

        GameFactory gameFactory = new GameFactory(
                new BoundedMapMoveStrategy(5, 5),
                new MapProperties(5, 5),
                configuration
        );

        Game game = gameFactory.createNewGame(GameDifficulty.EASY);

        LinkedMultiValueMap<Location, Entity> entities = new LinkedMultiValueMap<>();
        Location playerLocation = new Location(2, 2);
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gameProperties);
        playerEntity.addPowerup(GamePowerup.TELEPORT);
        playerEntity.setLocation(playerLocation);
        entities.add(playerLocation, playerEntity);

        Location enemyLocation = new Location(1, 3);
        entities.add(enemyLocation, new EnemyEntity(new BoundedMapMoveStrategy(5, 5), enemyLocation));
        Location enemyLocation2 = new Location(4, 4);
        entities.add(enemyLocation2, new EnemyEntity(new BoundedMapMoveStrategy(5, 5), enemyLocation2));
        game.setEntities(new ObservableLinkedMultiValueMap(entities));
        game.setPlayerLocation(new SimpleObjectProperty<>(playerLocation));
        game.setPlayerEntity(playerEntity);


        // when
        game.usePowerup(GamePowerup.TELEPORT);

        // then
        assertNotEquals(playerLocation, game.getPlayerLocation().getValue());
        assertFalse(game.getEntities().containsKey(enemyLocation));
        assertFalse(game.getEntities().containsKey(enemyLocation2));
    }

    @Test
    public void testUsePowerupReverseTime() {
        // given
        GameProperties gameProperties = GameProperties.builder()
                .enemies(0)
                .build();

        GamePropertiesConfiguration configuration = mock(GamePropertiesConfiguration.class);
        when(configuration.getConfiguration(GameDifficulty.EASY)).thenReturn(gameProperties);

        GameFactory gameFactory = new GameFactory(
                new BoundedMapMoveStrategy(5, 5),
                new MapProperties(5, 5),
                configuration
        );

        Game game = gameFactory.createNewGame(GameDifficulty.EASY);

        LinkedMultiValueMap<Location, Entity> entities = new LinkedMultiValueMap<>();
        Location playerLocation = new Location(2, 2);
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gameProperties);
        playerEntity.addPowerup(GamePowerup.REVERSE_TIME);
        playerEntity.setLocation(playerLocation);
        entities.add(playerLocation, playerEntity);

        Location enemyLocation = new Location(0, 3);
        entities.add(enemyLocation, new EnemyEntity(new BoundedMapMoveStrategy(5, 5), enemyLocation));
        Location enemyLocation2 = new Location(4, 4);
        entities.add(enemyLocation2, new EnemyEntity(new BoundedMapMoveStrategy(5, 5), enemyLocation2));
        game.setEntities(new ObservableLinkedMultiValueMap(entities));
        game.setPlayerLocation(new SimpleObjectProperty<>(playerLocation));
        game.setPlayerEntity(playerEntity);

        MovePlayerCommand movePlayerCommand = new MovePlayerCommand(game, Location.Direction.EAST);
        game.execute(movePlayerCommand);

        // when
        game.usePowerup(GamePowerup.REVERSE_TIME);

        // then
        assertEquals(playerLocation, game.getPlayerLocation().getValue());
        assertTrue(game.getEntities().containsKey(enemyLocation));
        assertTrue(game.getEntities().containsKey(enemyLocation2));
    }
}