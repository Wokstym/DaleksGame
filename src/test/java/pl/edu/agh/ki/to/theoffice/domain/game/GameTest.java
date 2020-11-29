package pl.edu.agh.ki.to.theoffice.domain.game;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import pl.edu.agh.ki.to.theoffice.domain.map.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.PlayerMoveResponse;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class GameTest {

    @Test
    public void testFromPropertiesNumberOfEntities() {
        // given
        GameProperties.GameMapProperties gameMapProperties = new GameProperties.GameMapProperties(20, 20, MapMoveStrategy.Type.BOUNDED);
        GameProperties gameProperties = new GameProperties(gameMapProperties, 10);

        // when
        Game game = Game.fromProperties(gameProperties);

        // then
        assertEquals(10 + 1, game
                .getEntities()
                .getValue()
                .size());

        assertTrue(game
                .getEntities()
                .getValue()
                .toSingleValueMap()
                .containsValue(EntityType.PLAYER));
    }

    @Test
    public void testPlayerNotMoved() {
        // given
        GameProperties.GameMapProperties gameMapProperties = new GameProperties.GameMapProperties(100, 1, MapMoveStrategy.Type.BOUNDED);
        GameProperties gameProperties = new GameProperties(gameMapProperties, 1);
        Game game = Game.fromProperties(gameProperties);
        Location.Direction direction = Location.Direction.SOUTH;

        // when
        PlayerMoveResponse playerMoveResponse = game.movePlayer(direction);

        // then
        assertEquals(PlayerMoveResponse.PLAYER_NOT_MOVED, playerMoveResponse);
    }

}