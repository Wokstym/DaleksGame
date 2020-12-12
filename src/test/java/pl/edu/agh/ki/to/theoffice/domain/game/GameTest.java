package pl.edu.agh.ki.to.theoffice.domain.game;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.util.LinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;

public class GameTest {

    @Test
    @Disabled
    // todo: refactor test
    public void testFromPropertiesNumberOfEntities() {
        // given
        GameProperties.GameMapProperties gameMapProperties = new GameProperties.GameMapProperties(20, 20, MapMoveStrategy.Type.BOUNDED);
//        GameProperties gameProperties = new GameProperties(gameMapProperties, 10);

        // when
//        Game game = Game.fromProperties(gameProperties);

        // then
//        assertEquals(10 + 1, game
//                .getEntities()
//                .size());
//
//        assertTrue(game
//                .getEntities()
//                .toSingleValueMap()
//                .containsValue(EntityType.PLAYER));
    }

    @Test
    @Disabled
    // todo: refactor test
    public void testPlayerNotMoved() {
        // given
        GameProperties.GameMapProperties gameMapProperties = new GameProperties.GameMapProperties(100, 1, MapMoveStrategy.Type.BOUNDED);
//        GameProperties gameProperties = new GameProperties(gameMapProperties, 1);
//        Game game = Game.fromProperties(gameProperties);
        Location.Direction direction = Location.Direction.SOUTH;

        // when
//        PlayerMoveResponse playerMoveResponse = game.movePlayer(direction);

        // then
        // assertEquals(PlayerMoveResponse.PLAYER_NOT_MOVED, playerMoveResponse);
    }

    @Test
    @Disabled
    // todo: refactor test
    public void testPlayerMoved() {
        // given
        GameProperties.GameMapProperties gameMapProperties = new GameProperties.GameMapProperties(5, 5, MapMoveStrategy.Type.BOUNDED);
//        GameProperties gameProperties = new GameProperties(gameMapProperties, 0);
//        Game game = Game.fromProperties(gameProperties);

        LinkedMultiValueMap<Location, EntityType> entities = new LinkedMultiValueMap<Location, EntityType>();
        Location playerLocation = new Location(2, 2);
        entities.add(playerLocation, EntityType.PLAYER);
//        game.setEntities(new ObservableLinkedMultiValueMap(entities));
//        game.setPlayerLocation(new SimpleObjectProperty<>(playerLocation));
        Location.Direction direction = Location.Direction.SOUTH;

        // when
//        PlayerMoveResponse playerMoveResponse = game.movePlayer(direction);
//
        // then
//        assertEquals(PlayerMoveResponse.PLAYER_MOVED, playerMoveResponse);
    }

    @Test
    @Disabled
    // todo: refactor test
    public void testPlayerCollidedWithEnemies() {
        // given
//        GameProperties.GameMapProperties gameMapProperties = new GameProperties.GameMapProperties(5, 5, MapMoveStrategy.Type.BOUNDED);
//        GameProperties gameProperties = new GameProperties(gameMapProperties, 0);
//        Game game = Game.fromProperties(gameProperties);
//
//        LinkedMultiValueMap<Location, EntityType> entities = new LinkedMultiValueMap<Location, EntityType>();
//        Location playerLocation = new Location(2, 2);
//        entities.add(playerLocation, EntityType.PLAYER);
//        entities.add(new Location(3, 2), EntityType.ENEMY);
//        entities.add(new Location(1, 2), EntityType.ENEMY);
//        game.setEntities(new ObservableLinkedMultiValueMap(entities));
//        game.setPlayerLocation(new SimpleObjectProperty<>(playerLocation));
//        Location.Direction direction = Location.Direction.NORTH;

        // when
//        PlayerMoveResponse playerMoveResponse = game.movePlayer(direction);

        // then
//        assertEquals(PlayerMoveResponse.PLAYER_COLLIDED_WITH_ENEMY, playerMoveResponse);
    }

}