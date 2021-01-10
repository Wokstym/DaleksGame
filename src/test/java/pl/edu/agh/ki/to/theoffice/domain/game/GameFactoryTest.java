package pl.edu.agh.ki.to.theoffice.domain.game;

import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.Test;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.PlayerEntity;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GameProperties;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GamePropertiesConfiguration;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.MapProperties;
import pl.edu.agh.ki.to.theoffice.domain.map.move.BoundedMapMoveStrategy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameFactoryTest {

    @Test
    public void testCreateNewGame() {
        // given
        GameProperties gameProperties = GameProperties.builder()
                .powerup(GamePowerup.BOMB, 1)
                .powerup(GamePowerup.TELEPORT, 2)
                .lives(3)
                .enemies(4)
                .build();

        GamePropertiesConfiguration configuration = mock(GamePropertiesConfiguration.class);
        when(configuration.getConfiguration(GameDifficulty.EASY)).thenReturn(gameProperties);

        GameFactory gameFactory = new GameFactory(
                new BoundedMapMoveStrategy(20, 20),
                new MapProperties(20, 20),
                configuration
        );

        // when
        Game game = gameFactory.createNewGame(GameDifficulty.EASY);

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

    @Test
    public void testCreateNextLevel() {
        // given
        Game game = mock(Game.class);
        when(game.getDifficulty()).thenReturn(GameDifficulty.EASY);
        when(game.getLevel()).thenReturn(new SimpleIntegerProperty(1));
        when(game.getPlayerEntity()).thenReturn(new PlayerEntity());
        when(game.getScore()).thenReturn(new SimpleIntegerProperty(10));

        GameProperties gameProperties = GameProperties.builder()
                .powerup(GamePowerup.BOMB, 1)
                .powerup(GamePowerup.TELEPORT, 2)
                .lives(3)
                .enemies(4)
                .build();

        GamePropertiesConfiguration configuration = mock(GamePropertiesConfiguration.class);
        when(configuration.getConfiguration(GameDifficulty.EASY)).thenReturn(gameProperties);

        GameFactory gameFactory = new GameFactory(
                new BoundedMapMoveStrategy(20, 20),
                new MapProperties(20, 20),
                configuration
        );

        // when
        Game nextLevelGame = gameFactory.createNextLevel(game);

        // then
        assertEquals(GameDifficulty.EASY, nextLevelGame.getDifficulty());
        assertEquals(game.getScore().get(), nextLevelGame.getScore().get());
        assertEquals(game.getLevel().get() + 1, nextLevelGame.getLevel().get());
        assertEquals(game.getPlayerEntity().getLives(), nextLevelGame.getPlayerEntity().getLives());
        assertEquals(game.getPlayerEntity().getPowerups(), nextLevelGame.getPlayerEntity().getPowerups());
    }
}