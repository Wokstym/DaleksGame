package pl.edu.agh.ki.to.theoffice.service;

import org.junit.jupiter.api.Test;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.game.GameState;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GameMapProperties;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GameProperties;
import pl.edu.agh.ki.to.theoffice.domain.map.move.BoundedMapMoveStrategy;

import static org.junit.jupiter.api.Assertions.*;

class GameFromPropertiesServiceTest {

    @Test
    public void testFromProperties() {
        // given
        GameProperties.GamePlayerProperties gamePlayerProperties = GameProperties.GamePlayerProperties.builder()
                .powerup(GamePowerup.BOMB, 1)
                .powerup(GamePowerup.TELEPORT, 2)
                .lives(3)
                .build();

        GameProperties gameProperties = GameProperties.builder()
                .enemies(4)
                .playerProperties(gamePlayerProperties)
                .build();

        GameFromPropertiesService gameFromPropertiesService = new GameFromPropertiesService(
                new GameMapProperties(20, 20),
                new BoundedMapMoveStrategy(20, 20)
        );

        // when
        Game game = gameFromPropertiesService.fromProperties(gameProperties);

        // then
        assertEquals(4 + 2 + 1 + 1, game
                .getEntities()
                .size());

        assertEquals(2 + 1, game
                .getPowerupsOnMap()
                .size());

        assertEquals(GameState.IN_PROGRESS, game
                .getGameState()
                .getValue());
    }
}