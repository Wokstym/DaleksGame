package pl.edu.agh.ki.to.theoffice.domain.game;

import javafx.beans.property.SimpleObjectProperty;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.EnemyEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.PlayerEntity;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GameMapProperties;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GameProperties;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.move.BoundedMapMoveStrategy;
import pl.edu.agh.ki.to.theoffice.service.GameFromPropertiesService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    @Test
    public void testPlayerNotMoved() {
        // given
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();

        GameProperties gameProperties = GameProperties.builder()
                .enemies(0)
                .playerProperties(gamePlayerProperties)
                .build();

        GameFromPropertiesService gameFromPropertiesService = new GameFromPropertiesService();
        gameFromPropertiesService.setGameMapProperties(new GameMapProperties(10, 1));
        gameFromPropertiesService.setMapMoveStrategy(new BoundedMapMoveStrategy(10, 1));
        Game game = gameFromPropertiesService.fromProperties(gameProperties);
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
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();

        GameProperties gameProperties = GameProperties.builder()
                .enemies(0)
                .playerProperties(gamePlayerProperties)
                .build();

        GameFromPropertiesService gameFromPropertiesService = new GameFromPropertiesService();
        gameFromPropertiesService.setGameMapProperties(new GameMapProperties(5, 5));
        gameFromPropertiesService.setMapMoveStrategy(new BoundedMapMoveStrategy(5, 5));
        Game game = gameFromPropertiesService.fromProperties(gameProperties);

        LinkedMultiValueMap<Location, Entity> entities = new LinkedMultiValueMap<>();
        Location playerLocation = new Location(2, 2);
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gamePlayerProperties);
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
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();

        GameProperties gameProperties = GameProperties.builder()
                .enemies(0)
                .playerProperties(gamePlayerProperties)
                .build();

        GameFromPropertiesService gameFromPropertiesService = new GameFromPropertiesService();
        gameFromPropertiesService.setGameMapProperties(new GameMapProperties(5, 5));
        gameFromPropertiesService.setMapMoveStrategy(new BoundedMapMoveStrategy(5, 5));
        Game game = gameFromPropertiesService.fromProperties(gameProperties);

        LinkedMultiValueMap<Location, Entity> entities = new LinkedMultiValueMap<>();
        Location playerLocation = new Location(2, 2);
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gamePlayerProperties);
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
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();

        GameProperties gameProperties = GameProperties.builder()
                .enemies(0)
                .playerProperties(gamePlayerProperties)
                .build();

        GameFromPropertiesService gameFromPropertiesService = new GameFromPropertiesService();
        gameFromPropertiesService.setGameMapProperties(new GameMapProperties(5, 5));
        gameFromPropertiesService.setMapMoveStrategy(new BoundedMapMoveStrategy(5, 5));
        Game game = gameFromPropertiesService.fromProperties(gameProperties);

        LinkedMultiValueMap<Location, Entity> entities = new LinkedMultiValueMap<>();
        Location playerLocation = new Location(2, 2);
        PlayerEntity playerEntity = PlayerEntity.fromProperties(gamePlayerProperties);
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

}