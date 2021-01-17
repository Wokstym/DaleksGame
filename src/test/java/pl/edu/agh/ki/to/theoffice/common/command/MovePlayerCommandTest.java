package pl.edu.agh.ki.to.theoffice.common.command;

import org.junit.jupiter.api.Test;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.EnemyEntity;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.game.GameDifficulty;
import pl.edu.agh.ki.to.theoffice.domain.game.GameFactory;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GameProperties;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GamePropertiesConfiguration;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.MapProperties;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.move.BoundedMapMoveStrategy;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MovePlayerCommandTest {

    @Test
    void testUndo() {
        // given
        MapProperties mapProperties = new MapProperties(10, 10);
        MapMoveStrategy mapMoveStrategy = new BoundedMapMoveStrategy(mapProperties.getWidth(), mapProperties.getHeight());
        GameProperties gameProperties = GameProperties.builder()
                .enemies(1)
                .lives(10)
                .build();

        GamePropertiesConfiguration configuration = mock(GamePropertiesConfiguration.class);
        when(configuration.getConfiguration(GameDifficulty.EASY)).thenReturn(gameProperties);

        GameFactory gameFactory = new GameFactory(mapMoveStrategy, mapProperties, configuration);
        Game game = gameFactory.createNewGame(GameDifficulty.EASY);

        MovePlayerCommand movePlayerCommand = new MovePlayerCommand(game, Location.Direction.NORTH);

        final Location expectedPlayerLocation = game.getPlayerLocation().getValue();
        final Integer expectedScore = game.getScore().getValue();
        final EnemyEntity expectedEnemyEntity = getEnemyEntity(game);

        movePlayerCommand.execute();

        // when
        movePlayerCommand.undo();

        // then
        final Location actualPlayerLocation = game.getPlayerLocation().getValue();
        final Integer actualScore = game.getScore().get();
        final EnemyEntity actualEnemyEntity = getEnemyEntity(game);

        assertEquals(expectedPlayerLocation, actualPlayerLocation);
        assertEquals(expectedScore, actualScore);
        assertEquals(expectedEnemyEntity, actualEnemyEntity);
    }

    private static EnemyEntity getEnemyEntity(Game game) {
        return game.getEntities().values()
                .stream()
                .flatMap(Collection::stream)
                .filter(EnemyEntity.class::isInstance)
                .map(EnemyEntity.class::cast)
                .findFirst()
                .orElseThrow();
    }

}