package pl.edu.agh.ki.to.theoffice.service;

import org.junit.jupiter.api.Test;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.game.GameDifficulty;
import pl.edu.agh.ki.to.theoffice.domain.game.GameRepository;
import pl.edu.agh.ki.to.theoffice.domain.game.GameState;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GameProperties;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GamePropertiesConfiguration;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.MapProperties;
import pl.edu.agh.ki.to.theoffice.domain.map.move.BoundedMapMoveStrategy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameFromPropertiesServiceTest {

    @Test
    public void testFromProperties() {
        // given
        GameProperties gameProperties = GameProperties.builder()
                .powerup(GamePowerup.BOMB, 1)
                .powerup(GamePowerup.TELEPORT, 2)
                .lives(3)
                .enemies(4)
                .build();

        GamePropertiesConfiguration configuration = mock(GamePropertiesConfiguration.class);
        when(configuration.getConfiguration(GameDifficulty.EASY)).thenReturn(gameProperties);

        GameRepository gameFromPropertiesService = new GameRepository(
                new BoundedMapMoveStrategy(20, 20),
                new MapProperties(20, 20),
                configuration
        );

        // when
        Game game = gameFromPropertiesService.createNewGame(GameDifficulty.EASY);

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