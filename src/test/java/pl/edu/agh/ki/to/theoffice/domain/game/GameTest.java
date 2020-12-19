package pl.edu.agh.ki.to.theoffice.domain.game;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GameTest {

    @Test
    public void testFromProperties() {
        // given
       /* GameProperties.GameMapProperties gameMapProperties = new GameProperties.GameMapProperties(20, 20, MapMoveStrategy.Type.BOUNDED);
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();
        GameProperties gameProperties = new GameProperties(gameMapProperties, MapMoveStrategyFactory.fromProperties(gameMapProperties), gamePlayerProperties, 10);

        // when
        Game game = Game.fromProperties(gameProperties);

        // then
        assertEquals(10 + 1, game
                .getEntities()
                .size());

        assertTrue(game
                .getEntities()
                .toSingleValueMap()
                .containsValue(EntityType.PLAYER));

        assertEquals(GameState.IN_PROGRESS, game.getGameState().getValue());*/

    }

    @Test
    public void testPlayerNotMoved() {
/*        // given
        GameProperties.GameMapProperties gameMapProperties = new GameProperties.GameMapProperties(100, 1, MapMoveStrategy.Type.BOUNDED);
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();
        GameProperties gameProperties = new GameProperties(gameMapProperties,MapMoveStrategyFactory.fromProperties(gameMapProperties), gamePlayerProperties, 0);
        Game game = Game.fromProperties(gameProperties);
        Location.Direction direction = Location.Direction.SOUTH;
        Location playerOldLocation = game.getPlayerLocation().getValue();

        // when
        game.movePlayer(direction);

        // then
         assertEquals(playerOldLocation, game.getPlayerLocation().getValue());*/
    }

    @Test
    public void testPlayerMoved() {
      /*  // given
        GameProperties.GameMapProperties gameMapProperties = new GameProperties.GameMapProperties(5, 5, MapMoveStrategy.Type.BOUNDED);
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();
        GameProperties gameProperties = new GameProperties(gameMapProperties,MapMoveStrategyFactory.fromProperties(gameMapProperties), gamePlayerProperties, 0);
        Game game = Game.fromProperties(gameProperties);

        LinkedMultiValueMap<Location, EntityType> entities = new LinkedMultiValueMap<Location, EntityType>();
        Location playerLocation = new Location(2, 2);
        entities.add(playerLocation, EntityType.PLAYER);
        game.setEntities(new ObservableLinkedMultiValueMap(entities));
        game.setPlayerLocation(new SimpleObjectProperty<>(playerLocation));
        Location.Direction direction = Location.Direction.SOUTH;

        // when
        game.movePlayer(direction);

        // then
        assertEquals(playerLocation.add(direction), game.getPlayerLocation().getValue());*/
    }

    @Test
    public void testPlayerCollidedWithEnemiesAndLost() {
      /*  // given
        GameProperties.GameMapProperties gameMapProperties = new GameProperties.GameMapProperties(5, 5, MapMoveStrategy.Type.BOUNDED);
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();
        GameProperties gameProperties = new GameProperties(gameMapProperties,MapMoveStrategyFactory.fromProperties(gameMapProperties), gamePlayerProperties, 0);
        Game game = Game.fromProperties(gameProperties);

        LinkedMultiValueMap<Location, EntityType> entities = new LinkedMultiValueMap<>();
        Location playerLocation = new Location(2, 2);
        entities.add(playerLocation, EntityType.PLAYER);
        entities.add(new Location(1, 2), EntityType.ENEMY);
        game.setEntities(new ObservableLinkedMultiValueMap(entities));
        game.setPlayerLocation(new SimpleObjectProperty<>(playerLocation));
        Location.Direction direction = Location.Direction.NONE;

        // when
        game.movePlayer(direction);

        // then
        assertEquals(GameState.LOST, game.getGameState().getValue());*/
    }

    @Test
    public void testPlayerWon() {
     /*   // given
        GameProperties.GameMapProperties gameMapProperties = new GameProperties.GameMapProperties(5, 5, MapMoveStrategy.Type.BOUNDED);
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder().build();
        GameProperties gameProperties = new GameProperties(gameMapProperties,MapMoveStrategyFactory.fromProperties(gameMapProperties), gamePlayerProperties, 0);
        Game game = Game.fromProperties(gameProperties);

        LinkedMultiValueMap<Location, EntityType> entities = new LinkedMultiValueMap<>();
        Location playerLocation = new Location(2, 2);
        entities.add(playerLocation, EntityType.PLAYER);
        entities.add(new Location(1, 3), EntityType.ENEMY);
        entities.add(new Location(3, 3), EntityType.ENEMY);
        game.setEntities(new ObservableLinkedMultiValueMap(entities));
        game.setPlayerLocation(new SimpleObjectProperty<>(playerLocation));
        Location.Direction direction = Location.Direction.NORTH;

        // when
        game.movePlayer(direction);

        // then
        assertEquals(GameState.WON, game.getGameState().getValue());*/
    }

}